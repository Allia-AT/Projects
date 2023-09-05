<html>
<head>
<?php require 'db_connect.php'?>
<title>
Your Location Manager
</title>
<link rel= "stylesheet" href = "style.css">
</head>
<body>
<! -- nav bar____________________________________________________________-->
<div class="header">
<img class="headimg" src = "img/header.png">
</div>
<ul>
<li><a href = "index.php">Info </a></li>
<li><a class="active" href = "page2.php">Your Location Manager </a></li>
</ul>

<div class="col1">
<p>Enter the location that you live in and find the location manager.

<! -- form 1:____________________________________________________________-->
<br><form action="<?php $_SERVER["PHP_SELF"];?>" method="get">
<select onclick="update();" id ="Site">
<option style="display:none" value="0">---select---</option>
<option value="1"  type="text">Manour Park</option>
<option value="2" type="text">Hazel Farm</option>
</select>
<br>Road:
<select id="M">
	<option style="display:none" value="0">---select---</option>
	<option value="AF">AF</option>
	<option value="DJ">DJ</option>
	<option value="EC">EC</option>
	<option value="JB">JB</option>
	<option value="RR">RR</option>
</select>

<select id="H">
	<option style="display:none" value="0">---select---</option>
	<option value="HC">HC</option>
	<option value="HD">HD</option>
	<option value="HP">HP</option>
	<option value="OW">OW</option>
</select>
<p> <input type="submit" value="submit"></p>
</form>

</div>
<div class="col2">
<p> show result for that here.
<p>display database the row here

<p>
<?php $Site ="";
 $Road = "";
if ($_SERVER["REQUEST_METHOD"] == "GET"){
	//checking if inputs are valid:

	//if the current site selected is manour park
	//set the site variable and the current road variable
	if (isset($_POST['Site']) == "1"){
		$Site = "Manor Park";
		if (isset($_POST['M']) != 0){
			$Road = $_POST['M'];
		}else{
			echo "Road name is required<p>";
		}
	}
	//if the current site selected is manour park
	//set the site variable and the current road variable
	if (isset($_POST['Site']) == "2") {
		$Site = "Hazel Farm";
		if (isset($_POST['H']) != 0){
			$Road = $_POST['H'];
		}else{
			echo "Road name is required<p>";
		}
		echo "Road name: $Road";
	}
		
	//gets the employee name that manages each site:
	$result = mysqli_query($conn,"select concat(FirstName, ' ', LastName) as 'Name', Road, Site
from Employee
inner join Location on Employee.EmpID = Location.ManagedBy;");

	//output the result:
	while ($r = mysqli_fetch_assoc($result)){
		if (($r["Road"] == $Road) and ($r["Site"] == $Site)){
			echo "<tr><td>", $r["Name"], "</td></tr>";
		}
	}
	
	//free result and close connection
	mysqli_free_result($result);
	mysqli_close($conn);
}?>


</div>
<script>
//at the start only the first selector is shown
if (document.getElementById('Site').value == "0"){
	document.getElementById('H').style.display = 'none';
	//document.getElementById('input').style.display = 'none';
	document.getElementById('M').style.display = 'none';}
//this is called when the Site selector is clicked, meaning that the value may have changed
function update(){
//at the defult state, hide the road selectors
if (document.getElementById('Site').value == "0"){
	document.getElementById('H').style.display = 'none';
	//document.getElementById('input').style.display = 'none';
	document.getElementById('M').style.display = 'none';}
//when Manour Park is selected, show only the Manour Park Roads
if (document.getElementById('Site').value == "1"){
	document.getElementById('H').style.display = 'none';
	//document.getElementById('input').style.display = 'block';
	document.getElementById('M').style.display = 'block';}
//when Hazel Farm is selected, show only the Hazel Farm Roads
if (document.getElementById('Site').value == "2"){
	document.getElementById('H').style.display = 'block';
	//document.getElementById('input').style.display = 'block';
	document.getElementById('M').style.display = 'none';}
}
</script>
</body>
</html>
