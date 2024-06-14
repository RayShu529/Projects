<?php
	
$fname = $_POST['fname'];
$lname = $_POST['lname'];
$user = $_POST['user'];
$pass1 = $_POST['pass1'];
$pass2 = $_POST['pass2'];
$contact = $_POST['contact'];
require "../connection.php";

	$stmt = "SELECT * FROM `customer` WHERE fname = '$fname' AND lname = '$lname' ";
	$stmt_query = mysqli_query($con,$stmt);
	if(mysqli_num_rows($stmt_query)==0)
	{
		$stmt = "SELECT * FROM `admin`";
		$stmt_query = mysqli_query($con,$stmt);
		while($take = mysqli_fetch_row($stmt_query))
		{$admin = $take[0];}
		$stmt = "SELECT * FROM `customer` WHERE username = '$user'";
		$stmt_query = mysqli_query($con,$stmt);
		if(mysqli_num_rows($stmt_query)==0&&$user!=$admin)
		{
		$stmt ="INSERT INTO `customer`(`fname`, `lname`, `contact`, `username`, `password`) VALUES ('$fname','$lname','$contact','$user','$pass1')";
		$stmt_query = mysqli_query($con,$stmt);
		echo "<script>window.location='../Login Page/login.php';alert('Registered Successfully');</script>";
		}
		else
		{
		echo "<script>window.location='createAccount.php';alert('Username is already taken');</script>";
		}
	}
	else
	{
		echo "<script>window.location='createAccount.php';alert('Already Registered');</script>";
	}
mysqli_close($con);
?>