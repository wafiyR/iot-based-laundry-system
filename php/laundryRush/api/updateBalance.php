<?php

if($_SERVER['REQUEST_METHOD']=='POST'){
	
	// it's ultimately crucial to test your php script with user input from html first!!! in order to get error messages of php script if there's any
	// for example for this one is updateBalanceTest.html
	
	require "databaseConfig.php";
	
	$sp_username = $_POST['sp_username'];
	$topup = $_POST['topup'];
	
	$float_topup = floatval($topup);
	
	// an array of response
	$output = array();
	
	if(!empty($topup)){  // $sp_username != null --> not working?
		
			$get_userid = "SELECT user_id FROM user_info WHERE user_name = '$sp_username' ";
			$userid_result = mysqli_query($connection, $get_userid);
			$row = mysqli_fetch_assoc($userid_result);
			
			$get_balance = "SELECT ewallet_balance FROM user_info WHERE user_id = '$row[user_id]' ";  // user_name = '$sp_username'
			$balance_result = mysqli_query($connection, $get_balance);
			$row2 = mysqli_fetch_assoc($balance_result);
			
			$float_balance = floatval($row2['ewallet_balance']);
			
			$add_balance = $float_topup + $float_balance;
				
			//$query="UPDATE user_info SET ewallet_balance = '$topup' WHERE user_id = '$row2[user_id]'";	
			$query="UPDATE user_info SET ewallet_balance = '$add_balance' WHERE user_id = '$row[user_id]'";	
			mysqli_query($connection, $query);	

			$output['response'] = 1;
			$output['message'] = "EWallet topup successfully!";	
				
			
	
	}else if($float_topup == ''){
		
			$output['response'] = 0;
			$output['message'] = "Amount cannot be less than zero!";   // "Missing Parameter"
		
	}else if($float_topup <= 0){
		
			$output['response'] = 0;
			$output['message'] = "Amount cannot be less than zero!";
		
	}
	
	echo json_encode($output);   // send json  // without this will get error: EOFException: End of input at line 1 column 1 path $	
}

?>