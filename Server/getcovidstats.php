<?php
	require('connection.php');

	$handle = fopen("https://raw.githubusercontent.com/MoH-Malaysia/covid19-public/main/epidemic/cases_malaysia.csv","r");
	
	
	$i = 0;
	$latestdata;
	while (($data = fgetcsv($handle)) !== FALSE) 
	{
		if($i > 0)
		{
			// foreach($data as $var)
				// echo $var . ' ';  
			// echo '<br>';
			$latestdata = $data;
		}
		$i++;
	}
	
	echo json_encode($latestdata); 
	// foreach($latestdata as $var)
		// echo $var . ' ';  

	fclose($handle);

?>