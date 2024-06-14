<?php
$user = $_POST['user'];
$pass = $_POST['pass'];
require "../connection.php";
$stmt = "SELECT * FROM `admin` WHERE username = '$user' AND password='$pass'";
$stmt_query = mysqli_query($con,$stmt);
if(mysqli_num_rows($stmt_query)==0)
{
	$stmt = "SELECT * FROM `customer` WHERE username = '$user'";
	$stmt_query = mysqli_query($con,$stmt);
	if(mysqli_num_rows($stmt_query)==0)
	{
		echo "<script>window.location='login.php';alert('Invalid Username');</script>";
	}
	else
	{
		$stmt = "SELECT * FROM `customer` WHERE  username = '$user' AND password = '$pass'";
		$stmt_query = mysqli_query($con,$stmt);
		if(mysqli_num_rows($stmt_query)==0)
		{
		echo "<script>window.location='login.php';alert('Invalid Password');</script>";
		}
		else
		{
			session_start();
			$log = mysqli_fetch_row($stmt_query);
			$_SESSION["id"]=$log[0];
			echo "<script>window.location='../Home Page/Home Page.php';alert('Login Successfully');</script>";
		}
	}
}
else
{
	session_start();
	$_SESSION["id"]="admin";
	echo "<script>window.location='../admin/adminLogin.php';alert('Welcome admin');</script>";
}
mysqli_close($con);
?>