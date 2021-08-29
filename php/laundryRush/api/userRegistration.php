<?php


if($_SERVER['REQUEST_METHOD']=='POST'){

    //header("Content-Type: application/json; charset=UTF-8");

	// establish connection
	
	require "databaseConfig.php";
	
	// Getting user input using POST request from RegisterActivity(Android Studio file)
	
	$username = $_POST['username'];
	$email = $_POST['email'];
	$password = $_POST['password'];
	$ewallet_balance = 0.00;
	$status = "Active";
	
	// an array of response
	$output = array();
	
	if($username == '' || $email == '' || $password == ''){
		
		//$state = "Missing Parameter";
		/* echo json_encode (array("status" => "false", "message" => "Parameter missing!")); */
		//echo json_encode(array("response" => $state));
		//$output['response'] = "Error";
		$output['response'] = 0;
		$output['message'] = "Missing Parameter";
		
	} else{
		
		$checkemail = "SELECT user_email FROM user_info WHERE user_email = '$email' ";
		
		$emailresult = mysqli_query($connection, $checkemail);
		
		if (!filter_var($email, FILTER_VALIDATE_EMAIL)){
			
			$output['response'] = 0;
		    $output['message'] = "Invalid Email Format!";
			
		}else if(mysqli_num_rows($emailresult) > 0){
			
			//$state = "Email Exist";
			/* echo json_encode(array("status" => "false", "message" => "Email already exist!")); */
			//echo json_encode(array("response" => $state));
			//$output['response'] = "Error";
			$output['response'] = 0;
		    $output['message'] = "Email Already Exists!";
		}else{
			
			$checkusername = "SELECT user_name FROM user_info WHERE user_name = '$username' ";
			
			$usernameresult = mysqli_query($connection, $checkusername);
			
			if(mysqli_num_rows($usernameresult) > 0){
				
				//$state = "Username Exist";
				/* echo json_encode(array("status" => "false", "message" => "Username already exist!") ); */
				//echo json_encode(array("response" => $state));
			    //$output['response'] = "Error";
				$output['response'] = 0;
		        $output['message'] = "Username Already Exists!";
			}else if(strlen($username) < 6){
				
							   
				$output['response'] = 0;
		        $output['message'] = "Username Must Be More than 6 Characters";
				
			}else if(strlen($password) < 6){
				
				$output['response'] = 0;
		        $output['message'] = "Password Must Be More than 6 Characters";
				
			}else{
				
				// when use md5 to encrypt password, make sure in phpmyadmin
				// database, the password column has 255 length, cause md5 change password string to lengthy words
				//and 255 is max length in phpmyadmin
				$encrypt_pass = md5($password);
				
				$registeruser = "INSERT INTO user_info (user_name,user_email,password,ewallet_balance,status) VALUES ('$username','$email','$encrypt_pass','$ewallet_balance','$status')";
				
				if(mysqli_query($connection, $registeruser)){
					
					$checkregister = "SELECT user_name FROM user_info WHERE user_name='$username'";
					
					$register_result = mysqli_query($connection, $checkregister);
					
					$emparray = array();
					
					if(mysqli_num_rows($register_result) > 0){
						while($row = mysqli_fetch_assoc($register_result)){
							$emparray[] = $row;
						}
					}
					//$state = "Success";
/* 					 echo json_encode(array("response" => "true","message" => "Successfully registered!", "data" => $emparray)); */ 
					//echo json_encode(array("response"=>$state));
				    //$output['response'] = "Success";
					$output['response'] = 1;
		            $output['message'] = "Successfully Registered!";
				}else{
					//$state = "Error";
					/* echo json_encode(array("status" => "false","message" => "Error occured, please try again!")); */
					//echo json_encode(array("response"=>$state ));
					//$output['response'] = "Error";
					$output['response'] = 0;
		            $output['message'] = "Error occured, please try again";
				}
			}
		}	
	}
	echo json_encode($output);		// send json  // without this will get error: EOFException: End of input at line 1 column 1 path $ 
}	

?>