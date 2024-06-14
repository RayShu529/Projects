  <?php
	session_start();
	if(!isset($_SESSION["id"]))
	{
	header('Location: ../Login Page/login.php');
	exit();
	}
	$id=$_SESSION['id'];
	require "../connection.php";
?>
 <!doctype html>
<html>
    <head>
     <style>
	 body{ 
		margin: 0px;
		background-image: url("../GT pictures/bghome.jpg");
	}

	.topdiv{
		 width:100%;
		  height:50px; 
		  background-color:rgb(9, 102, 29);
		   text-align:right;
		    box-shadow: rgba(0, 0, 0, 0.4) 0px 2px 4px, 
		rgba(0, 0.5, 0, 0.6) 0px 7px 13px -3px,
		rgba(0, 0.5, 0, 0.7) 0px -3px 0px inset;
	}

	#imageTop{ 
		width: 50px;
		height: 50px;
		float: left;
		margin-top: 0px;
		margin-left: 30px;
		border-radius: 30px;
		box-shadow: rgba(0, 0, 0, 0.4) 0px 2px 4px, 
    rgba(0, 0.5, 0, 0.6) 0px 7px 13px -3px, rgba(0, 0.5, 0, 0.7) 0px -3px 0px inset; border: solid white;}

	#pp{
		padding: 10px;
		font-family: 'Times New Roman', Times, serif;
		font-size: 26px; 
		float: left; 
		color: white; text-shadow: 
		0px 0px 15px rgb(3, 3, 3); 
		margin-top: 0px;
	}
	#imageTop:hover { 
		border-color: #058610c4; 
		box-shadow: 0 4px 18px 0 rgba(0, 0, 0, 0.25);
	
	}
	
	#imageTop:hover{ 
		transform: translate(-50%, 50%); 
		opacity: 1;
	}
	#imageTop:hover{
		transform: scale(1.8); 
		color: #07ff07; 
		box-shadow: 2px 2px 15px #07ff07 inset;
	}
	#home, #contact, #about, #rp,#account { 
		font-size:25px; 
		color: White;  
		border: 0px solid black; 
		background-color: transparent;  
		padding: 8px 10px; 
	}
	#linktxt{  
		text-decoration: none;  
		color: White; 
	}
	#home:hover,#account:hover, #rp:hover, #about:hover{  
		background-color: #3aeb23;  
		color: #000;
	}


	#left{ width: fit-content; 
		text-align: center; 
		float: left; 
		background-color: rgba(9, 102, 29, 0.637);
		border-radius: 20px; 
		padding: 15px; 
		font-size: 30px;
	}
	#right{ 
		width: fit-content; 
		text-align: center; 
		float: left; 
		margin-left: 50px; 
		margin-top: 150px; 
		background-color: rgba(9, 102, 29, 0.637);
		border-radius: 20px; 
		padding: 10px;
	}

	#p,select,#btn{ 
		font-family:Georgia; 
		color: rgb(255, 255, 255);  
		text-align: center; 
		font-size: 30px;  
		background-color: rgba(9, 102, 29, 0.637);
   		 padding: 10px; 
		border-radius: 20px;  
		box-shadow: rgba(0, 0, 0, 0.4) 0px 2px 4px,  
		rgba(0, 0.5, 0, 0.6) 0px 7px 13px -3px, rgba(0, 0.5, 0, 0.7) 0px -3px 0px inset;
	
	}

	label{font-family:'Times New Roman', Times, serif; 
		font-size: 27px;
	}
	#date{
		border-radius: 20px;
		font-size: 20px; 
		padding: 10px;
		margin-top:10px; 
		width: 130px; 
		height: 30px; 
		background: transparent;
		border: 0px;
	}
	#pr{
		border-radius: 20px;
		font-size: 30px; 
		padding: 10px;
		margin-top:10px; 
		width: 130px; 
		height: 30px; 
		background: transparent;
		border: 0px;
	}
	#btn{
		border-radius: 20px; 
		margin-top:10px;
		padding: 10px;
		margin-top: 10px;
	}
	.basta{display:none;}

	#rooms,#p, #btn{
		margin-top: 10px;
		color: #000;
		text-shadow: 0px 0px 15px rgb(31, 255, 42);
	}
	#lalagyan{
		
		width: 50%;
		height: 700px;
		position: absolute;
		top: 180px;
		right: 180px;
		text-shadow: 0px 0px 15px rgb(31, 255, 42);
	}
	 </style>
	<script type="text/javascript">
		function sel()
		{
			var type=document.getElementById("rooms").value;
			var vip=document.getElementById("vipdiv");
			var acu1=document.getElementById("acudiv1");
			var acu2=document.getElementById("acudiv2");
			var ord1=document.getElementById("ordiv1");
			var ord2=document.getElementById("ordiv2");
			var right=document.getElementById("right");
			var radio = document.querySelector('input[name="roomno"]:checked');
			var dur = document.querySelector('input[name="dur"]:checked');
			if(type=="vip")
			{
				vip.style.display="block"; right.style.display="block";
				acu1.style.display="none"; acu2.style.display="none";
				ord1.style.display="none"; ord2.style.display="none";
				
					if(dur.value=="3 hrs") 
					{
					document.getElementById('pr').value="350";
					}  
					else if(dur.value=="6 hrs") 
					{
					document.getElementById('pr').value="600";
					}
					else if(dur.value=="12 hrs") 
					{
					document.getElementById('pr').value="1200";
					}
					else if(dur.value=="24 hrs")  
					{
					document.getElementById('pr').value="2000";
					}
					
				if(radio.checked==true){radio.checked=false;}
				
					
				 
			}
			else if(type=="acuw/cr")
			{
				vip.style.display="none"; right.style.display="block";
				acu1.style.display="block"; acu2.style.display="none";
				ord1.style.display="none"; ord2.style.display="none";
				if(dur.value=="3 hrs") 
				{
					document.getElementById('pr').value="250";
				}  
				else if(dur.value=="6 hrs") 
				{
					document.getElementById('pr').value="450";
				}
				else if(dur.value=="12 hrs") 
				{
					document.getElementById('pr').value="800";
				}
				else if(dur.value=="24 hrs")  
				{
				document.getElementById('pr').value="1600";
				}  
				if(radio.checked==true){radio.checked=false;}
			}
			else if(type=="acuw/ocr")
			{
				vip.style.display="none"; right.style.display="block";
				acu1.style.display="none"; acu2.style.display="block";
				ord1.style.display="none"; ord2.style.display="none";
				if(dur.value=="3 hrs") 
				{
					document.getElementById('pr').value="200";
				}  
				else if(dur.value=="6 hrs") 
				{
					document.getElementById('pr').value="350";
				}
				else if(dur.value=="12 hrs") 
				{
					document.getElementById('pr').value="500";
				}
				else if(dur.value=="24 hrs")  
				{
				document.getElementById('pr').value="1000";
				} 
				if(radio.checked==true){radio.checked=false;}
			}
			else if(type=="ordinaryw/cr")
			{
				vip.style.display="none"; right.style.display="block";
				acu1.style.display="none"; acu2.style.display="none";
				ord1.style.display="block"; ord2.style.display="none";
				if(dur.value=="3 hrs") 
				{
					document.getElementById('pr').value="200";
				}  
				else if(dur.value=="6 hrs") 
				{
					document.getElementById('pr').value="350";
				}
				else if(dur.value=="12 hrs") 
				{
					document.getElementById('pr').value="500";
				}
				else if(dur.value=="24 hrs")  
				{
				document.getElementById('pr').value="1000";
				}
				if(radio.checked==true){radio.checked=false;}
			}
			else if(type=="ordinaryw/ocr")
			{
				vip.style.display="none"; right.style.display="block";
				acu1.style.display="none"; acu2.style.display="none";
				ord1.style.display="none"; ord2.style.display="block";
				if(dur.value=="3 hrs") 
				{
					document.getElementById('pr').value="180";
				}  
				else if(dur.value=="6 hrs") 
				{
					document.getElementById('pr').value="300";
				}
				else if(dur.value=="12 hrs") 
				{
					document.getElementById('pr').value="450";
				}
				else if(dur.value=="24 hrs")  
				{
				document.getElementById('pr').value="800";
				} 
				if(radio.checked==true){radio.checked=false;}
			}
		}
		function d()
		{
			var currentDate  = new Date();
			currentMonth = currentDate.getMonth()+1;
			currentDay = currentDate.getDate();
			currentYear = currentDate.getFullYear();
		if(currentDate.getDate()<10)
		{
			currentDay   = "0" + currentDate.getDate();
		}
		if(currentDate.getMonth()<10)
		{
			currentMonth = "0" + currentDate.getMonth();
		}

		today=currentYear+"-"+currentMonth+"-"+currentDay;
		document.getElementById('date').setAttribute("min",today);
		
}
		function cprice()
		{
			var type=document.getElementById("rooms").value;
			var dur = document.querySelector('input[name="dur"]:checked');
			if(type=="vip")
			{
				if(dur.value=="3 hrs") 
				{
					document.getElementById('pr').value="350";
				}  
				else if(dur.value=="6 hrs") 
				{
					document.getElementById('pr').value="600";
				}
				else if(dur.value=="12 hrs") 
				{
					document.getElementById('pr').value="1200";
				}
				else if(dur.value=="24 hrs")  
				{
				document.getElementById('pr').value="2000";
				}  
			}
			else if(type=="acuw/cr")
			{
				if(dur.value=="3 hrs") 
				{
					document.getElementById('pr').value="250";
				}  
				else if(dur.value=="6 hrs") 
				{
					document.getElementById('pr').value="450";
				}
				else if(dur.value=="12 hrs") 
				{
					document.getElementById('pr').value="800";
				}
				else if(dur.value=="24 hrs")  
				{
				document.getElementById('pr').value="1600";
				}  
			}
			else if(type=="acuw/ocr")
			{
				if(dur.value=="3 hrs") 
				{
					document.getElementById('pr').value="200";
				}  
				else if(dur.value=="6 hrs") 
				{
					document.getElementById('pr').value="350";
				}
				else if(dur.value=="12 hrs") 
				{
					document.getElementById('pr').value="500";
				}
				else if(dur.value=="24 hrs")  
				{
				document.getElementById('pr').value="1000";
				} 
			}
			else if(type=="ordinaryw/cr")
			{
				if(dur.value=="3 hrs") 
				{
					document.getElementById('pr').value="200";
				}  
				else if(dur.value=="6 hrs") 
				{
					document.getElementById('pr').value="350";
				}
				else if(dur.value=="12 hrs") 
				{
					document.getElementById('pr').value="500";
				}
				else if(dur.value=="24 hrs")  
				{
				document.getElementById('pr').value="1000";
				} 
			}
			else if(type=="ordinaryw/ocr")
			{
				if(dur.value=="3 hrs") 
				{
					document.getElementById('pr').value="180";
				}  
				else if(dur.value=="6 hrs") 
				{
					document.getElementById('pr').value="300";
				}
				else if(dur.value=="12 hrs") 
				{
					document.getElementById('pr').value="450";
				}
				else if(dur.value=="24 hrs")  
				{
				document.getElementById('pr').value="800";
				} 
			}
		}
		function check()
		{
			var type = document.getElementById("rooms");
			var dur = document.querySelector('input[name="dur"]:checked');
			var date = document.getElementById("date");
			var roomno = document.querySelector('input[name="roomno"]:checked');
			if((type.value!="")&&(dur!=null)&&(date.value!="")&&(roomno!=null))
			{
				var str ="Room Type: "+type.value+"\nDuration: "+dur.value+"\nDate: "+date.value+"\nPrice: "+document.getElementById('pr').value;
				var choice=confirm(str);
					if ((choice==true))
					{
						alert("Processing Order. Please wait");
						return;
					}
					else
					{
						alert("order cancelled");
						return false;
					}
			}
			else
			{
				alert("All field are required");
				return false;
			}
		}
		function start()
		{
		 if ( window.history.replaceState ) 
		{
		window.history.replaceState( null, null, window.location.href );
		}
	}
	</script>
    </head>
     <body onload="start()">
            <div class = "topdiv">
                <img src="../GT pictures/bg.png" alt="Image not available" id="imageTop">
                <p id="pp">Green Trees Hotel & Resort</p>
                <button id="home" type="button"><a href="../Home Page/Home Page.php" id="linktxt">Home</a></button>   
                <button id="rp" type="button"> <a href="../Rooms/room.php" id="linktxt">Rooms</a></button> 
                <button id="contact" type="button"> <a href="../Home Page/Home Page.php#div4" id="linktxt">Contact</a></button> 
                <button id="about" type="button"> <a href="../Home Page/Home Page.php#div4" id="linktxt">About Us</a></button> 
                <button id="account" type="button"> <a href="../account/acc.php" id="linktxt">Account</a></button> 
            </div>
<div id="container">
	<form method="post" action="<?php echo htmlspecialchars($_SERVER["PHP_SELF"]);?>" onsubmit="return check()">
			<div id="lalagyan">



			<div id="left">
			<select name="rtype" id="rooms" onchange="sel()">
					<option value="" selected disabled>Select Type</option>
					<option value="vip">VIP</option>";
					<option value="acuw/cr">ACU w/ C.R.</option>";
					<option value="acuw/ocr">ACU w/o C.R.</option>";
					<option value="ordinaryw/cr">Ordinary w/ C.R.</option>";
					<option value="ordinaryw/ocr">Ordinary w/o C.R.</option>";
				</select>
                <p id="p"> DURATION</p>
					<label>
                    <input type="radio" name="dur" onclick="cprice()" value="3 hrs">3 hrs</input>
                    </label><br>
                    <label>
                    <input type="radio" name="dur" onclick="cprice()" value="6 hrs">6 hrs</input>
                    </label><br>
                    <label>
                    <input type="radio" name="dur" onclick="cprice()" value="12 hrs">12 hrs</input>
                    </label><br>
                    <label>
                    <input type="radio" name="dur" onclick="cprice()" value="24 hrs">24 hrs</input>
                    </label>
					<br>
					<label>DATE:<input type="date" id="date" name="date" onclick="d()"></input></label>
					<br>
					<label>Price:
                    <input id="pr" type="text" readonly></input>
                    </label><br>
					<button type="submit" id="btn" name="confirm">CONFIRM</button>
		</div>
		
		<div id="right">
			<div id="vipdiv" class="basta">
			<p id="p">Select Room</p>
			<?php
			$stmt = "SELECT * FROM `rooms` WHERE type = 'vip'";
			$stmt_query = mysqli_query($con,$stmt);
			while($reserve = mysqli_fetch_row($stmt_query))
				{
					echo "<label>
                    <input type='radio' name='roomno' value='$reserve[0]'>Room $reserve[0]</input>
                    </label><br>";
				}
			?>
			</div>
			<div id="acudiv1" class="basta">
			<p id="p">Select Room</p>
			<?php
			$stmt = "SELECT * FROM `rooms` WHERE type = 'acuw/cr'";
			$stmt_query = mysqli_query($con,$stmt);
			while($reserve = mysqli_fetch_row($stmt_query))
				{
					echo "<label>
                    <input type='radio' name='roomno' value='$reserve[0]'>Room $reserve[0]</input>
                    </label><br>";
				}
			?>
			</div>
			<div id="acudiv2" class="basta">
			<p id="p">Select Room</p>
			<?php
			$stmt = "SELECT * FROM `rooms` WHERE type = 'acuw/ocr'";
			$stmt_query = mysqli_query($con,$stmt);
			while($reserve = mysqli_fetch_row($stmt_query))
				{
					echo "<label>
                    <input type='radio' name='roomno' value='$reserve[0]'>Room $reserve[0]</input>
                    </label><br>";
				}
			?>
			</div>
			<div id="ordiv1" class="basta">
			<p id="p">Select Room</p>
			<?php
			$stmt = "SELECT * FROM `rooms` WHERE type = 'ordinaryw/cr'";
			$stmt_query = mysqli_query($con,$stmt);
			while($reserve = mysqli_fetch_row($stmt_query))
				{
					echo "<label>
                    <input type='radio' name='roomno' value='$reserve[0]'>Room $reserve[0]</input>
                    </label><br>";
				}
			?>
			</div>
			<div id="ordiv2" class="basta">
			<p id="p">Select Room</p>
			<?php
			$stmt = "SELECT * FROM `rooms` WHERE type = 'ordinaryw/ocr'";
			$stmt_query = mysqli_query($con,$stmt);
			while($reserve = mysqli_fetch_row($stmt_query))
				{
					echo "<label>
                    <input type='radio' name='roomno' value='$reserve[0]'>Room $reserve[0]</input>
                    </label><br>";
				}
			?>
			</div>
		</div>

</div>
	</form>		
</div>
     </body>
</html>
<?php
	if(isset($_REQUEST['confirm']))
	{
		
		$room=$_REQUEST['roomno'];
		$dur=$_REQUEST['dur'];
		$date=$_REQUEST['date'];
		$stmt = "SELECT * FROM `reservation` WHERE roomno='$room' AND date='$date'";
		$stmt_query = mysqli_query($con,$stmt);
	if(mysqli_num_rows($stmt_query)==0)
		{
			$stmt = "INSERT INTO `reservation`(`userID`, `roomno`, `date`, `duration`) VALUES ('$id','$room','$date','$dur')";
			$stmt_query = mysqli_query($con,$stmt);
			echo "<script>window.location.href='reservation.php';alert('Room reserved successfully');</script>";	
		}
	else
		{
			echo "<script>alert('Room already taken. Choose other Room or Date');</script>";	
		}
	}
mysqli_close($con);
?>