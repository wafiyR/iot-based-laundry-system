<?php

if($_SERVER['REQUEST_METHOD']=='POST'){
	
	require "databaseConfig.php";
	
	$amount = $_POST['amount'];
	$sp_username = $_POST['sp_username'];
	
	$float_amount = floatval($amount);
	
	$output = array();
	
	$get_userid = "SELECT user_id FROM user_info WHERE user_name = '$sp_username' ";
	$userid_result = mysqli_query($connection, $get_userid);
	$row = mysqli_fetch_assoc($userid_result);
			
	$get_balance = "SELECT ewallet_balance FROM user_info WHERE user_id = '$row[user_id]' ";  // user_name = '$sp_username'
	$balance_result = mysqli_query($connection, $get_balance);
	$row2 = mysqli_fetch_assoc($balance_result);
			
	$check_balance = floatval($row2['ewallet_balance']);
	
	$check_enough_amt = $check_balance - $float_amount;
	
	if($amount == ''){
		
		$output['response'] = 0;
		$output['message'] = "Please Enter Amount to Pay!";
		
	}else if ($float_amount < 4){
		
		$output['response'] = 0;
		$output['message'] = "Insufficient Amount!";		
		
	}else if ($check_balance < 4){ // check if eWallet balance is less than 4
		
		$output['response'] = 0;
		$output['message'] = "Insufficient EWallet Balance! Please Topoup Balance!";		
		
	}else if ($check_enough_amt < 0){ // $check_enough_amt = $check_balance - $float_amount;--> need to add a condition, when user insert amount more than available ewallet balance, display error
	
		$output['response'] = 0;
		$output['message'] = "Insufficient EWallet Balance! Please Topoup Balance!";
		
	}else{  
		
			/*$get_userid = "SELECT user_id FROM user_info WHERE user_name = '$sp_username' ";
			$userid_result = mysqli_query($connection, $get_userid);
			$row = mysqli_fetch_assoc($userid_result);
			
			$get_balance = "SELECT ewallet_balance FROM user_info WHERE user_id = '$row[user_id]' ";  // user_name = '$sp_username'
			$balance_result = mysqli_query($connection, $get_balance);
			$row2 = mysqli_fetch_assoc($balance_result);
			
			$float_balance = floatval($row2['ewallet_balance']);*/
			
			//$subtract_balance = $float_amount - $float_balance;
			
			$subtract_balance = $check_balance - $float_amount;
			
			$addfinance = "INSERT INTO laundry_finance (profit) VALUES ('$float_amount')";
			mysqli_query($connection, $addfinance);
				
			//$query="UPDATE user_info SET ewallet_balance = '$topup' WHERE user_id = '$row2[user_id]'";	
			$query="UPDATE user_info SET ewallet_balance = '$subtract_balance' WHERE user_id = '$row[user_id]'";	
			mysqli_query($connection, $query);	

			$output['response'] = 1;
			$output['message'] = "Payment done successfully!";	
	}
	
	
	echo json_encode($output); // send json  // without this will get error: EOFException: End of input at line 1 column 1 path $
	
}

?>