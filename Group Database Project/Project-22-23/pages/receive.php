<!--Screen: Receive Drug Delivery-->
<!--Purpose: Mark delivery as received-->
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
    <link rel="stylesheet" href="../assets/css/addCounterStock.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />
    <link href="https://cdn.jsdelivr.net/npm/remixicon@2.5.0/fonts/remixicon.css" rel="stylesheet"><!--link stylesheets-->
</head>
<body>
    <div class="horizonal-nav">	<!--setup page design-->
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
			<?php 
				session_start();
			?>
			<br>
			<br>
			<?php

				$orderID = $_POST['orderID'];
				$sql = "UPDATE Orders SET paid = true WHERE orderID = $orderID";	//set order to paid
				
				if(!mysqli_query($conn,$sql))
				{
						echo "Error".mysqli_error($conn);
				}
				
				$sql2 = "SELECT Drug.drugID, quantityInStock, quantity, orderID
						FROM Drug
						INNER JOIN Drugs_Orders ON Drug.DrugID = Drugs_Orders.DrugID
						WHERE orderID = $orderID";
						
				if (!$result = mysqli_query($conn, $sql2))
				{
					die( 'Error in querying the database' . mysqli_error($conn));
				}
						
				while ($row = mysqli_fetch_array($result))
				{
					$quantity = $row['quantity'];
					$drugID = $row['drugID'];
					$sqlupdate = "UPDATE Drug SET quantityInStock = quantityInStock + $quantity WHERE drugID = '$drugID' AND orderID = $orderID";
				}

				echo "Record marked as paid";

				mysqli_close($conn);

			?>
			<form action="receiveDrugDeliveries.php" method="post" >

				<button class="button" id="return">Return to Receive Drug Delivery</button> <!--return to recieve screen-->
			</form>
        </main>
    </div>
</body>
    <script src="../assets/js/date.js"></script>
</html>