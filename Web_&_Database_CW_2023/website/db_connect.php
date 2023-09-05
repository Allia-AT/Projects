<!--  PHP code for connecting to the MySQL database -->
<?php
$servername = "localhost";
$username = "root";
$password = ""; 
$dbname = "accomidation"; #the name of the database
$port = "3306";

// Create a connection to MySQL
$conn = new mysqli($servername, $username, $password, $dbname);

// Check that the connection works
if ($conn->connect_error) {
  die("Connection failed: " . $conn->connect_error);
}

?>
