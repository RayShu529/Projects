<?php
	$xml = new domdocument("1.0");
	$xml->load("BSIT3EG2G2.xml");
	$hsneakers = $xml->getElementsByTagName("hsneaker");
	$fname = $_REQUEST["q"];
	$hint = "";
	$flag=0;
	foreach($hsneakers as $hsneaker)
	{	
		$n = $hsneaker->getElementsByTagName("sname")->item(0)->nodeValue;

		if (strcasecmp($n, $fname) ==0)
		{
			echo " The name ".$fname." is already in the record.";
			$flag=1;
		}
	}
	if($flag==0){
		echo " The name ".$fname." is available.";
	}

?>