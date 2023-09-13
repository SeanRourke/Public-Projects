<!--Screen: Receive Drugs Delivery-->
<!--Purpose: VIew Drugs on Order-->
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
    <link rel="stylesheet" href="../assets/css/receiveDrugs.css">
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
			<script>
				function confirmCheck()
				{
					var response;
					response = confirm('Are you sure you want to mark this order as received?');	//prompt confirmation
					if (response)
					{
						document.getElementById("orderID").disabled = false;
						return true
					}
					else
					{
						populate();
						return false;
					}
				}
			</script>
			<form action="receive.php" onsubmit="return confirmCheck()" method="post">
				<div class="mainForm">
					<div class="formText">
						<h2>Drug Details</h2>
						<div>
						<?php
							$orderID = $_POST['ordID'];	//get order ID
								//query data based on criteria
							$sql = "SELECT brandName, form, strength, quantity, Drug.drugID, supplierCode
									FROM Drug
									INNER JOIN Drugs_Orders  ON Drug.drugID = Drugs_Orders.drugID
									WHERE Drugs_Orders.orderID = $orderID";
						?>
						<div class="inputbox">
							<label for="orderID">Order ID</label>
							<input type="text" name="orderID" id="orderID" value="<?php echo $orderID?>" disabled>
						</div>
						<br>
						<br>
						<?php

							if (!$result = mysqli_query($conn, $sql))
							{
								die( 'Error in querying the database' . mysqli_error($conn));
							}

							while ($row = mysqli_fetch_array($result))
							{
								echo "Name: " . $row['brandName'] . "<br>";
								echo "Form: " .  $row['form'] . "<br>";
								echo "Strength: " . $row['strength'] . "<br>";
								echo "Quantity Ordered: " . $row['quantity'] . "<br>";
								echo "Drug ID: " . $row['drugID'] . "<br>";
								echo "Supplier Drug Code: " . $row['supplierCode'] . "<br><br>";		//print data
							}
						?>
					<br>
					<br>
					<button class="button">Mark as Received</button>
					
					</div>
				</div>
			</form>
        </main>
    </div>
</body>
    <script src="../assets/js/date.js"></script>
</html>