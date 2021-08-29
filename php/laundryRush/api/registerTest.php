<?php
 
//getting user values
$username=$_POST['username'];
$email=$_POST['email'];
$password=$_POST['password'];
 
//array of responses
$output=array();
 
//require database
require_once('databaseConfig.php');
 
//checking if email exists
$conn=$dbh

prepare('SELECT email FROM users WHERE email=?');
$conn-&gt;bindParam(1,$email);
$conn-&gt;execute();
 
//results
if($conn-&gt;rowCount() !==0){
$output['isSuccess'] = 0;
$output['message'] = "Email Exists Please Login";
}else{
 
$conn=$dbh-&gt;prepare('INSERT INTO users(username,email,password) VALUES (?,?,?)');
//encrypting the password
$pass=md5($password);
$conn-&gt;bindParam(1,$username);
$conn-&gt;bindParam(2,$email);
$conn-&gt;bindParam(3,$pass);
 
$conn-&gt;execute();
if($conn-&gt;rowCount() == 0)
{
$output['isSuccess'] = 0;
$output['message'] = "Registration failed, Please try again";
}
elseif($conn-&gt;rowCount() !==0){
$output['isSuccess'] = 1;
$output['message'] = "Succefully Registered";
$output['username']=$username;
}
}
echo json_encode($output);
 
?&gt;