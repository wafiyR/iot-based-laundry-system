<?php


if($_SERVER['REQUEST_METHOD']=='POST'){
	
	// establish connection
	require "databaseConfig.php";
	
	$username = $_POST['username'];
	$password = $_POST['password'];
	
	//$encrypt_pass = md5($password);
	
	// an array of response
	$output = array();
	
	if($username == '' || $password == ''){
		
		/* echo json_encode(array("status" => "false", "message" => "Parameter missing")); */
		$output['response'] = 0;
		$output['message'] = "Parameter missing";
	}else{
						
		$encrypt_pass = md5($password);
				
		$checklogin = "SELECT user_name,password FROM user_info WHERE user_name='$username' AND password='$encrypt_pass' LIMIT 1";
				
		$result_login = mysqli_query($connection, $checklogin);
				
		if(mysqli_num_rows($result_login) == 1){
			
			/* $userlogin = "SELECT user_name,password FROM user_info WHERE user_name='$username' AND password = '$password'"; */
			$userlogin = "SELECT user_name FROM user_info WHERE user_name='$username'";
			
			
			$result = mysqli_query($connection, $userlogin);
			
			$emparray = array();
			
			if(mysqli_num_rows($result) > 0){
				
				while($row = mysqli_fetch_assoc($result)){
					
					$emparray[] = $row;
				}								
			}
			$output['response'] = 1;
		    $output['message'] = "Login Successfully";
			//$state = "Success";
			//echo json_encode(array("response"=>$state, "username"=>$emparray));
/* 			echo json_encode(array("status" => "true","message" => "Login Successfully!", "data" => $emparray)); */
		} else{
			
 			//echo json_encode(array("status" => "false", "message" => "Invalid username or password!")); 
			
			$output['response'] = 0;
		    $output['message'] = "Invalid username or password!";
		} 				
				
			
			
		

		//mysqli_close($connection);
	
	}
	echo json_encode($output);
}



?>