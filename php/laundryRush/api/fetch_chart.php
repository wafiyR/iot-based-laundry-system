<?php

if($_SERVER['REQUEST_METHOD']=='POST'){
	
		require "databaseConfig.php";
		
		$response = array();
		
		$query = "SELECT profit FROM laundry_finance";
		
		$result = mysqli_query($connection, $query);
		
		
		
		echo json_encode($response);
}

?>