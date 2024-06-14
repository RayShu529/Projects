<!doctype html>
<html>
	<head>
		<title>TOP HYPE SNEAKERS</title>
		<script src="jquery/jquery-3.3.1.min.js"></script>
		<script src="js/topsneaker.js"></script>
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		<link rel="stylesheet" href="css/bootstrap.min.css" />
		<script src="js/bootstrap.min.js"></script>
		<link rel="stylesheet" href="css/topsneaker.css">
	</head>
<body id="body" class = "container-fluid">
	<div id="div1" class="row"> 
	<div class="col-sm-4">
	<a href="topsneaker.php">	
	<img id="logo" class="img-fluid float-left" src="img2.png" alt="image nawawala">
	</a>
	</div>
	<div class="col-sm-4">
		<button id="btnhome" type="button"><a class="txts" href="topsneaker.php">HOME</a></button>
		<button id="btnAdd"  type="button"><a class="txts" href="add.php">ADD</a></button>   
		<button id="btnEdit"  type="button"><a class="txts"  href="edit.php">EDIT</a></button>  
		<button id="btnDelete"  type="button"><a class="txts" href="delete.php">DELETE</a></button>  
	</div>	
	<div id="div2" class="col-sm-4"> 
		<div class="container">
			<form method="post" action="searchProcess.php">
						<input id="btnSearch"  type="submit" value="SEARCH">
						<input id="searchbar"  type="text" name="search"  required /><br/>	
					<label class = "float-center"id="txtHint" ></label><br/>	
		</form>
		</div>
	</div>
	 </div>
<div class="d-flex justify-content-center">
	<h1 id="title">TOP HYPE SNEAKERS</h1>
</div>	
<img id="img2" class = "img-fluid float-center" src="bgfinal.png" alt="crdaad">


<div id='result' class='row justify-content-center'>
<?php
	$xml = new domdocument("1.0");
	$xml->load("BSIT3EG2G2.xml");
	$hsneakers = $xml->getElementsByTagName("hsneaker");
	
	foreach($hsneakers as $hsneaker) 
	{
		$name = $hsneaker->getElementsByTagName("sname")->item(0)->nodeValue;
		$brand = $hsneaker->getElementsByTagName("brand")->item(0)->nodeValue;
		$collab = $hsneaker->getElementsByTagName("collab")->item(0)->nodeValue;
		$date = $hsneaker->getElementsByTagName("rdate")->item(0)->nodeValue;
		$srp = $hsneaker->getElementsByTagName("srp")->item(0)->nodeValue;
		$cprice = $hsneaker->getElementsByTagName("cprice")->item(0)->nodeValue;
			$pic = $hsneaker->getElementsByTagName("pic")->item(0)->nodeValue;
					echo "
					<div id='box' class='col-sm-12 col-md-9'>
						<img id = 'shoes' class = 'img-fluid float-end' src='data:image;base64,".$pic."'><br><br>
						<h4> $name </h4>
						<p>Brand: $brand</p>
						<p>SRP: $$srp</p>
						<p>Current Price: $$cprice</p>
						<p>Release Date: $date</p>
						<p>Collaborator: $collab</p>
					</div>
					";
	}
?>	
</div>
	</body>
</html>

