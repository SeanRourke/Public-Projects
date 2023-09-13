<!--Screen: Receive Drug Delivery-->
<!--Purpose: Select Order to mark as paid-->
<!--C00251168, Seán Rourke, 03/23-->
<?php
include '../assets/php/db_connection.php';	//connect to database
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
    <link href="https://cdn.jsdelivr.net/npm/remixicon@2.5.0/fonts/remixicon.css" rel="stylesheet">	<!--link stylesheets-->
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
				function populate()
				{
					var sel = document.getElementById("listbox");	//fill input boxes with data
					var result;
					result = sel.options[sel.selectedIndex].value;
					var orderDetails = result.split('+');		//split string on symbol
					document.getElementById("ordID").value = orderDetails[0];
					document.getElementById("orderDate").value = orderDetails[1];
					document.getElementById("supplierName").value = orderDetails[2];
					document.getElementById("supplierAddress").value = orderDetails[3];
				}
				function confirmCheck()
				{
					var response;
					response = confirm('Are you sure you want to select this order?');		//prompt confirmation
					if (response)
					{
						document.getElementById("ordID").disabled = false;
						document.getElementById("orderDate").disabled = false;
						document.getElementById("supplierName").disabled = false;
						document.getElementById("supplierAddress").disabled = false;
						return true
					}
					else
					{
						populate();
						return false;
					}
				}
			</script>
			
			<form action="receiveDrugDeliveries2.php" onsubmit="return confirmCheck()" method="post">
				<div class="mainForm">
					<div class="formText">
						<h2>Order Details</h2>
						<div>
							<?php
								include "listboxOrders.php";	//box to select order
							?>
						</div>
					<br>
					<br>
					<div class="inputbox">
						<label for="ordID">Order ID</label>
						<input type="text" name="ordID" id="ordID" disabled>
					</div>
					<br>
					<div class="inputbox">
						<label for="orderDate">Order Date</label>
						<input type="text" name="orderDate" id="orderDate" disabled>
					</div>
					<br>
					<div class="inputbox">
						<label for="supplierName">Supplier Name</label>
						<input type="text" name="supplierName" id="supplierName" disabled>
					</div>
					<br>
					<div class="inputbox">
						<label for="supplierAddress">Supplier Address</label>
						<textarea type="text" name="supplierAddress" id="supplierAddress" disabled></textarea>
					</div>
					<br>
					<br>
					<br>
					<button class="button">Select Order</button>
					</div>
				</div>
			</form>
        </main>
    </div>
</body>
    <script src="../assets/js/date.js"></script>
</html>