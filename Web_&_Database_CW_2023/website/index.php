<html>
<head>
<?php require 'db_connect.php'?>
<title>
See You Rent Agreements
</title>
<link rel= "stylesheet" href = "style.css">
<body>
<! -- nav bar____________________________________________________________-->

<div class="header">
<img class="headimg" src = "img/header.png"> 
</div>

<ul>
<li><a class="active" href = "index.php">Info </a></li>
<li><a href = "page2.php">Your Location Manager </a></li>
</ul>


<div class="col1">
<p><form action="<?php $_SERVER["PHP_SELF"];?>" method="post">
	<label for="StID">Student ID:</label>
	<br>
		<input type="text" id="StID" name="StID">
	<br> 
	<label for="Type">Type of Rental:</label>
	<br>
		<select type="text" id="Type" name="Type">
			<option style="display:none" value="Defult">---select---</option>
			<option value="S">Student</option>
			<option value="G">Guest</option>
		</select>
	<p>
		<input type="submit" value="Send"></p>
	</form>
</div>



<div class="col2">

<p><?php 
$ID ="";
 $Type = "";
 $err = "";
$idPat = "/^673[1-7]$/";
$result = null;
if ($_SERVER["REQUEST_METHOD"] == "POST"){
	//checking if inputs are valid:
	if (empty($_POST["StID"])){
		//if ID is empty:
		echo "Your Student ID is required<p>";
	}if (!preg_match($idPat,$_POST["StID"])){
		//if ID isn't the right set of numbers:
		echo "The ID needs to be in the format $idPat<p>";
	}else {
		//otherwise set the ID variable:
		$ID = $_POST["StID"];
	}
	
	if ($_POST["Type"] == "Defult"){
		//if Type is empty:
		echo "Please select an option<p>";
	}else {
		//set Type varibale:
		$Type = $_POST["Type"];
	}
	
	//what type of rental agreements do they want to see
	//Student rental agreements:
	if ($Type == "S"){
		$result = mysqli_query($conn, "select Student.StudentID, concat(Student.FirstName, ' ', Student.LastName) as 'Student Name', concat(RoomNum, ' ', FlatNo, ' ', Road, ' ', Site) as 'Address' , StartDate, EndDate 
 from Rent 
 inner join Student on Student.StudentID = Rent.StudentID 
 inner join Room on Room.RoomID = Rent.RoomID 
 inner join Flat on Flat.FlatID = Room.FlatID 
 inner join Location on Location.LocationID = Flat.LocationID 
 where Rent.RoomID in (select RoomID from StudentRoom);");
 	//guest renatal agreements:
	}if ($Type == "G"){
		$result = mysqli_query($conn, "select Student.StudentID, concat(Student.FirstName, ' ', Student.LastName) as 'Student Name', concat(RoomNum, ' ', FlatNo, ' ', Road, ' ', Site) as 'Address' , StartDate, EndDate 
 from Rent 
 inner join Student on Student.StudentID = Rent.StudentID 
 inner join Room on Room.RoomID = Rent.RoomID 
 inner join Flat on Flat.FlatID = Room.FlatID 
 inner join Location on Location.LocationID = Flat.LocationID 
 where Rent.RoomID in (select RoomID from GuestRoom);");
	}
	//output the result:
	echo "The ID entered is: $ID<p>The Type entered: $Type<p>";
	echo "<table><tr><th>Student ID</th> <th>Address</th><th>Start Date</th><th>End Date</th></tr>";
	if ($result != null){	
		while ($r = mysqli_fetch_assoc($result)){
			if ($r['StudentID'] == $ID){
				echo "<tr><td>", $r['StudentID'], "</td><td>", $r['Address'], "</td><td> ", $r['StartDate'], "</td><td>", $r['EndDate'], "</td></tr><p>";
			}
		}
		//free result and close connection
		mysqli_free_result($result);
		mysqli_close($conn);
	}
	echo "</table>";
	
}
?>

</body>
</html>


