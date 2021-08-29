<?php

if($_SERVER['REQUEST_METHOD']=='POST'){
	
		require "databaseConfig.php";
		
		$sp_username = $_POST['balance'];
		
		$response = array();
		
		$query = "SELECT ewallet_balance FROM user_info WHERE user_name = '$sp_username'";
		
		$result = mysqli_query($connection, $query);
		
		while($row = mysqli_fetch_array($result)){
			// array_push($response,array('username'=>$row['user_name'], 'email'=>$row['user_email']));
			array_push($response,array('balance'=>$row['ewallet_balance']));
		}
		
		echo json_encode($response);
	
}

?>