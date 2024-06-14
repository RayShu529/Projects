function check()
{
	var name = document.getElementById("name").value;
	var lname = document.getElementById("surname").value;
	var username = document.getElementById("user").value;
	var pass1 = document.getElementById("password").value;
	var pass2 = document.getElementById("passwordr").value;
	var contact = document.getElementById("contact").value;
	if((name=="")||(lname=="")||(username=="")||(pass1=="")||(pass2=="")||(contact==""))
	{
		alert('All fields are required!');
		return false;
	}
	else if((pass1!=pass2))
	{
		alert('Password are not the same!');
		return false;
	}
	else if((contact.charAt(0)!="0")||(contact.charAt(1)!="9")||(contact.length!=11))
	{
		alert('Enter a valid number');
		return false;
	}
	
}