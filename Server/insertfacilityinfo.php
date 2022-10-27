<?php
	require('connection.php');
	require('generateauthkey.php');
	try 
	{
		
		$name = $_POST['name'];
		$address = $_POST['address'];
		$icnum   = $_POST['icnum'];
		$authkey = random_str(18);
		
		
		$jsonObject->name = $name;
		$jsonObject->address = $address;
		$jsonObject->authkey = $authkey;
		
		$insertstatement = "INSERT INTO facilities ( name, address, authkey,registeredby ) VALUES ('$name','$address','$authkey','$icnum') ";
		if(mysqli_query($con,$insertstatement))
		{
			$jsonObject->status = "SUCCESS";
			
			$selectstatement = "SELECT * FROM facilities WHERE authkey = '$authkey'";
			
			$selectresult = mysqli_query($con,$selectstatement);
			$jsonObject->id = mysqli_fetch_assoc($selectresult)["id"];
		}
		else
			$jsonObject->status = "FAILED";
		
		echo json_encode($jsonObject);
    
	}
	catch(Exception $e) {
  		echo 'Message: ' .$e->getMessage();
	} 
?>