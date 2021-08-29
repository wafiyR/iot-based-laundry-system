<?php

if($_SERVER['REQUEST_METHOD']=='POST'){
	
	require "databaseConfig.php";
	
	$sp_username = $_POST['sp_username'];
	//$username = $_POST['username'];
	$status = "Deactivate";
	
	// an array of response
	$output = array();
	
	$get_userid = "SELECT user_id FROM user_info WHERE user_name = '$sp_username' ";
	$userid_result = mysqli_query($connection, $get_userid);
	$row = mysqli_fetch_assoc($userid_result);
	
	$query="UPDATE user_info SET status = '$status' WHERE user_id = '$row[user_id]'";
	mysqli_query($connection, $query);	

	$output['response'] = 1;
	$output['message'] = "Successfully Delete Account!";
	
	echo json_encode($output);   // send json  // without this will get error: EOFException: End of input at line 1 column 1 path $	
	
}


?>