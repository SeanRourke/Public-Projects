<!--Screen: Orders Listbox-->
<!--Purpose: Retrieve data for selected item-->
<!--C00251168, Seán Rourke, 03/23-->
<!--Doctype HTML-->
<?php
	include '../assets/php/db_connection.php';//connect to database
	date_default_timezone_set('UTC');
		//query for order based on criteria
	$sql = "SELECT orderID, date, supplierName, address
			FROM Orders 
			INNER JOIN Suppliers  ON Orders.supplierID = Suppliers.supplierID
			WHERE Orders.paid = 0";
			
	if (!$result = mysqli_query($conn, $sql))
	{
		die( 'Error in querying the database' . mysqli_error($conn));
	}

	echo "<select name='listbox' id='listbox' onclick='populate()'>";	//listbox that calls populate function on selection

	while ($row = mysqli_fetch_array($result))	//loop through results
		{	//store data in variables
			$id = $row['orderID'];
			$date = date_create( $row['date']);
			$date = date_format($date, "Y-m-d");
			$supplierName = $row['supplierName'];
			$supplierAddress = $row['address'];
			$allText = "$id+$date+$supplierName+$supplierAddress";		//concatanate data to string
			echo "<option value = '$allText'>$id</option>";			//listbox display
		}
			
	echo "</select>";
?>