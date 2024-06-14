<?php
	$xml = new domdocument("1.0");
	$xml->load("BSIT3EG2G2.xml");
	$hsneakers = $xml->getElementsByTagName("hsneaker");

	$dname = $_POST["sname"];

	foreach($hsneakers as $hsneaker) 
	{
		$name = $hsneaker->getElementsByTagName("sname")->item(0)->nodeValue;
		
		if($name == $dname)
		{
			$xml->getElementsByTagName("hsneakers")->item(0)->removeChild($hsneaker);
			$xml->save("BSIT3EG2G2.xml");
			
			echo "
			<link rel='stylesheet' href='css/process.css'>
			<p>Record deleted!</p>
			<div>
			<a href='delete.php'>Back</a>	</div>";
			
			break;
		}
	}	
?>