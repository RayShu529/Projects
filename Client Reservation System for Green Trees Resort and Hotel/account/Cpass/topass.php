<?php
session_start();
$id=$_SESSION['id'];
require "../../connection.php";
$old=$_POST['old'];
$pass1=$_POST['pass1'];
		$stmt = "SELECT * FROM `customer` WHERE  ID = '$id' AND password = '$old'";
		$stmt_query = mysqli_query($con,$stmt);
		if(mysqli_num_rows($stmt_query)==0)
	{
		echo "<script>window.location='cpass.php';alert('Invalid Password');</script>";
	}
	else
	{
		$stmt="UPDATE `customer` SET `password`='$pass1' WHERE ID = '$id' AND password = '$old'";
		$stmt_query = mysqli_query($con,$stmt);
		echo "<script>window.location='../acc.php';alert('Password change successfully');</script>";
	}

mysqli_close($con);
?>