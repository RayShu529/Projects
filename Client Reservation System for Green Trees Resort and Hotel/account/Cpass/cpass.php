<?php
	session_start();
	if(!isset($_SESSION["id"]))
	{
	header('Location: ../../Login Page/login.php');
	exit();
	}
?>
<!DOCTYPE html>
<html >


<head>
       <link rel="stylesheet" type="text/css" href="cdesign.css">
		<script type="text/javascript" src="../account.js"></script>
        <title>GTH&R</title>
</head>

<body>
    <div class = "bg">
            <div>
                    

            </div>
        
            <form action="topass.php" method="POST" onsubmit="return check()">
                <div id="container">

                    <img src="../../GT pictures/bg.png" alt="Image not available" id="image">
                    <div id="loginForm"> 
                            <input type="password" placeholder="Password" id="old" name="old">
                            <br>
                            <input type="password" placeholder="New password" id="password1" name="pass1">
                            <br>
							<input type="password" placeholder="Re-enter New password" id="password2" name="pass2">
                            <br>
                            <button type="submit" id="confirm">Confirm</button><br>
							 <button type="button" id="cancel"><a href="../acc.php">Cancel</a></button><br>
                    </div>

                </div>

        </form>
    </div>
</body>
</html>
