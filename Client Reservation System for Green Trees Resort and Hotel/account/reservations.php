<?php
session_start();
if(!isset($_SESSION["id"]))
	{
	header('Location: ../Login Page/login.php');
	exit();
	}
$id=$_SESSION['id'];
require "../connection.php";
$stmt = "SELECT * FROM `reservation` WHERE userID='$id'";
$stmt_query = mysqli_query($con,$stmt);
if(mysqli_num_rows($stmt_query)==0)
{
	echo "<script>window.location='acc.php';alert('No reservations at the moment');</script>";
}
?>
<!doctype html>
<html>
	<head>
  <style>
				body{
					margin: 0px;
				}
				#container{
					width: 100%;
					height: 1080px;
					
				}
				#topdiv{
					background-color:rgb(9, 102, 29);	 
					width: 100%;
					height: 50px;
					box-shadow: rgba(0, 0, 0, 0.4) 0px 2px 4px, 
					rgba(0, 0.5, 0, 0.6) 0px 2px 10px -3px,
					rgba(0, 0.5, 0, 0.7) 0px -1px 0px inset;
				}
				#imageTop{

					width: 50px;
					height: 50px;
					float: left;
					margin-top: 0px;
					margin-left: 30px;
					border-radius: 30px;
					box-shadow: rgba(0, 0, 0, 0.4) 0px 2px 4px, 
					rgba(0, 0.5, 0, 0.6) 0px 7px 13px -3px,
					rgba(0, 0.5, 0, 0.7) 0px -3px 0px inset;

				}

				#pp{
					padding: 10px;
					font-family: 'Times New Roman', Times, serif;
					font-size: 26px;
					float: left;
					color: white;
					text-shadow: 0px 0px 15px rgb(3, 3, 3);
					margin-top: 0px;


					}
				#div2{
					width: 20%;
					height:1000px;
					background: rgba(9, 102, 29, 0.822);
					box-shadow: rgba(0, 0, 0, 0.4) 0px 2px 4px, 
					rgba(0, 0.5, 0, 0.6) 0px 7px 13px -3px,
					rgba(0, 0.5, 0, 0.7) 0px -3px 0px inset;
				}
				table {
					border-collapse: collapse;
				}
				table {
					border-spacing: 10px 10px;
				}

				table {
					width: 800px;
				}
				table{
					position: absolute;
					top: 200px;
					right: 113px;
					color: white;
					font-family: 'Times New Roman', Times, serif;

				}

				th {

					height: 30px;
				}

				th, td {
					padding: 10px;
				}

				th {
					text-align: center;
				}

				td {
					text-align: center;
				}

				td {
					height: 40px;
					vertical-align: bottom;
					}

					th, td {
						border-bottom: 1px solid black;
					}

					tr:hover {
						background-color: rgb(39, 136, 47);
						}

						th {
							background-color: rgb(20, 116, 33);
							color: white;
						}

						tr {
							background-color: rgba(20, 116, 33, 0.616);
						}
				table td:last-child ,th:last-child
				{
					  border-right: 1px ; 
				}
				tr:first-child
				{
					 border-top: 1px solid #000;
					 border-bottom: 1px solid #000;
				}
				tr:last-child
				{
					border-bottom: 1px solid #000;
				}
				#linktxt{
					text-decoration: none;
					color: white;
				}
				#back{
					text-align:center; background-color: 
					red;margin:10px 10px 0px 0px;
					font-weight:BOLD;
					padding: 10px 40px;
					font-size: 18px;
					background-color: #eb0b0b;
					color: #fff;
					text-shadow: 0 2px 0 rgb(0 0 0 / 25%);
					display: inline-flex;
					align-items: center;
					justify-content: center;
					position: absolute;
					top: 100px;
					left:60px;
					border: 0;
					z-index: 1;
					user-select: none;
					cursor: pointer;
					text-transform: uppercase;
					letter-spacing: 1px;
					white-space: unset;
					padding: .8rem 1.5rem;
					text-decoration: none;
					font-weight: 900;
					transition: all 0.7s cubic-bezier(0,.8,.26,.99);
				}

				#back:before {
					position: absolute;
					pointer-events: none;
					top: 0;
					left: 0;
					display: block;
					width: 100%;
					height: 100%;
					content: '';
					transition: .7s cubic-bezier(0,.8,.26,.99);
					z-index: -1;
					background-color: #cc5a1!important;
					box-shadow: 0 -4px rgba(136, 2, 2, 0.5) 
					inset, 0 4px rgba(241, 48, 13, 0.99) 
					inset, -4px 0 rgba(253, 31, 31, 0.5) 
					inset, 4px 0 rgba(163, 25, 1, 0.534) inset;
				}
   
				#back:after {
					position: absolute;
					
					pointer-events: none;
					top: 0;
					left: 0;
					display: block;
					width: 100%;
					height: 100%;
					content: '';
					box-shadow: 0 4px 0 0 rgb(0 0 0 / 15%);
					transition: .7s cubic-bezier(0,.8,.26,.99);
				}
				
				#back:hover:before {
					box-shadow: 0 -4px rgb(0 0 0 / 50%) inset, 
					0 4px rgb(255 255 255 / 20%) inset, -4px 0 rgb(255 255 255 / 20%)
					inset, 4px 0 rgb(0 0 0 / 50%) inset;
				}
				
				#back:hover:after 
				{
					box-shadow: 0 4px 0 0 rgb(0 0 0 / 15%);
				}
				
				#back:active 
				{
					transform: translateY(4px);
				}
				
				#back:active:after {
					box-shadow: 0 0px 0 0 rgb(0 0 0 / 15%);
				}
				input,select{
					width:85%;
					height: 20%;
					font-size:15px;
					border-radius: 10px;
					margin:5px;
					padding-left:10px ;
				}
				form{
					font-weight:BOLD;
					
					
					text-align: center;
					font-family: 'Times New Roman', Times, serif;
					color: white;
					font-size: 20px;
					position:absolute;
					top: 200px;
					left: 25px;
					background-color: #008542;
					border-radius: 20px;
					width:180px;
					height:135px;
					padding:12px;


				}
				#delete{
					margin-top: 10px;
					position: absolute;
					left: 30%;
					text-align:center; background-color: 
					red;
					font-weight:BOLD;
					font-size: 14px;
					background-color: #eb0b0b;
					color: #fff;
					text-shadow: 0 2px 0 rgb(0 0 0 / 25%);
					display: inline-flex;
					align-items: center;
					justify-content: center;
					border: 0;
					z-index: 1;
					user-select: none;
					cursor: pointer;
					text-transform: uppercase;
					letter-spacing: 1px;
					white-space: unset;
					text-decoration: none;
					font-weight: 900;
					transition: all 0.7s cubic-bezier(0,.8,.26,.99);
				}
				#delete:before {
					position: absolute;
					pointer-events: none;
					top: 0;
					left: 0;
					display: block;
					width: 100%;
					height: 100%;
					content: '';
					transition: .7s cubic-bezier(0,.8,.26,.99);
					z-index: -1;
					background-color: #cc5a1!important;
					box-shadow: 0 -4px rgba(136, 2, 2, 0.5) 
					inset, 0 4px rgba(241, 48, 13, 0.99) 
					inset, -4px 0 rgba(253, 31, 31, 0.5) 
					inset, 4px 0 rgba(163, 25, 1, 0.534) inset;
				}
   
				#delete:after {
					position: absolute;
					
					pointer-events: none;
					top: 0;
					left: 0;
					display: block;
					width: 100%;
					height: 100%;
					content: '';
					box-shadow: 0 4px 0 0 rgb(0 0 0 / 15%);
					transition: .7s cubic-bezier(0,.8,.26,.99);
				}
				
				#delete:hover:before {
					box-shadow: 0 -4px rgb(0 0 0 / 50%) inset, 
					0 4px rgb(255 255 255 / 20%) inset, -4px 0 rgb(255 255 255 / 20%)
					inset, 4px 0 rgb(0 0 0 / 50%) inset;
				}
				
				#delete:hover:after 
				{
					box-shadow: 0 4px 0 0 rgb(0 0 0 / 15%);
				}
				
				#delete:active 
				{
					transform: translateY(4px);
				}
				
				#delete:active:after {
					box-shadow: 0 0px 0 0 rgb(0 0 0 / 15%);
				}
					form:hover {
					background-color: #28c936;
					box-shadow: 0px 15px 20px rgba(9, 255, 0, 0.534);
					color: #fff;
					transform: translateY(-7px);
				}
				
				form:active {
					transform: translateY(-1px);
				}

				#niHao{
					position: absolute;
					top:50px;
					right: 320px;
					width: fit-content;
					height: fit-content;
					font-weight:BOLD;
					font-size:50px;
					color:white;
					background-color:rgb(9, 102, 29);	
					font-family: 'Times New Roman', Times, serif;
					padding: 8px;
					border-radius: 25px;
					margin-bottom: 30px;
				}



</style>
		<script>
				function start()
						{
						if ( window.history.replaceState ) 
						{
						window.history.replaceState( null, null, window.location.href );
						}
					}
				function check()
				{
					var choice =confirm("Are you sure?");
					if ((choice==false))
						{
						return false;
						}
									
				}
		</script>
	</head>
				<body onload="start()">

		<div id="container">
			<div id="topdiv">
					<img src="../GT pictures/bg.png" alt="Image not available" id="imageTop">
					<p id="pp">Green Trees Hotel & Resort</p>
			</div>
					
			<div id="div2">


			</div><p id="niHao"> RESERVATION</p>
				<form method='post' action='<?php echo htmlspecialchars($_SERVER['PHP_SELF']);?>' onsubmit="return check()">
				Delete Reservation	<br>
				<input type="text" placeholder="Room Number"name="roomno"></br>
				<input type="date" name="date"></br>

				<button id="delete" type='submit' name='delserve'>Delete</button>
				</form>
				<table>
				<tr>
					<th>UserID</th>
					<th>Room No.</th>
					<th>Date</th>
					<th>Duration</th>

				</tr>
				<?php

				while($display = mysqli_fetch_row($stmt_query))
					{
						echo"
						<tr>
							<td>$display[0]</td>
							<td>$display[1]</td>
							<td>$display[2]</td>
							<td>$display[3]</td>
							
						</tr>";

					}
				?>

				</table>
					<button id="back" type="button"> <a href='acc.php' id="linktxt">Back</a></button> 


				<?php
				if(isset($_REQUEST['delserve']))
				{
					$room=$_POST['roomno'];
					$date= $_POST['date'];
					$stmt = "SELECT * FROM `reservation` WHERE userID='$id' AND roomno='$room' AND date='$date'";
					$stmt_query = mysqli_query($con,$stmt);
					if(mysqli_num_rows($stmt_query)==0)
					{
					echo "<script>window.location='reservations.php';alert('Reservation not found');</script>";
					}
					else
					{
					$stmt = "DELETE FROM `reservation` WHERE userID='$id' AND roomno='$room' AND date='$date'";
					$stmt_query = mysqli_query($con,$stmt);	
					echo "<script>window.location='reservations.php';alert('Reservation deleted successfully');</script>";
					}
				}
				mysqli_close($con);
				?>

		</div>
</body>
</html>
