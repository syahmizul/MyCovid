<?php
	require('connection.php');
	try 
	{
		
		$icnum = $_POST['icnum'];
		$password = $_POST['password'];
		
		
		$checkuserstatement = "SELECT * FROM users WHERE icnum='$icnum' AND password='$password' ";
		if(mysqli_num_rows(mysqli_query($con,$checkuserstatement)) > 0)
		{
			echo "SUCCESS"; 
			return;
		}
   	 	else
		{
			echo "Account doesn't exist/Wrong password";
			return;
		}
	}
	catch(Exception $e) {
  		echo 'Message: ' .$e->getMessage();
	} 
?>