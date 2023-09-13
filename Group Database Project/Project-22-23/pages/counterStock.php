<!--Screen: Menu-->
<!--Purpose: Provide options in counter stock section-->
<!--C00251168, Seán Rourke, 03/23-->
<?php
include '../assets/php/db_connection.php'; //connect to database
?>

<!-- 
    Icons obtained from https://remixicon.com/ and https://fonts.google.com/icons 
 -->

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Pharmacy</title>
    <link rel="stylesheet" href="../assets/css/counterStock.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />
    <link href="https://cdn.jsdelivr.net/npm/remixicon@2.5.0/fonts/remixicon.css" rel="stylesheet"> <!--link stylesheets-->
</head>
<body>
    <div class="horizonal-nav"> <!--setup page design-->
        <span id="time"></span>
            <div class="logo-container">
            <i class="ri-capsule-line"></i>
            <span id="logo-title"> | BP</span>
        </div>
        <div class="account-container">
            <button>
                <span class="accountId">Logout</span>
            </button>
        </div>
    </div>
    <div class="main-container">
        <div class="vertical-nav">
            <a href="#">Drugs</a>
			<a href="counterStock.php" class="selected">Stock Control</a>
			<a href="#">Doctor</a>
			<a href="#">Customer</a>
			<a href="#">Supplier</a>
        </div>
        <main>
            <!-- put your html here -->
			<div class="buttons">
				<a class="button" href="addCounterStock.php">Add Counter Stock</a>	<!--buttons to reach each section-->
				<a class="button" href="amendCounterStock.php">Amend Counter Stock</a>
				<a class="button" href="deleteCounterStock.php">Delete Counter Stock</a>
				<a class="button" href="receiveDrugDeliveries.php">Receive Drug Deliveries</a>
			</div>
        </main>
    </div>
</body>
    <script src="../assets/js/date.js"></script>
</html>