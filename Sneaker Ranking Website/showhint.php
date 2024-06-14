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

		if (strcasecmp($fname,substr($n,0,strlen($fname))) ==0)
		{
			echo "<span>".$n."</span><br>";
			$flag=1;
		}
		$b = $hsneaker->getElementsByTagName("collab")->item(0)->nodeValue;

		if (strcasecmp($fname,substr($b,0,strlen($fname))) ==0)
		{
			echo "<span>".$b."</span><br> ";
			$flag=1;
		}
	}
	if($flag==0){
		echo "No suggestion";
	}

?>