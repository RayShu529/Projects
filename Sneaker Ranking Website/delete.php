<!doctype html>
<html>
	<head>
		<title>Delete Page</title>
		<script src="jquery/jquery-3.3.1.min.js"></script>
		<link rel="stylesheet" href='css/delete.css'>
		<script src="js/delete.js"></script>
		<meta name="viewport" content="width=device-width, initial-scale=1" />
	<link rel="stylesheet" href="css/bootstrap.min.css" />
	<script src="js/bootstrap.min.js"></script>
	</head>
	<body class='container-fluid'>
	<div id="header" class="row">
		<div class = 'col-sm-4'>
			<a href="topsneaker.php">	<img id="logo" class="img-fluid float-left" src="img2.png" alt="image nawawala"></a>
		</div>
		<div  class = 'col-sm-8'>
			<button id="btnhome" type="button"><a class="txts" href="topsneaker.php">HOME</a></button>
			<button id="btnAdd"  type="button"><a class="txts" href="add.php">ADD</a></button>   
			<button id="btnEdit"  type="button"><a class="txts"  href="edit.php">EDIT</a></button>  
			<button id="btnDelete"  type="button"><a class="txts" href="delete.php">DELETE</a></button>  
		</div>
	</div>
<div class="d-flex justify-content-center">
		<div id="div1">
			<form id="form" method="post" action="deleteProcess.php">
			<div class = "form-group">
					Name: <select id='sname'class='form-control' name="sname">
						<option selected disabled>Select Sneaker</option>
						<?php
							$xml = new domdocument("1.0");
							$xml->load("BSIT3EG2G2.xml");
							$hsneakers = $xml->getElementsByTagName("hsneaker");
							
							foreach($hsneakers as $hsneaker){
								$name = $hsneaker->getElementsByTagName("sname")->item(0)->nodeValue;
								echo "<option>" .$name. "</option>";
							}
						?>
					</select>
					</div>
					<div class='d-flex justify-content-center'>
					<input id="delete" type="submit" value="Delete">
					<button id="back" type="button"><a id="txt" href="topsneaker.php">Back</a></button>
					</div>
				</form>

		</div>
		</div>
	</body>
</html>