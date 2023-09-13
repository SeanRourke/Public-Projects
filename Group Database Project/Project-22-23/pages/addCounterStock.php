<!--Screen: Add Counter Stock Item-->
<!--Purpose: Fill in data for item to be added-->
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
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />
    <link href="https://cdn.jsdelivr.net/npm/remixicon@2.5.0/fonts/remixicon.css" rel="stylesheet">
	<link rel="stylesheet" href="../assets/css/addCounterStock.css"> <!--link stylesheets-->
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
			<form action="add.php" onsubmit="confirmAction()" method="Post">
				<div class="mainForm">
					<div class="formText">
						<h2 class="heading">Add Stock Item</h2>
						<div class="inputbox">
							<label for="desc">Description</label>
							<input type="text" name="desc" id="desc" required />
						</div>
						<br>
						<div class="inputbox">
							<label for="cost">Cost Price</label>
							<input type="number" step="0.01" name="cost" id="cost" required /> <!--allow 2 decimal places-->
						</div>
						<br>
						<div class="inputbox">
							<label for="retail">Retail Price</label>
							<input type="number" step="0.01" name="retail" id="retail" required />
						</div>
						<br>
						<div class="inputbox">
							<label for="reLevel">Reorder Level</label>
							<input type="number" name="reLevel" id="reLevel" required />
						</div>
						<br>
						<div class="inputbox">
							<label for="reQuantity">Reorder Quantity</label>
							<input type="number" name="reQuantity" id="reQuantity"  required />
						</div>
						<br>
						<div>
							<label for="supplierName">Supplier Name</label>
							<select name="supplierName" id="supplierName" required >
							<?php 
								$sql = "SELECT * FROM Suppliers";	//display supplierName and save ID of selected supplier
								$result = mysqli_query($conn, $sql);
								
								if ($result->num_rows > 0) {
									while($row = $result->fetch_assoc()) {	
										echo '<option value="' . $row['supplierID'] . '">' . $row['supplierName'] . '</option>';
									}
								}
							?>
							</select>
						</div>
						<br>
						<div class="input box">
							<label for="supplierCode">Supplier's Stock Code:</label>
							<input type="text" name="supplierCode" id="supplierCode" required />
						</div>
						<br>
						<script>
							function confirmAction(){
							var desc = document.getElementById('desc').value;
							var cost = document.getElementById('cost').value;
							var retail = document.getElementById('retail').value;
							var reLevel = document.getElementById('reLevel').value;
							var reQuantity = document.getElementById('reQuantity').value;
							var supplierName = document.getElementById('supplierName').value;
							var supplierCode = document.getElementById('supplierCode').value;
							if(desc && cost && retail && reLevel && reQuantity && supplierName && supplierCode){
								let confirmAction = confirm("Are you sure you want to add this Counter Stock Item?");	//prompt confirmation
								if (confirmAction){
									alert("Item added");
								} else{
									alert("Process Cancelled");
								}
							}
							else{
								alert("Fill in all details");
							}
						</script>
						<button class="button">Add</button>
						<button class="button" type="reset">Clear</button> <!--clear form-->
					</div>
				</div>
			</form>
        </main>
    </div>
</body>
    <script src="../assets/js/date.js"></script>
</html>