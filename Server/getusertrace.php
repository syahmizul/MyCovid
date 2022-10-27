<?php
	require('connection.php');
	try 
	{
		
		$icnum = $_POST['icnum'];
		
		$checkuserstatement = "SELECT trace.id,trace.tracerIC,trace.datetime,facilities.name AS facilityname FROM trace LEFT JOIN facilities ON trace.tracerfacilityid = facilities.id WHERE tracerIC='$icnum'";
		if($query = mysqli_query($con,$checkuserstatement))
		{
			echo json_encode(mysqli_fetch_all($query));
			return;
		}
   	 	else
		{
			echo "CANT GET USER TRACES";
			return;
		}
	}
	catch(Exception $e) {
  		echo 'Message: ' .$e->getMessage();
	} 
?>