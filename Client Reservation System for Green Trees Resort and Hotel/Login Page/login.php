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
<html >


<head>
        <link rel="stylesheet" href="loginDesign.css">
		<script type="text/javascript" src="log.js"></script>
        <title>GTH&R</title>
</head>

<body>
    <div class = "bg">
            <div>
                    

            </div>
        
            <form action="log.php" method="POST" onsubmit="return check()">
                <div id="container">

                    <img src="../GT pictures/bg.png" alt="Image not available" id="image">
                    <div id="loginForm"> 

                            <input type="text" placeholder="Username" id="user" name="user">
                            <br>
                            <input type="password" placeholder="Password" id="password" name="pass">
                            <br>
                             <button type="submit" id="login">Log in</button><br>
                            <button id="ca"> <a href="../CA Page/createAccount.php" id="catxt"> Create Account</a></button>
                    
                </div>

                </div>

        </form>
    </div>
</body>
</html>
