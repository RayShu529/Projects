<?php
session_start();
$id=$_SESSION['id'];
require "../../connection.php";
$usn=$_POST['username'];
$password=$_POST['password'];
$stmt = "SELECT * FROM `customer` WHERE ID='$id' AND password='$password'";	
$stmt_query = mysqli_query($con,$stmt);
if(mysqli_num_rows($stmt_query)==0)
	{
		echo "<script>window.location='cuser.php';alert('Incorrect Password');</script>";
	}	
else
	{
		$stmt="UPDATE `customer` SET username = '$usn' WHERE ID = '$id'";
		$stmt_query = mysqli_query($con,$stmt);
		echo "<script>window.location='../acc.php';alert('Username change successfully');</script>";
	}

mysqli_close($con);
?>