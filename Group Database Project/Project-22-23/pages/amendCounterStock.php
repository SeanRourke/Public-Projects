<!--Screen: Amend Counter Stock Item-->
<!--Purpose: Fill in data for item to be amended-->
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
    <link rel="stylesheet" href="../assets/css/amendCounterStock.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />
    <link href="https://cdn.jsdelivr.net/npm/remixicon@2.5.0/fonts/remixicon.css" rel="stylesheet"><!--link stylesheets-->
</head>
<body>
    <div class="horizonal-nav"><!--setup page design-->
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
				var counterStockDetails = result.split('+');	//split string on symbol
				document.getElementById("amendId").value = counterStockDetails[0];
				document.getElementById("amendDescription").value = counterStockDetails[1];
				document.getElementById("amendCostPrice").value = counterStockDetails[2];
				document.getElementById("amendRetailPrice").value = counterStockDetails[3];
				document.getElementById("amendReorderLevel").value = counterStockDetails[4];
				document.getElementById("amendReorderQuantity").value = counterStockDetails[5];
				document.getElementById("amendQuantityInStock").value = counterStockDetails[6];
				document.getElementById("amendSupplierName").value = counterStockDetails[7];
			}

			function toggleLock()
			{
				if (document.getElementById("amendviewbutton").value == "Amend Details")//allow editing
				{
					document.getElementById("amendDescription").disabled = false;
					document.getElementById("amendCostPrice").disabled = false;
					document.getElementById("amendRetailPrice").disabled = false;
					document.getElementById("amendReorderLevel").disabled = false;
					document.getElementById("amendReorderQuantity").disabled = false;
					document.getElementById("amendQuantityInStock").disabled = false;
					document.getElementById("amendSupplierName").disabled = false;
					document.getElementById("amendviewbutton").value = "View Details";
				}
				else
				{
					document.getElementById("amendDescription").disabled = true;	//disable editing
					document.getElementById("amendCostPrice").disabled = true;
					document.getElementById("amendRetailPrice").disabled = true;
					document.getElementById("amendReorderLevel").disabled = true;
					document.getElementById("amendReorderQuantity").disabled = true;
					document.getElementById("amendQuantityInStock").disabled = true;
					document.getElementById("amendSupplierName").disabled = true;
					document.getElementById("amendviewbutton").value = "Amend Details";
				}
			}
			function confirmCheck()
			{
				var response;
				response = confirm('Are you sure you want to save these changes?');	//prompt confirmation
				if (response)
				{
					document.getElementById("amendId").disabled = false;
					document.getElementById("amendDescription").disabled = false;
					document.getElementById("amendCostPrice").disabled = false;
					document.getElementById("amendRetailPrice").disabled = false;
					document.getElementById("amendReorderLevel").disabled = false;
					document.getElementById("amendReorderQuantity").disabled = false;
					document.getElementById("amendQuantityInStock").disabled = false;
					document.getElementById("amendSupplierName").disabled = false;
					return true
				}
				else
				{
					populate();
					toggleLock();
					return false;
				}
			}
			</script>
			<p id="display"> </p>
			<form action="amend.php" onsubmit="return confirmCheck()" method="post">
				<div class="mainForm">
					<div class="formText">
						<h2 class="heading">Amend Stock Item</h2>
						<div>
							<?php
								include 'listboxCounterStock.php';	//box to select item
							?>
						</div>
						<br>
						<br>
						<div class="inputbox">
							<label for="amendId">Counter Stock ID </label>
							<input type="text" name="amendId" id="amendId" disabled>
						</div>
						<br>
						<div class="inputbox">
							<label for="amendDescription">Description </label>
							<input type="text" name="amendDescription" id="amendDescription" required disabled>
						</div>
						<br>
						<div class="inputbox">
							<label for="amendCostPrice">Cost Price </label>
							<input type="number" step="0.01" name="amendCostPrice" id="amendCostPrice" required disabled>
						</div>
						<br>
						<div class="inputbox">
							<label for="amendRetailPrice">Retail Price </label>
							<input type="number" step="0.01" name="amendRetailPrice" id="amendRetailPrice" required disabled>
						</div>
						<br>
						<div class="inputbox">
							<label for="amendReorderLevel">Reorder Level </label>
							<input type="number" name="amendReorderLevel" id="amendReorderLevel" required disabled>
						</div>
						<br>
						<div class="inputbox">
							<label for="amendReorderQuantity">Reorder Quantity</label>
							<input type="number" name="amendReorderQuantity" id="amendReorderQuantity" required disabled>
						</div>
						<br>
						<div class="inputbox">
							<label for="amendQuantityInStock">Quantity in Stock </label>
							<input type="number" name="amendQuantityInStock" id="amendQuantityInStock" required disabled>
						</div>
						<br>
						<div class="inputbox">
							<label for="amendSupplierName">Supplier Name</label>
							<input type="text" name="amendSupplierName" id="amendSupplierName" required disabled>
						</div>
						<br>
						<input class="button" type="button" value="Amend Details" id="amendviewbutton" onclick="toggleLock()">
						<button class="button">Save Changes</button>
					</div>
				</div>
			</form>
        </main>
    </div>
</body>
    <script src="../assets/js/date.js"></script>
</html>