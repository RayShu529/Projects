function c()
{
	var choice=confirm("Are you sure?");
	if (choice==true)
	{
		window.location.href="logout.php";
	}
}
function check()
{
	
	var old = document.getElementById("old").value;
	var pass1 = document.getElementById("password1").value;
	var pass2 = document.getElementById("password2").value;
	if((old=="")||(pass1=="")||(pass2==""))
	{
		alert('All fields are required!');
		return false;
	}
	else if((pass1!=pass2))
	{
		alert('Re-enter new password must be the same');
		return false;
	}
	else if((pass1==old))
	{
		alert('New password must be different from the old password');
		return false;
	}
}
function cpn()
{
	
	var cp= document.getElementById("contact").value;
	var pass = document.getElementById("password").value;
	if((cp=="")||(pass==""))
	{
		alert('All fields are required!');
		return false;
	}
	else if((cp.charAt(0)!="0")||(cp.charAt(1)!="9")||(cp.length!=11))
	{
		alert('Enter a valid number');
		return false;
	}
	
}
function usn()
{
	
	var us= document.getElementById("username").value;
	var pass = document.getElementById("password").value;
	if((us=="")||(pass==""))
	{
		alert('All fields are required!');
		return false;
	}
	
	
}