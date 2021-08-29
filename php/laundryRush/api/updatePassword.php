
<?php

if($_SERVER['REQUEST_METHOD']=='POST'){
	
	// it's ultimately crucial to test your php script with user input from html first!!! in order to get error messages of php script if there's any
	// for example for this one is updateBalanceTest.html
	
	require "databaseConfig.php";
	
	$sp_username = $_POST['sp_username'];
	$curr_password = $_POST['curr_password'];
	$new_password = $_POST['new_password'];
	$confirm_password = $_POST['confirm_password'];
	
	
	// an array of response
	$output = array();
	
	
	if(!empty($curr_password) && !empty($new_password) && !empty($confirm_password)){  
	
	
			// need to check if current password input match or not
			
			$check_password = "SELECT password FROM user_info WHERE user_name = '$sp_username' ";
			
			$password_result = mysqli_query($connection, $check_password);	
			
			$db_password = mysqli_fetch_assoc($password_result);
			
					
			$encrypt_pass = md5($curr_password);
			
			$new_encrypt_pass = md5($new_password);
			
			
			if($encrypt_pass != $db_password['password']){
				
			    $output['response'] = 0;
		        $output['message'] = "Wrong Current Password!";
				
			}else if (strlen($new_password) < 6){
				
				$output['response'] = 0;
		        $output['message'] = "New Password Must Be More than 6 Characters";
				
			}else if($new_password != $confirm_password) {
				
			    $output['response'] = 0;
		        $output['message'] = "New Password Didn't Match!";				
				
			}else if($db_password['password'] == $new_encrypt_pass){
				
			    $output['response'] = 0;
		        $output['message'] = "New Password Cannot be Same as Old Password!";					
				
			}else{
				
				//$new_encrypt_pass = md5($new_password);
				
				$get_userid = "SELECT user_id FROM user_info WHERE user_name = '$sp_username' ";
				$userid_result = mysqli_query($connection, $get_userid);
				$row = mysqli_fetch_assoc($userid_result);
						
				$query="UPDATE user_info SET password = '$new_encrypt_pass' WHERE user_id = '$row[user_id]'";
				mysqli_query($connection, $query);	

				$output['response'] = 1;
				$output['message'] = "Successfully Update Password!";				
				
			}
					
			
	}else{
			$output['response'] = 0;
			$output['message'] = "Missing Parameter";		
	}
			
			
	
	echo json_encode($output);   // send json  // without this will get error: EOFException: End of input at line 1 column 1 path $	
}

?>

