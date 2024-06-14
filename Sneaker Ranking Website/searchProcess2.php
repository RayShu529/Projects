<?php
	$xml = new domdocument("1.0");
	$xml->load("BSIT3EG2G2.xml");
	$hsneakers = $xml->getElementsByTagName("hsneaker");

	$flag = 0;
	$search = $_REQUEST["q"];
	foreach($hsneakers as $hsneaker) 
	{
		$name = $hsneaker->getElementsByTagName("sname")->item(0)->nodeValue;
		$brand = $hsneaker->getElementsByTagName("brand")->item(0)->nodeValue;
		$collab = $hsneaker->getElementsByTagName("collab")->item(0)->nodeValue;
		if((strcasecmp($search, $name) ==0 )||(strcasecmp($search, $brand) ==0 )||(strcasecmp($search, $collab) ==0))
		{
			$date = $hsneaker->getElementsByTagName("rdate")->item(0)->nodeValue;
			$srp = $hsneaker->getElementsByTagName("srp")->item(0)->nodeValue;
			$cprice = $hsneaker->getElementsByTagName("cprice")->item(0)->nodeValue;
			$pic = $hsneaker->getElementsByTagName("pic")->item(0)->nodeValue;
		
			echo "<div id='result' class='row justify-content-center'>
			<div id='box' class='col-sm-12 col-md-9'>
			<img id='shoes' class = 'img-fluid float-end' src='data:image;base64,".$pic."'><br><br>
				<h4> $name </h4>
				<p>Brand: $brand</p>
				<p>SRP: $$srp</p>
				<p>Current Price: $$cprice</p>
				<p>Release Date: $date</p>
				<p>Collaboration: $collab</p>
				</div>
			</div>
			";
		}
	}
?>