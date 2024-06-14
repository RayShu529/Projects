 <?php
	session_start();
	if(!isset($_SESSION["id"]))
	{
	header('Location: ../Login Page/login.php');
	exit();
	}
	require "../connection.php";
?>
<!doctype html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="hpstyle.css">
<script type="text/javascript" src="../Contact/action.js"></script>
</head>
<body>

<div class="div1">
	<div class = "topdiv">
		<img src="../GT pictures/bg.png" alt="Image not available" id="imageTop">
		<p id="pp">Green Trees Hotel & Resort</p>
		
		<button id="home" type="button"><a href="Home Page.php" id="linktxt">Home</a></button>   
		<button id="rp" type="button"> <a href="../Rooms/room.php" id="linktxt">Rooms</a></button> 
		<button id="contact" type="button" onclick="window.location.href='#ft'" >Contact</button>
		<button id="about" type="button" onclick="window.location.href='#div4'"> About Us</button> 
		<button id="account" type="button"> <a href="../account/acc.php" id="linktxt">Account</a></button> 
		<img src="../GT pictures/menu.png" alt="No Image" class="menu">
	</div>
	<div class = "div7"> <h1>WELCOME TO GREEN TREES RESORT & HOTEL</h1> </div>
<div class ="div2">
<video width="20%" controls preload="true" autoplay loop muted> <source src="../GT pictures/video1.mp4"></video>
<video width="20%" controls preload="true" autoplay loop muted > <source src="../GT pictures/video2.mp4"></video>
</div>
	<div class = "div3">

			
			<img src="../GT pictures/img1.jpg" alt="No Image">
			<img src="../GT pictures/img2.jpg" alt="No Image">
			<img src="../GT pictures/img3.jpg" alt="No Image">
			<img src="../GT pictures/img4.jpg" alt="No Image">
			<img src="../GT pictures/img5.jpg" alt="No Image">
			<img src="../GT pictures/img6.jpg" alt="No Image">
			<img src="../GT pictures/img7.jpg" alt="No Image">
			<img src="../GT pictures/img8.jpg" alt="No Image">
			<img src="../GT pictures/img9.jpg" alt="No Image">
			<img src="../GT pictures/img10.jpg" alt="No Image">
			<img src="../GT pictures/img11.jpg" alt="No Image">
			<img src="../GT pictures/img12.jpg" alt="No Image">
			<img src="../GT pictures/img13.jpg" alt="No Image">
			<img src="../GT pictures/img14.jpg" alt="No Image">
			<img src="../GT pictures/img15.jpg" alt="No Image">
			<img src="../GT pictures/img16.jpg" alt="No Image">
			<img src="../GT pictures/img17.jpg" alt="No Image">
			<img src="../GT pictures/bghome1.jpg" alt="No Image">
			<img src="../GT pictures/img18.jpg" alt="No Image">
			<img src="../GT pictures/img19.jpg" alt="No Image">
</div>
<div id="div4">

	<p class="bold"> HISTORY </p>
		<p class="text">Green Trees Resort & Hotel is a Resort located in Bustos, Bulacan with the purpose of giving the people a place to relax and enjoy with 
		the provision of clean rooms and fun swimming pools that will make their stay worth remembering.</p>
		
	<p class="bold"> MISSION </p>
		<p class="text">At Green Trees Resort & Hotel, we believe in recognizing familiar faces, welcoming new ones, and treating every 
		guest we meet as we would be treated ourselves.</p>
		
		<p class="bold">VISION </p>
		<p class="text">At Green Trees Resort & Hotel, we believe that our purpose is to create a lasting impressions to our customers.</p>
</div>
<div id="ft" class = "footer">
	<p class="call"> VISIT OR CONTACT US HERE</p>
	<p>#13 Tanawan, Bustos, Bulacan, Philippines </p>
	<P> Telephone #: (044) 797 7334</P>
	<p>Phone: 0949-501-7707</p>
	<p>  FB Page: https://www.facebook.com/greentreesresorthotel </p> 
</div>
</div>

</body>
</html>
<?php
mysqli_close($con);
?>