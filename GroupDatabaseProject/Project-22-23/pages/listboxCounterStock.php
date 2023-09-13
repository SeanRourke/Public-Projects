<!--Screen: Counter Stock Listbox-->
<!--Purpose: Retrieve data for selected item-->
<!--C00251168, Seán Rourke, 03/23-->
<!--Doctype HTML-->
<?php
	include '../assets/php/db_connection.php';//connect to database
	date_default_timezone_set('UTC');
		//query for data based on criteria
	$sql = "SELECT counterStockID, description, costPrice, retailPrice, reorderLevel, reorderQuantity,quantity, supplierName
			FROM CounterStock 
			INNER JOIN Suppliers  ON CounterStock.supplierID = Suppliers.supplierID
			WHERE CounterStock.deleted = 0";

	if (!$result = mysqli_query($conn, $sql))
	{
		die( 'Error in querying the database' . mysqli_error($conn));
	}

	echo "<br><select name='listbox' id='listbox' onclick='populate()'>";	//listbox that calls populate function on selection

	while ($row = mysqli_fetch_array($result))	//loop through results
	{	//store data in variables
		$id = $row['counterStockID'];
		$description = $row['description'];
		$costPrice = $row['costPrice'];
		$retailPrice = $row['retailPrice'];
		$reorderLevel = $row['reorderLevel'];
		$reorderQuantity = $row['reorderQuantity'];
		$quantityInStock = $row['quantity'];
		$supplier = $row['supplierName'];
		$allText = "$id+$description+$costPrice+$retailPrice+$reorderLevel+$reorderQuantity+$quantityInStock+$supplier";	//concatanate data to string
		echo "<option value = '$allText'>$description</option>";			//listbox display
	}
	echo "</select>";

?>