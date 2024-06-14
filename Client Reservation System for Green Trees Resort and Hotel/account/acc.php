<?php
	session_start();
	if(!isset($_SESSION["id"]))
	{
	header('Location: ../Login Page/login.php');
	exit();
	}
$id=$_SESSION['id'];
require "../connection.php";
$stmt = "SELECT * FROM `customer` WHERE ID='$id'";
$stmt_query = mysqli_query($con,$stmt);
$display = mysqli_fetch_row($stmt_query);
?>
<!doctype html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="accdesign.css">
<script type="text/javascript" src="account.js"></script>

</head>
<body>

<div class="div1">
	<div class = "topdiv">
		<img src="../GT pictures/bg.png" alt="Image not available" id="imageTop">
		<p id="pp">Green Trees Hotel & Resort</p>
		
		<button id="home" type="button"><a href="../Home Page/Home Page.php" id="linktxt">Home</a></button>   
		<button id="rp" type="button"> <a href="../Rooms/room.php" id="linktxt">Rooms</a></button> 
		<button id="contact" type="button"> <a href="../Home Page/Home Page.php#div4" id="linktxt">Contact</a></button> 
		<button id="about" type="button"> <a href="../Home Page/Home Page.php#div4" id="linktxt">About Us</a></button> 
		<button id="account" type="button"> <a href="acc.php" id="linktxt">Account</a></button> 
		<img src="../GT pictures/menu.png" alt="No Image" class="menu">
	</div >
		<div id="container">
				<?php echo"<button style='height:fit-content;' id='cUsername' type='button'><a href='Cuser/cuser.php' class='linktxt'>Username: $display[4]<br>Change Username</a></button>   <br>";?>
				<?php echo"<button style='height:fit-content;' id='cContact' type='button'><a href='Ccontact/ccontact.php' class='linktxt'>Contact: $display[3]<br>Change Contact</a></button>   <br>";?>
				
				<button id="change" type="button"><a href="Cpass/cpass.php" class="linktxt">Change Password</a></button>   <br>
				<button id="reservations" type="button"><a href="reservations.php" class="linktxt">Reservations</a></button> <br>
				<button id="logout" type="button" onclick="c()">Logout</button>  


		</div>
	 
</div>

</body>
</html>
<?php
mysqli_close($con);
?>