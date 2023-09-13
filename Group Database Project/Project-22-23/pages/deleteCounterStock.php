<!--Screen: Delete Counter Stock Item-->
<!--Purpose: Fill in data for item to be deleted->
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
    <link rel="stylesheet" href="../assets/css/deleteCounterStock.css">
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

				function populate()
				{
					var sel = document.getElementById("listbox");	//fill input boxes with data
					var result;
					result = sel.options[sel.selectedIndex].value;
					var personDetails = result.split('+');		//split string in symbol
					document.getElementById("delId").value = personDetails[0];
					document.getElementById("delDescription").value = personDetails[1];
					document.getElementById("delCostPrice").value = personDetails[2];
					document.getElementById("delQuantity").value = personDetails[3];
					document.getElementById("delSupplierName").value = personDetails[4];
				}
				function confirmCheck() {
					var quantity = parseInt(document.getElementById("delQuantity").value);
					if (quantity === 0) {
						var response = confirm('Are you sure you want to delete this item?');	//prompt confirmation if none in stock
						if (response) {
							document.getElementById("delId").disabled = false;
							document.getElementById("delDescription").disabled = false;
							document.getElementById("delCostPrice").disabled = false;
							document.getElementById("delQuantity").disabled = false;
							document.getElementById("delSupplierName").disabled = false;
							return true;
						} 
						else {
							populate();
							return false;
						}
					} 
					else {
						alert('Cannot delete this item because the quantity in stock is not zero.');
						return false;
					}
				}
			</script>

			<p id="display"></p>
			<form name="deleteForm" action="delete.php" onsubmit="return confirmCheck()" method="post">
				<div class="mainForm">
					<div class="formText">
						<h2 class="heading">Delete Stock Item</h2>
						<div>
							<?php 
								include 'deleteListbox.php'; //listbox of items
							?>	
						</div>
						<br>
						<br>
						<div class="inputbox">
							<label for="delId">Counter Stock ID</label>
							<input type="text" name="delId" id="delId" disabled>
						</div>
						<br>
						<div class="inputbox">
							<label for="delDescription">Description</label>
							<input type="text" name="delDescription" id="delDescription" disabled>
						</div>
						<br>
						<div class="inputbox">
							<label for="delCostPrice">Cost Price</label>
							<input type="number" step="0.01" name="delCostPrice" id="delCostPrice" disabled>
						</div>
						<br>
						<div class="inputbox">
							<label for="delQuantity">Quantity in Stock</label>
							<input type="number" name="delQuantity" id="delQuantity" disabled>
						</div>
						<br>
						<div class="inputbox">
							<label for="delSupplierName">Supplier Name</label>
							<input type="text" name="delSupplierName" id="delSupplierName" disabled>
						</div>
						<br>
						<br>
						<button class="button">Delete</button>
					</div>
				</div>
			</form>
		</main>
	</div>
</body>
    <script src="../assets/js/date.js"></script>
</html>