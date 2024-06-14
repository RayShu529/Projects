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
<link rel="stylesheet" type="text/css" href="Rstyle.css">
<script type="text/javascript" src="../Contact/action.js"></script>
</head>
<body>

<div class="div1">
	<div class = "topdiv">
		<img src="../GT pictures/bg.png" alt="Image not available" id="imageTop">
		<p id="pp">Green Trees Hotel & Resort</p>
		<button id="home" type="button"><a href="../Home Page/Home Page.php" id="linktxt">Home</a></button>   
		<button id="rp" type="button"> <a href="room.php" id="linktxt">Rooms</a></button> 
		<button id="contact" type="button"> <a href="../Home Page/Home Page.php#div4" id="linktxt">Contact</a></button> 
		<button id="about" type="button"> <a href="../Home Page/Home Page.php#div4" id="linktxt">About Us</a></button> 
		<button id="account" type="button"> <a href="../account/acc.php" id="linktxt">Account</a></button> 

	</div>
	<p class = "title">Room Rates</p>
	<div class="prices">
	<img src="../GT pictures/img5.jpg" alt="No Image">
		<div class="room">
			<?php
			$stmt = "SELECT * FROM `rooms` WHERE type = 'vip'";
			$stmt_query = mysqli_query($con,$stmt);
			echo"<p class='type'>VIP ROOMS</p><span>Room";
			while($reserve = mysqli_fetch_row($stmt_query))
				{
					echo " $reserve[0]";
				}
			echo "</span><br>";
			?>
			<label>
			P350 for 3 hours
			</label><br>
			<label>
			P600 for 6 hours
			</label><br>
			<label>
			P1200 for 12 hours
			</label><br>
			<label>
			P2000 for 24 hours
			</label><br>
		
		</div>
	</div>
	<hr>
	<hr>
	<div class="prices">
	<img src="../GT pictures/img3.jpg" alt="No Image">
		<div class="room">
			<?php
			$stmt = "SELECT * FROM `rooms` WHERE type = 'acuw/cr'";
			$stmt_query = mysqli_query($con,$stmt);
			echo"<p class='type'>ACU w/ C.R.</p><span>Room";
			while($reserve = mysqli_fetch_row($stmt_query))
				{
					echo " $reserve[0]";
				}
			echo "</span><br>";
			?>
			<label>
			P250 for 3 hours
			</label><br>
			<label>
			P450 for 6 hours
			</label><br>
			<label>
			P800 for 12 hours
			</label><br>
			<label>
			P1600 for 24 hours
			</label><br>
			
		</div>
	</div>
	<hr>
	<hr>
	<div class="prices">
	<img src="../GT pictures/img2.jpg" alt="No Image">
		<div class="room">
			<?php
			$stmt = "SELECT * FROM `rooms` WHERE type = 'acuw/ocr'";
			$stmt_query = mysqli_query($con,$stmt);
			echo"<p class='type'>ACU w/o C.R.</p><span>Room";
			while($reserve = mysqli_fetch_row($stmt_query))
				{
					echo " $reserve[0]";
				}
			echo "</span><br>";
			?>
			<label>
			P200 for 3 hours
			</label><br>
			<label>
			P350 for 6 hours
			</label><br>
			<label>
			P500 for 12 hours
			</label><br>
			<label>
			P1000 for 24 hours
			</label><br>
			
		</div>
	</div>
	<hr>
	<hr>
	<div class="prices">
	<img src="../GT pictures/img4.jpg" alt="No Image">
		<div class="room">
			<?php
			$stmt = "SELECT * FROM `rooms` WHERE type = 'ordinaryw/cr'";
			$stmt_query = mysqli_query($con,$stmt);
			echo"<p class='type'>Ordinary w/ C.R.</p><span>Room";
			while($reserve = mysqli_fetch_row($stmt_query))
				{
					echo " $reserve[0]";
				}
			echo "</span><br>";
			?>
			<label>
			P200 for 3 hours
			</label><br>
			<label>
			P350 for 6 hours
			</label><br>
			<label>
			P500 for 12 hours
			</label><br>
			<label>
			P1000 for 24 hours
			</label><br>
		
		</div>
	</div>
	<hr>
	<hr>
	<div class="prices">
	<img src="../GT pictures/img4.jpg" alt="No Image">
		<div class="room">
			<?php
			$stmt = "SELECT * FROM `rooms` WHERE type = 'ordinaryw/ocr'";
			$stmt_query = mysqli_query($con,$stmt);
			echo"<p class='type'>Ordinary w/o C.R.</p><span>Room";
			while($reserve = mysqli_fetch_row($stmt_query))
				{
					echo " $reserve[0]";
				}
			echo "</span><br>";
			?>
			<label>
			P180 for 3 hours
			</label><br>
			<label>
			P300 for 6 hours
			</label><br>
			<label>
			P450 for 12 hours
			</label><br>
			<label>
			P800 for 24 hours
			</label><br>
			
		</div>
	</div>
	<button  type="button" id="cmd"> <a href="../reserve/reservation.php" id="linktxt">RESERVE NOW</a></button>
</div>
</body>
</html>
<?php
mysqli_close($con);
?>