<?php
	$xml = new domdocument("1.0");
	$xml->formatOutput = true;
	$xml->preserveWhiteSpace = false;
	$xml->load("BSIT3EG2G2.xml");

	$name = $_POST["sname"]; 
	$br = strtoupper($_POST["brand"]); 
	$initialprice = $_POST["srp"]; 
	$curprice= $_POST["cprice"]; 
	$date= $_POST["rdate"];
	$collaboration= $_POST["collab"]; 	
	$picture= $_POST["pic"];
	$hs = $xml->getElementsByTagName("hsneaker");
	$flag=0;
	foreach($hs as $h) 
	{
		$namecheck = $h->getElementsByTagName("sname")->item(0)->nodeValue;
			if(strcasecmp($name, $namecheck) ==0)
			{
				echo "<link rel='stylesheet' href='css/process.css'>
				<p>Name already in the record...<p><div><a href='add.php'>Back</a></div>";
				$flag=1;
			}
	}
	if($flag==0)
	{
	$hsneaker= $xml->createElement("hsneaker");  
	$sname = $xml->createElement("sname",$name); 
	$brand = $xml->createElement("brand",$br); 
	$srp = $xml->createElement("srp",$initialprice); 
	$cprice = $xml->createElement("cprice",$curprice); 
	$rdate = $xml->createElement("rdate",$date); 
	$collab = $xml->createElement("collab",$collaboration); 
	
	$pic = $xml->createElement("pic"); 
	$imageData = file_get_contents($picture);
	$base64 = base64_encode($imageData);
	$cdata = $xml->createCDATASection($base64);
	$pic->appendChild($cdata);
	
	$hsneaker->appendChild($sname); 
	$hsneaker->appendChild($brand); 
	$hsneaker->appendChild($srp);
	$hsneaker->appendChild($cprice);
	$hsneaker->appendChild($rdate);
	$hsneaker->appendChild($collab);
	$hsneaker->appendChild($pic);
	
	$xml->getElementsByTagName("hsneakers")->item(0)->appendchild($hsneaker); 
	$xml->save("BSIT3EG2G2.xml");
	echo "
	<link rel='stylesheet' href='css/process.css'>
	<p>Record saved...</p>
	<div><a href='add.php'>Back</a></div>";
	}
	
?>