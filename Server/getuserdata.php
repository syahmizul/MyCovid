<?php
	require('connection.php');
	try 
	{
		
		$icnum = $_POST['icnum'];
		
	
		$checkuserstatement = "SELECT * FROM users WHERE icnum='$icnum'";
		if($query = mysqli_query($con,$checkuserstatement))
		{
			echo json_encode(mysqli_fetch_assoc($query));
			return;
		}
   	 	else
		{
			echo "CANT GET USER DATA";
			return;
		}
	}
	catch(Exception $e) {
  		echo 'Message: ' .$e->getMessage();
	} 
?>