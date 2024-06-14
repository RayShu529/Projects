<!doctype html>
<html>
	<head>
		<title>Add Page</title>
		<link rel="stylesheet" href="css/add.css">
		<script src="jquery/jquery-3.3.1.min.js"></script>
		<script src="jquery/jquery-ui.css"></script>
		<script src="jquery/jquery-ui.min.js"></script>
		<script src="js/add.js"></script>
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		<link rel="stylesheet" href="css/bootstrap.min.css" />
		<script src="js/bootstrap.min.js"></script>
	</head>
	<body class = "container-fluid">
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
	<div id="div1" >
		<form id="form1"  method="post" action="addProcess.php">
			<div class = "form-group">
			Name: <input id="namebar" class="form-control" type="text" name="sname" required /><br><label id="txtHint"></label>
			</div>
			<div class = "form-group">
			Brand: <input id="brandbar" class="form-control" type="text" name="brand" required />
			</div>
			Suggested Retail Price:
			<div class = "input-group mb-3">
			
				<span class="input-group-text">&#36;</span>
				<input  type="text" id = "srp" class="form-control" name="srp" required /> 
				
			</div>
			Current Price:
			<div class = "input-group mb-3">
			<span class="input-group-text">&#36;</span>
			 <input  type="text" id="cprice" class="form-control" name="cprice" required /> 
			</div>
			<div class = "form-group">
			<label>Release Date: <input id="date"  class="form-control" type="text" name="rdate"required readonly /></label> 
			</div>
			<div class = "form-group">
			Collaborator: <input  type="text"  class="form-control" name="collab" required /> 
			</div>
			<div class = "form-group">
			Picture: <input  type="file" class="form-control" name="pic" required />
			</div>
			
				<div class='d-flex justify-content-center'>
					<input  id="add"  type="submit" value="Add">
					<button  id="back" type="button"><a class="txt" href="topsneaker.php">Back</a></button>
				</div>
			
		</form></div></div>
	</body>
</html>





