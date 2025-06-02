#include "com2039.hpp"


size_t loadSamples(string fileName, float **addressToSamples){

	string line;
	std::ifstream inputFile(fileName);
	// Count the number of lines to allocate
	// the right amount of memory
	size_t numLines=0;
	while( getline( inputFile, line ) ){
		numLines++;
	};

	// Allocate memory on CPU
	// Notice that we dereference adressToSamples
	*addressToSamples  = (float*)malloc(numLines*sizeof(float));

	// Return to the beginning of the file
	// and fill in the
	inputFile.clear();
	inputFile.seekg(0);
	for(int j = 0; j < numLines; j++){
		getline(inputFile, line);
		(*addressToSamples)[j] = std::stof(line);
	}

	inputFile.close();
	return numLines;
}

/////// Find Maximum
__global__ void maxReduceKernel(float *d_in, int lenArray){
	int index = blockIdx.x * blockDim.x + threadIdx.x;
		int stride = blockDim.x;
		float val = 0;
		float arr[BLOCK_SIZE];
		//find max value in each thread
		for(int i = index; i < lenArray; i += stride){
			d_in[index] = fmaxf(d_in[index], d_in[i]);
		}

		//printf("%f\n", d_in[index]);
		__syncthreads();

		//block_size = number of threads in a block
			//so put thread max into shared memory for each block
		__shared__ float threadSharedMem[BLOCK_SIZE];

		threadSharedMem[threadIdx.x] = d_in[index];
		__syncthreads();
		//make shared memory

		__shared__ float sharedMem[BLOCK_SIZE];

		for (int i=0; i < BLOCK_SIZE; ++i){
			//itterate through threadShareMem and compare to
			//sharedMem[block.id] and put the min value into
			//it
			val = fmaxf(val, threadSharedMem[i]);
		}
		__syncthreads();

		sharedMem[blockIdx.x] = val;
		__syncthreads();

		//now itterate though this array to find the min
		//out of all the blocks

		for (int i=0; i < BLOCK_SIZE; ++i){
			val = fmaxf(val, sharedMem[i]);
		}
		__syncthreads();

		//write back to global
		d_in[0] = val;

}


float findMaxValue(float* samples_h, size_t numSamples){
	//allocate memory
	int *device_array = 0;
	int *host_array = 0;
	float *tempMax;
	float maxVal = 0;

	host_array = (int*)malloc(BLOCK_SIZE * sizeof(int));
	cudaMalloc((void**)&device_array, BLOCK_SIZE);
	cudaMallocManaged(&tempMax, numSamples*sizeof(float));

	//send data to the GPU from the CPU
	for (int i=0; i < numSamples; i++){
		tempMax[i] = samples_h[i];
	}
	cudaMemcpy(device_array, tempMax, numSamples*sizeof(float), cudaMemcpyHostToDevice);

	//make the threads and put them to work
	maxReduceKernel<<<ceil(numSamples/BLOCK_SIZE), BLOCK_SIZE>>>(tempMax, numSamples);
	cudaError_t err = cudaDeviceSynchronize();
//	printf("Run kernel: %s\n", cudaGetErrorString(err));
//	err = cudaThreadSynchronize();
//	printf("Run kernel: %s\n", cudaGetErrorString(err));

	//return a copy of [0] element (this should be the min value)
	maxVal = tempMax[0];
	cudaFree(tempMax);
	cudaFree(device_array);
	cudaFree(host_array);
	return maxVal;
}


/////// Find Minimum
__global__ void minReduceKernel(float *d_in, int lenArray){
	int index = blockIdx.x * blockDim.x + threadIdx.x;
	int stride = blockDim.x;
	float val = 0;
	float arr[BLOCK_SIZE];
	//find min value in each thread
	for(int i = index; i < lenArray; i += stride){
		d_in[index] = fminf(d_in[index], d_in[i]);
	}

	//printf("%f\n", d_in[index]);
	__syncthreads();

	//block_size = number of threads in a block
		//so put thread max into shared memory for each block
	__shared__ float threadSharedMem[BLOCK_SIZE];

	threadSharedMem[threadIdx.x] = d_in[index];
	__syncthreads();
	//make shared memory

	__shared__ float sharedMem[BLOCK_SIZE];

	for (int i=0; i < BLOCK_SIZE; ++i){
		//itterate through threadShareMem and compare to
		//sharedMem[block.id] and put the min value into
		//it
		val = fminf(val, threadSharedMem[i]);
	}
	__syncthreads();

	sharedMem[blockIdx.x] = val;
	__syncthreads();

	//now itterate though this array to find the min
	//out of all the blocks

	for (int i=0; i < BLOCK_SIZE; ++i){
		val = fminf(val, sharedMem[i]);
	}
	__syncthreads();

	//write back to global
	d_in[0] = val;

}



float findMinValue(float* samples_h, size_t numSamples){
	//allocate memory
	int *device_array = 0;
	int *host_array = 0;
	float *temp;
	float minVal = 0;

	host_array = (int*)malloc(BLOCK_SIZE * sizeof(int));
	cudaMalloc((void**)&device_array, BLOCK_SIZE);
	cudaMallocManaged(&temp, numSamples*sizeof(float));

	//send data to the GPU from the CPU
	for (int i=0; i < numSamples; i++){
		temp[i] = samples_h[i];
	}
	cudaMemcpy(device_array, temp, numSamples*sizeof(float), cudaMemcpyHostToDevice);

	//make the threads and put them to work
	minReduceKernel<<<ceil(numSamples/BLOCK_SIZE), BLOCK_SIZE>>>(temp, numSamples);

	cudaError_t err = cudaDeviceSynchronize();
//	printf("Run kernel: %s\n", cudaGetErrorString(err));
//	err = cudaThreadSynchronize();
//	printf("Run kernel: %s\n", cudaGetErrorString(err));

	//return a copy of [0] element (this should be the min value)
	minVal = temp[0];
	cudaMemcpy(device_array, temp, numSamples*sizeof(float), cudaMemcpyDeviceToHost);
	cudaFree(temp);
	return minVal;
}



/////// Create Histogram
__global__ void histogramKernel256(float *d_in, unsigned int *hist, size_t lenArray, float minValue, float maxValue) {
	int index = threadIdx.x + blockIdx.x * blockDim.x;
	int stride = blockDim.x;
	int binIndex;
	int Cval = 0;
	bool flag = false;
	//want hist with 256 bins with equal width.

	float binWidth = (maxValue-minValue) /NUM_BINS;
	if (index == 0){
		printf("bin width: %f\n", binWidth);
	};


	__shared__ int frequencySharedMem[NUM_BINS];
	for (int i = 0; i < NUM_BINS; i++){
		frequencySharedMem[i] = 0;
	}

	__syncthreads();

	for (int i=index; i < lenArray; i += stride){
		binIndex = (d_in[i] - minValue) / binWidth;
		if (binIndex > 255){
			binIndex = 255;
		}
//		if (threadIdx.x ==0){
//			printf("\nBin [%i]", binIndex);
//		}
		__syncthreads();
		atomicAdd(&frequencySharedMem[binIndex], 1);
		__syncthreads();
//		if (threadIdx.x ==0){
//			printf(" | %i", frequencySharedMem[binIndex]);
//		}
//		__syncthreads();
	}
//	__syncthreads();
	if (index ==0){
		Cval = 0;
//		for (int i = 0; i < NUM_BINS; i++){
//			printf("\nBin : %i | %f", i, frequencySharedMem[i]);
//			Cval += frequencySharedMem[i];
//		}
		printf("\ntotal frequency: %i", Cval);
	};

	//------------------------------------assuming frequency found-----------------------------------------------------

	__shared__ float frequencyDensitySharedMem[NUM_BINS];
	if (index == 0){
		int counter = 0;
		for (int i = 0; i < NUM_BINS; i++){
			frequencyDensitySharedMem[i] = (frequencySharedMem[i] / binWidth);
			printf("\nBin[%i]: %f", i, frequencyDensitySharedMem[i]);
			counter += frequencySharedMem[i];
		}
		printf("\nTotal number of elements in histogram: %i", counter);

	}
	__syncthreads();

	for (int i = 0; i < NUM_BINS; i++){
		hist[i] = frequencyDensitySharedMem[i];
	}

}

//for (int i=index; i < lenArray; i += stride){
//		cumulativeBinWidth = (d_in[index] / binWidth) + 1;
//		int currentBin = cumulativeBinWidth / binWidth;
//		__syncthreads();
//		atomicAdd(&frequencySharedMem[currentBin], 1);
//
//		__syncthreads();
//	}

//while (index < lenArray) {
//		//atomicAdd(int* address, int val)
//			//When a thread executes this operation,
//			//a memory address is read, has the value of
//			//‘val’ added to it, and the result is
//			//written back to memory.
//		//atomicAdd( &(hist[d_in[index]]), 1);
//		index += stride;
//	}
//	__syncthreads();

//Cval = frequencySharedMem[currentBin];
//			__syncthreads();
//
//			Cval += 1;
//			__syncthreads();
//			frequencySharedMem[currentBin] = Cval;


/// histogram
void histogram256(float *samples_h, size_t numSamples, unsigned int **hist_h, float minValue, float maxValue) {

	int *device_array = 0;
	int *host_array = 0;
	float *buffer;
	unsigned int *hist = *hist_h;
	int grid_size = (numSamples + BLOCK_SIZE -1)/BLOCK_SIZE;
	printf("num blocks: %d\n", grid_size);

	host_array = (int*)malloc(BLOCK_SIZE * sizeof(int));
	cudaMalloc((void**)&device_array, BLOCK_SIZE);
	cudaMallocManaged(&buffer, numSamples*sizeof(float));
	cudaMemset(hist, 0, NUM_BINS * sizeof(float));
//	for (int i=0; i <NUM_BINS; i++){
//		hist[i] = *hist_h[i];
//	}

	for (int i = 0; i < numSamples; i++) {
		buffer[i] = samples_h[i];
	}

	cudaMemcpy(device_array, buffer, numSamples*sizeof(float), cudaMemcpyHostToDevice);


	histogramKernel256<<<grid_size, BLOCK_SIZE>>>(buffer, hist, numSamples, minValue, maxValue);

	cudaDeviceSynchronize();
	for (int i=0; i < 5; i++){
			printf("%f", hist[i]);
		}
	cudaGetLastError();
	cudaMemcpy(device_array, buffer, numSamples*sizeof(float), cudaMemcpyDeviceToHost);
	cudaFree(buffer);
}

