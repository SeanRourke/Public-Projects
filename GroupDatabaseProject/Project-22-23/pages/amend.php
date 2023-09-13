<!--Screen: Amend Counter Stock Item-->
<!--Purpose: Amend data based off of inputted data-->
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
			<?php
				$sql = "UPDATE CounterStock SET
						description = '$_POST[amendDescription]',
						costPrice = '$_POST[amendCostPrice]',
						retailPrice = '$_POST[amendRetailPrice]',
						reorderLevel = '$_POST[amendReorderLevel]',
						reorderQuantity = '$_POST[amendReorderQuantity]',
						quantity = '$_POST[amendQuantityInStock]',
						supplierID = (
						SELECT supplierID 
						FROM Suppliers 
						WHERE Suppliers.supplierName = '$_POST[amendSupplierName]'
						)
						WHERE counterStockID = '$_POST[amendId]' "; //amend details based off input
		
				echo "<br>";
				if (! mysqli_query($conn,$sql ))
				{
					echo "Error ".mysqli_error($conn);
				}
				else
				{
					if (mysqli_affected_rows($conn) != 0)
					{
						echo mysqli_affected_rows($conn)." record(s) updated <br>";
						echo "Counter Stock Id ". $_POST['amendId'].", ".$_POST['amendDescription']." has been updated";
					}
					else
					{
						echo "No records were changed";
					}
				}
				mysqli_close($conn);
			?>
			<form action="amendCounterStock.php" method="post" />

				<button class="button" id="return">Return to Amend Item</button> <!--return to amend screen-->
			</form>
        </main>
    </div>
</body>
    <script src="../assets/js/date.js"></script>
</html>