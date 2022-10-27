<?php
	require('connection.php');
	date_default_timezone_set('Asia/Singapore');
	try 
	{
		$currentdate = date('Y-m-d H:i:s');
		$traceric = $_POST['traceric'];
		$tracerfacilityid = $_POST['tracerfacilityid'];
		
		


		$insertstatement = "INSERT INTO trace ( tracerIC, tracerfacilityid, datetime ) VALUES ('$traceric','$tracerfacilityid','$currentdate') ";
		if(mysqli_query($con,$insertstatement))
		{
			echo "SUCCESS";
			return;
		}
		else
		{
			echo "FAILED";
			return;
		}
		
    
	}
	catch(Exception $e) {
  		echo 'Message: ' .$e->getMessage();
	} 
?>