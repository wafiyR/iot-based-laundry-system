<?php

	// Define your host here.
	$hostname = "localhost";

	// Define your database username here.
	$hostusername = "root";

	// Define your database password here.
	$hostpassword = "";

	// Define your database name here.
	$databasename = "laundryrush";

	// create connection
	$connection = mysqli_connect($hostname,$hostusername,$hostpassword,$databasename);
	
	// Check connection
	if (mysqli_connect_errno()){
		echo "Connection failed: " . mysqli_connect_error();
  }else{  
		//echo "Connect"; --> no need, interrupt with json response e.g supposedly json response get : {"response":1,"message":"Registered successfully"}, 
		//but because of echo "Connect", get : Connect{"response":1,"message":"Registered successfully"}
   }

?>