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
        
            <form action="tocontact.php" method="POST" onsubmit="return cpn()">
                <div id="container">	

                    <img src="../../GT pictures/bg.png" alt="Image not available" id="image">
                    <div id="loginForm"> 
                            <input type="text" placeholder="New Contact Number" id="contact" name="contact" maxlength="11">
                            <br>
							<input type="password" placeholder="Password" id="password" name="password">
                            <br>							
                            <button type="submit" id="confirm">Confirm</button><br>
							 <button type="button" id="cancel"><a href="../acc.php">Cancel</a></button><br>
                    </div>

                </div>

        </form>
    </div>
</body>
</html>
