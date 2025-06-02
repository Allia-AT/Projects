The code covers the practical aspects of creating a histogram from real data. 

My task was to write an efficient CUDA kernel implementation of a uniform histogram having 256 bins. 
The extreme edges of the histogram will be defined by the minimum and maximum values of the sequence of numbers being processed. 

When creating the histogram, a data point x will fall into the i-th bin having lower and upper edges bi and bi+1, respectively, if bi ≤ x <bi+1, except for the last bin, which is inclusive of the rightmost edge, i.e., b255 ≤ x ≤ b256.

**Structure**
1. src/main.cpp: This file contains the main flow of the program and guides you through how to progress.
2. src/com2039.hpp: This is a header file containing the declaration of functions you will need to write.
3. src/Q1.cu: This contains the functions to find the lowest & highest value as well as the the funtion to create the hisogram.

Note: I have obmitted the files that were use to mark this code.
