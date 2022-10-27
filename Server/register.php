<?php
	require('connection.php');
	date_default_timezone_set('Asia/Singapore');
	try 
	{
		
		$currentdate = date('Y-m-d H:i:s');
		$icnum = $_POST['icnum'];
		$fullname = $_POST['fullname'];
		$address = $_POST['address'];
		$phonenum = $_POST['phonenum'];
		$password = $_POST['password'];
		
		
		$checkuserstatement = "SELECT * FROM users WHERE icnum='$icnum'";
		if(mysqli_num_rows(mysqli_query($con,$checkuserstatement)) > 0)
		{
			echo "IC Number already exist.Try again."; 
			return;
		}
   	 	$insertstatement ="INSERT INTO users (icnum, fullname, address, phonenum, password, registerdatetime, vaccinelevel, riskstats) VALUES ('$icnum', '$fullname', '$address', '$phonenum', '$password', '$currentdate', '0', 'Low Risk')";
		if (mysqli_query($con,$insertstatement)) 
			echo "SUCCESS";
		else
			echo "Failed.Try again."; 
	
    
	}
	catch(Exception $e) {
  		echo 'Message: ' .$e->getMessage();
	} 
?>