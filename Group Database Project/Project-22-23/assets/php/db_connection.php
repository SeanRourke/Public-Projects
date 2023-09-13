<?php 
$host = "localhost";
$username = "MaherGuy";
$password = "V1tam1nC;23";
$database = "BigPharma";

// Create connection
$conn = new mysqli($host, $username, $password, $database);

// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}
?>