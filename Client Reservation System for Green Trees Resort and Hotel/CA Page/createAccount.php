<?php
session_start();
	if(isset($_SESSION["id"]))
	{
		if($_SESSION["id"]=="admin")
		{
		header("Location: ../admin/adminLogin.php");
		exit();
		}
		else{
		header("Location: ../Home Page/Home Page.php");
		exit();
		}
	}
?>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="caDesign.css">
	<script type="text/javascript" src="ca.js"></script>
    <title>GTH&R</title>
</head>
<body>
            

            <div id="image">
                        <img src="../GT pictures/bg.png" alt="Image not available" >


                        </div>
        <div id="container">
         
            
            <div id="caDiv">
                    <div id="signuptxt">
                         <h1>Sign Up</h1>
                    </div>             
              <form id="caForm" action="ca.php" method="POST" onsubmit="return check()">

                    <div class="create"> 
                        <input type="text" placeholder="Firstname" id="name" name="fname">
                        <input type="text" placeholder="Surname" id="surname" name="lname">
                        <input type="text" placeholder="Username" id="user" name="user">
                        <input type="password" placeholder="Password" id="password" name="pass1">
                        <input type="password" placeholder="Re-enter Password" id="passwordr" name="pass2">
                        <input type="text" placeholder="Contact Number" id="contact" name="contact" maxlength="11">
                       <button type="button" id="back" ><a href="../Login Page/login.php">Back</a></button><button type="submit" id="signin" >Sign in</button>
						
                    </div>

              </form>
            </div>
        </div>
</body>
</html>
