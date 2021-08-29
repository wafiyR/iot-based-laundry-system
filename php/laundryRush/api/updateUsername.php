
<?php

if($_SERVER['REQUEST_METHOD']=='POST'){
	
	// it's ultimately crucial to test your php script with user input from html first!!! in order to get error messages of php script if there's any
	// for example for this one is updateBalanceTest.html
	
	require "databaseConfig.php";
	
	$sp_username = $_POST['sp_username'];
	$username = $_POST['username'];
	
	// an array of response
	$output = array();
	
	if(!empty($username)){  // $sp_username != null --> not working?
	
	
			// need to check if username already exist or not
			
			$check_username = "SELECT user_name FROM user_info WHERE user_name = '$username' ";
			
			$username_result = mysqli_query($connection, $check_username);	
			
			if(strlen($username) < 6){
				
				$output['response'] = 0;
		        $output['message'] = "Username Must Be More than 6 Characters";
				
			}else if(mysqli_num_rows($username_result) < 1 ){
				
				$get_userid = "SELECT user_id FROM user_info WHERE user_name = '$sp_username' ";
				$userid_result = mysqli_query($connection, $get_userid);
				$row = mysqli_fetch_assoc($userid_result);
					
			
				
				//$query="UPDATE user_info SET ewallet_balance = '$topup' WHERE user_id = '$row2[user_id]'";	
				//$query="UPDATE user_info SET ewallet_balance = '$add_balance' WHERE user_id = '$row[user_id]'";	
				$query="UPDATE user_info SET user_name = '$username' WHERE user_id = '$row[user_id]'";
				mysqli_query($connection, $query);	

				$output['response'] = 1;
				$output['message'] = "Successfully Update Username!";					
				
			}else{
				$output['response'] = 0;
				$output['message'] = "Username Already Exist!";
				
			}
									
	
	}else if($username == ''){
		
			$output['response'] = 0;
			$output['message'] = "Missing Parameter";   // "Missing Parameter"
		
	}
	
	echo json_encode($output);   // send json  // without this will get error: EOFException: End of input at line 1 column 1 path $	
}

?>

