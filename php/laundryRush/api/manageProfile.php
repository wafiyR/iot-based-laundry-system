<?php

if($_SERVER['REQUEST_METHOD']=='POST'){
	
	// establish connection
	
	require "databaseConfig.php";
	
	$sp_username = $_POST['sp_username']; //  get username from shared preferences of android  app
	$username = $_POST['username'];  
	$email = $_POST['email'];
	//$password = $_POST['password'];
	//$change_password = $_POST['change_password'];
	//$confirm_password = $_POST['confirm_password'];
	
		// an array of response
	$output = array();
	
/* 	if($username == '' || $email == '' || $password == ''){
		
		$output['response'] = 0;
		$output['message'] = "Parameter missing";
	} */
	
	// verify missing parameter?
	
	if ($username != '' && $email != ''){
		
		$check_username = "SELECT user_name FROM user_info WHERE user_name = '$username' ";
			
		$username_result = mysqli_query($connection, $check_username);
			
		$checkemail = "SELECT user_email FROM user_info WHERE user_email = '$email' ";
		
		$email_result = mysqli_query($connection, $checkemail);
		
		if (!filter_var($email, FILTER_VALIDATE_EMAIL)){
			
			$output['response'] = 0;
		    $output['message'] = "Invalid Email Format!";
			
		}else if(mysqli_num_rows($username_result) < 1 && mysqli_num_rows($email_result) < 1){
			
			$get_userid = "SELECT user_id FROM user_info WHERE user_name = '$sp_username' ";
			$userid_result = mysqli_query($connection, $get_userid);
			$row = mysqli_fetch_assoc($userid_result);				  
			$query="UPDATE user_info SET user_name = '$username', user_email = '$email'  WHERE user_id = '$row[user_id]'";	
			mysqli_query($connection, $query);	

			$output['response'] = 1;
		    $output['message'] = "Successfully Update Profile";
			
		}else{ // else if(mysqli_num_rows($username_result) > 1 && mysqli_num_rows($email_result) > 1)
			
			$output['response'] = 0;
			$output['message'] = "User Already Exist!";			
			
		}
		
		//echo json_encode($output);   // send json  // without this will get error: EOFException: End of input at line 1 column 1 path $	
		/*else if(mysqli_num_rows($username_result) > 1){
			
			$output['response'] = 0;
			$output['message'] = "Username Already Exist";
			
		}else if(mysqli_num_rows($email_result) > 1){
			
			$output['response'] = 0;
			$output['message'] = "Email Already Exist";
			
		}*/
			
		}else if ($username == '' && $email == ''){
			
			$output['response'] = 0;
			$output['message'] = "Missing Parameter";
			
	}

		echo json_encode($output);   // send json  // without this will get error: EOFException: End of input at line 1 column 1 path $	
		
		
	/*$check_username = "SELECT user_name FROM user_info WHERE user_name = '$username' ";
			
	$username_result = mysqli_query($connection, $check_username);
	
	// need to check whether parameter is empty, and username already exist yet before execute update sql
	// if not check, empty parameter will cause null reference error, and existed username cause data duplication
	
		if ($username != '' && mysqli_num_rows($username_result) < 1){
		
			$get_userid = "SELECT user_id FROM user_info WHERE user_name = '$sp_username' ";
			$userid_result = mysqli_query($connection, $get_userid);
			$row = mysqli_fetch_assoc($userid_result);				  
			$query="UPDATE user_info SET user_name = '$username' WHERE user_id = '$row[user_id]'";	
			mysqli_query($connection, $query);	

			$output['response'] = 1;
		    $output['message'] = "Username updated successfully";			
		
		}else if($username == ''){
		
			$output['response'] = 0;
			$output['message'] = "Missing Parameter";		
		
		}else{
				$output['response'] = 0;
				$output['message'] = "Username already exists";		
		}
	

// this code only update either username or email only, because of if statement run either one of them	
			
	$checkemail = "SELECT user_email FROM user_info WHERE user_email = '$email' ";
		
	$emailresult = mysqli_query($connection, $checkemail);
		
		if($email != '' && mysqli_num_rows($emailresult) < 1){
			
			$get_userid = "SELECT user_id FROM user_info WHERE user_name = '$sp_username' ";
			$userid_result = mysqli_query($connection, $get_userid);
			$row = mysqli_fetch_assoc($userid_result);				  
			$query="UPDATE user_info SET user_email = '$email' WHERE user_id = '$row[user_id]'";	
			mysqli_query($connection, $query);
			
			$output['response'] = 1;
		    $output['message'] = "Email updated successfully";
			
			}else if($email == ''){
				
				$output['response'] = 0;
				$output['message'] = "Missing Parameter";					
								
			}else{
				$output['response'] = 0;
				$output['message'] = "Email already exists";		
		}
		
	echo json_encode($output);*/   // send json  // without this will get error: EOFException: End of input at line 1 column 1 path $				
			
}

?>