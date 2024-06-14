function check()
{
	var username = document.getElementById("user").value;
	var pass = document.getElementById("password").value;
	
	if((username=="")||(pass==""))
	{
		alert('All fields are required!');
		return false;
	}
	
}