<?php
session_start();
$id=$_SESSION['id'];
require "../../connection.php";
$cpn=$_POST['contact'];
$password=$_POST['password'];
$stmt = "SELECT * FROM `customer` WHERE ID='$id' AND password='$password'";	
$stmt_query = mysqli_query($con,$stmt);
if(mysqli_num_rows($stmt_query)==0)
	{
		echo "<script>window.location='ccontact.php';alert('Incorrect Password');</script>";
	}
else
{	
		$stmt="UPDATE `customer` SET contact = '$cpn' WHERE ID = '$id'";
		$stmt_query = mysqli_query($con,$stmt);
		echo "<script>window.location='../acc.php';alert('Contact change successfully');</script>";
}	

mysqli_close($con);
?>
