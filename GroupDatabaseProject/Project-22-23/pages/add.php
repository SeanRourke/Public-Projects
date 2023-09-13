<!--Screen: Add Counter Stock Item-->
<!--Purpose: Add an entry to the Counter Stock Table with all relevant details-->
<!--C00251168, Seán Rourke, 03/23-->
<?php
include '../assets/php/db_connection.php'; // connect to database
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
			<?php
				
				$sql = "SELECT counterStockID FROM CounterStock";
				
				$result = mysqli_query($conn,$sql);
				
				$last_id = 0;
				
				while($row = mysqli_fetch_array($result)) {
						$last_id = $row['counterStockID'];
				}
				
				$new_id = $last_id + 1;	//increment id
			
			
				$sql = "Insert into CounterStock (counterStockID,description,costPrice,retailPrice,reorderLevel,reorderQuantity,supplierID,supplierStockCode)
				Values ('$new_id','$_POST[desc]','$_POST[cost]','$_POST[retail]','$_POST[reLevel]','$_POST[reQuantity]','$_POST[supplierName]','$_POST[supplierCode]')";
					echo "<br>";	//add data to table
					echo "New stock item created with the ID " . $new_id;

				if (!mysqli_query($conn,$sql))
				{
					die ("An Error in the SQL Query: " . mysqli_error($conn));
				}
				
				mysqli_close($conn);
			?>
			<form action="addCounterStock.php" method="post" />

				<button class="button" id="return">Return to Add Item</button> <!--return to add screen-->
			</form>
        </main>
    </div>
</body>
    <script src="../assets/js/date.js"></script>
</html>