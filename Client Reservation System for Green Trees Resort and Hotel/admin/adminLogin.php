<?php
require "../connection.php";
session_start();
if($_SESSION["id"]!="admin")
	{
	header('Location: ../Login Page/login.php');
	exit();
	}
?>
<!doctype html>

<html>
<head><title> GRTHS</title>
																					<style type="text/css">
		body{
		
}
caption{
	font-weight:BOLD;
	font-size:50px;
	color:white;
	background-color: rgb(39, 136, 47);
	font-family: 'Times New Roman', Times, serif;
    padding: 8px;
	border-radius: 25px;
	margin-bottom: 30px;
}
		
		table td:last-child ,th:last-child{  border-right: 1px ; }
		tr:first-child{ border-top: 1px solid #000;border-bottom: 1px solid #000;}
		tr:last-child{border-bottom: 1px solid #000;}
#customer,#room,#reservations,#account{display:none;
	text-align:center;
    width: fit-content;
}
#imageTop{

width: 50px;
height: 50px;
float: left;
margin-top: 0px;
margin-left: 30px;
border-radius: 30px;
box-shadow: rgba(0, 0, 0, 0.4) 0px 2px 4px, 
rgba(0, 0.5, 0, 0.6) 0px 7px 13px -3px,
rgba(0, 0.5, 0, 0.7) 0px -3px 0px inset;

}

#pp{
padding: 10px;
font-family: 'Times New Roman', Times, serif;
font-size: 26px;
float: left;
color: white;
text-shadow: 0px 0px 15px rgb(3, 3, 3);
margin-top: 0px;


}
#delete1,#delete2,#delete3{
	font-size:25px;
	width: 200px;
    height:  50px;
    text-decoration: none;
    padding: 10px 40px;
    background-color: #008542;
    color: #fff;
    text-shadow: 0 2px 0 rgb(0 0 0 / 25%);
    display: inline-flex;
    align-items: center;
    justify-content: center;
    border: 0;
    z-index: 1;
    user-select: none;
    cursor: pointer;
    text-transform: uppercase;
    letter-spacing: 1px;
    white-space: unset;
    padding: .8rem 1.5rem;
    text-decoration: none;
    font-weight: 900;
    transition: all 0.7s cubic-bezier(0,.8,.26,.99);
	position:absolute;
	top:650px;
	left:25px;
}
#delete1:before {
    position: absolute;
    pointer-events: none;
    top: 0;
    left: 0;
    display: block;
    width: 100%;
    height: 100%;
    content: '';
    transition: .7s cubic-bezier(0,.8,.26,.99);
    z-index: -1;
    background-color: #008542!important;
    box-shadow: 0 -4px rgb(21 108 0 / 50%) inset, 0 4px rgb(100 253 31 / 99%) inset, -4px 0 rgb(100 253 31 / 50%) inset, 4px 0 rgb(21 108 0 / 50%) inset;
   }
   
   #delete1:after {
    position: absolute;
    pointer-events: none;
    top: 0;
    left: 0;
    display: block;
    width: 100%;
    height: 100%;
    content: '';
    box-shadow: 0 4px 0 0 rgb(0 0 0 / 15%);
    transition: .7s cubic-bezier(0,.8,.26,.99);
   }
   
   #delete1:hover:before {
    box-shadow: 0 -4px rgb(0 0 0 / 50%) inset, 0 4px rgb(255 255 255 / 20%) inset, 
	-4px 0 rgb(255 255 255 / 20%) inset, 4px 0 rgb(0 0 0 / 50%) inset;
   }
   
   #delete1:hover:after {
    box-shadow: 0 4px 0 0 rgb(0 0 0 / 15%);
   }
   
   #delete1:active {
    transform: translateY(4px);
   }
   
   #delete1:active:after {
    box-shadow: 0 0px 0 0 rgb(0 0 0 / 15%);
   }
   #delete2:before {
    position: absolute;
    pointer-events: none;
    top: 0;
    left: 0;
    display: block;
    width: 100%;
    height: 100%;
    content: '';
    transition: .7s cubic-bezier(0,.8,.26,.99);
    z-index: -1;
    background-color: #008542!important;
    box-shadow: 0 -4px rgb(21 108 0 / 50%) inset, 0 4px rgb(100 253 31 / 99%) inset, -4px 0 rgb(100 253 31 / 50%) inset, 4px 0 rgb(21 108 0 / 50%) inset;
   }
   
   #delete2:after {
    position: absolute;
    pointer-events: none;
    top: 0;
    left: 0;
    display: block;
    width: 100%;
    height: 100%;
    content: '';
    box-shadow: 0 4px 0 0 rgb(0 0 0 / 15%);
    transition: .7s cubic-bezier(0,.8,.26,.99);
   }
   
   #delete2:hover:before {
    box-shadow: 0 -4px rgb(0 0 0 / 50%) inset, 0 4px rgb(255 255 255 / 20%) inset, 
	-4px 0 rgb(255 255 255 / 20%) inset, 4px 0 rgb(0 0 0 / 50%) inset;
   }
   
   #delete2:hover:after {
    box-shadow: 0 4px 0 0 rgb(0 0 0 / 15%);
   }
   
   #delete2:active {
    transform: translateY(4px);
   }
   
   #delete2:active:after {
    box-shadow: 0 0px 0 0 rgb(0 0 0 / 15%);
   }

   #delete3:before {
    position: absolute;
    pointer-events: none;
    top: 0;
    left: 0;
    display: block;
    width: 100%;
    height: 100%;
    content: '';
    transition: .7s cubic-bezier(0,.8,.26,.99);
    z-index: -1;
    background-color: #008542!important;
    box-shadow: 0 -4px rgb(21 108 0 / 50%) inset, 0 4px rgb(100 253 31 / 99%) inset, -4px 0 rgb(100 253 31 / 50%) inset, 4px 0 rgb(21 108 0 / 50%) inset;
   }
   
   #delete3:after {
    position: absolute;
    pointer-events: none;
    top: 0;
    left: 0;
    display: block;
    width: 100%;
    height: 100%;
    content: '';
    box-shadow: 0 4px 0 0 rgb(0 0 0 / 15%);
    transition: .7s cubic-bezier(0,.8,.26,.99);
   }
   
   #delete3:hover:before {
    box-shadow: 0 -4px rgb(0 0 0 / 50%) inset, 0 4px rgb(255 255 255 / 20%) inset, 
	-4px 0 rgb(255 255 255 / 20%) inset, 4px 0 rgb(0 0 0 / 50%) inset;
   }
   
   #delete3:hover:after {
    box-shadow: 0 4px 0 0 rgb(0 0 0 / 15%);
   }
   
   #delete3:active {
    transform: translateY(4px);
   }
   
   #delete3:active:after {
    box-shadow: 0 0px 0 0 rgb(0 0 0 / 15%);
   }

#add1,#add2{

	font-size:25px;
	width: 200px;
    height:  50px;
    text-decoration: none;
    padding: 10px 40px;
    background-color: #008542;
    color: #fff;
    text-shadow: 0 2px 0 rgb(0 0 0 / 25%);
    display: inline-flex;
    align-items: center;
    justify-content: center;
    border: 0;
    z-index: 1;
    user-select: none;
    cursor: pointer;
    text-transform: uppercase;
    letter-spacing: 1px;
    white-space: unset;
    padding: .8rem 1.5rem;
    text-decoration: none;
    font-weight: 900;
    transition: all 0.7s cubic-bezier(0,.8,.26,.99);
	position:absolute;
	top:720px;
	left:25px;

}
#add1:before {
    position: absolute;
    pointer-events: none;
    top: 0;
    left: 0;
    display: block;
    width: 100%;
    height: 100%;
    content: '';
    transition: .7s cubic-bezier(0,.8,.26,.99);
    z-index: -1;
    background-color: #008542!important;
    box-shadow: 0 -4px rgb(21 108 0 / 50%) inset, 0 4px rgb(100 253 31 / 99%) inset, -4px 0 rgb(100 253 31 / 50%) inset, 4px 0 rgb(21 108 0 / 50%) inset;
   }
   
   #add1:after {
    position: absolute;
    pointer-events: none;
    top: 0;
    left: 0;
    display: block;
    width: 100%;
    height: 100%;
    content: '';
    box-shadow: 0 4px 0 0 rgb(0 0 0 / 15%);
    transition: .7s cubic-bezier(0,.8,.26,.99);
   }
   
   #add1:hover:before {
    box-shadow: 0 -4px rgb(0 0 0 / 50%) inset, 0 4px rgb(255 255 255 / 20%) inset, 
	-4px 0 rgb(255 255 255 / 20%) inset, 4px 0 rgb(0 0 0 / 50%) inset;
   }
   
   #add1:hover:after {
    box-shadow: 0 4px 0 0 rgb(0 0 0 / 15%);
   }
   
   #add1:active {
    transform: translateY(4px);
   }
   
   #add1:active:after {
    box-shadow: 0 0px 0 0 rgb(0 0 0 / 15%);
   }

   #add2:before {
    position: absolute;
    pointer-events: none;
    top: 0;
    left: 0;
    display: block;
    width: 100%;
    height: 100%;
    content: '';
    transition: .7s cubic-bezier(0,.8,.26,.99);
    z-index: -1;
    background-color: #008542!important;
    box-shadow: 0 -4px rgb(21 108 0 / 50%) inset, 0 4px rgb(100 253 31 / 99%) inset, -4px 0 rgb(100 253 31 / 50%) inset, 4px 0 rgb(21 108 0 / 50%) inset;
   }
   
   #add2:after {
    position: absolute;
    pointer-events: none;
    top: 0;
    left: 0;
    display: block;
    width: 100%;
    height: 100%;
    content: '';
    box-shadow: 0 4px 0 0 rgb(0 0 0 / 15%);
    transition: .7s cubic-bezier(0,.8,.26,.99);
   }
   
   #add2:hover:before {
    box-shadow: 0 -4px rgb(0 0 0 / 50%) inset, 0 4px rgb(255 255 255 / 20%) inset, 
	-4px 0 rgb(255 255 255 / 20%) inset, 4px 0 rgb(0 0 0 / 50%) inset;
   }
   
   #add2:hover:after {
    box-shadow: 0 4px 0 0 rgb(0 0 0 / 15%);
   }
   
   #add2:active {
    transform: translateY(4px);
   }
   
   #add2:active:after {
    box-shadow: 0 0px 0 0 rgb(0 0 0 / 15%);
   }
		#customer,#room,#reservations,#account{display:none;text-align:center;}
body{
	margin: 0%;
	}
	table, th, td {
	border: 1px black solid;
}
table {
	border-collapse: collapse;
}
table {
	border-spacing: 10px 10px;
}

table {
	width: 800px;
}
table{
    position: absolute;
    top: 200px;
    right: 113px;
    color: white;
    font-family: 'Times New Roman', Times, serif;

}

th {

	height: 30px;
}

th, td {
	padding: 10px;
}

th {
	text-align: center;
}

td {
	text-align: center;
}

td {
    height: 40px;
    vertical-align: bottom;
    }

    th, td {
        border-bottom: 1px solid black;
    }

    tr:hover {
        background-color: rgb(39, 136, 47);
        }

        th {
            background-color: rgb(20, 116, 33);
            color: white;
        }

        tr {
            background-color: rgba(20, 116, 33, 0.616);
        }

#userform{
	position:absolute;
	top: 20px;
	display:none;
	background-color: #008542;
	border-radius: 10px;
	width:180px;
	height:100px;
	padding:10px;

	}
	#passform{
	position:absolute;
	top: 20px;
	display:none;
	background-color: #008542;
	border-radius: 10px;
	width:180px;
	height:100px;
	padding:10px;

	}
	#dform{
		position:absolute;
	top:  500px;
	left:25px;
	display:none;
	background-color: #008542;
	border-radius: 20px;
	width:180px;
	height:100px;
	padding:10px;

	}
	#serveadd{
	position:absolute;
	top:  650px;
	left:25px;
	display:none;
	background-color: #008542;
	border-radius: 20px;
	width:180px;
	height:fit-content;
	padding: 10px;
	text-align: center;
	
	}
	#servedelete{
	position:absolute;
	top:  650px;
	left:25px;
	display:none;
	background-color: #008542;
	border-radius: 20px;
	width:180px;
	height:fit-content;
	padding: 10px;
	text-align: center;
	
	}
	#rdeleteform{
	position:absolute;
	top:  650px;
	left:25px;
	display:none;
	background-color: #008542;
	border-radius: 20px;
	width:180px;
	height:fit-content;
	padding: 10px;
	text-align: center;
	
	}
	#raddform{
	position:absolute;
	top:  650px;
	left:25px;
	display:none;
	background-color: #008542;
	border-radius: 20px;
	width:180px;
	height:fit-content;
	padding: 10px;
	text-align: center;
	
	}
.serves{
	font-family: 'Times New Roman', Times, serif;
	padding:5px;
	border-radius:15px;
}

	

	

   

input,select{
		width:80%;
		height: 20%;
		font-size:15px;
		border-radius: 10px;
		margin:5px;
	}

#topdiv{
	background-color:rgb(9, 102, 29);	 
    width: 100%;
    height: 40px;
	box-shadow: rgba(0, 0, 0, 0.4) 0px 2px 4px, 
rgba(0, 0.5, 0, 0.6) 0px 2px 10px -3px,
rgba(0, 0.5, 0, 0.7) 0px -1px 0px inset;
		}

#div2{

	width: 20%;
	height:1600px;
	background: rgba(9, 102, 29, 0.822);
	box-shadow: rgba(0, 0, 0, 0.4) 0px 2px 4px, 
rgba(0, 0.5, 0, 0.6) 0px 7px 13px -3px,
rgba(0, 0.5, 0, 0.7) 0px -3px 0px inset;
	}

#customerbtn{
	font-size:25px;
	width: 200px;
    height:  50px;
    text-decoration: none;
    padding: 10px 40px;
    background-color: #008542;
    color: #fff;
    text-shadow: 0 2px 0 rgb(0 0 0 / 25%);
    display: inline-flex;
    align-items: center;
    justify-content: center;
    position: absolute;
    top: 200px;
    left: 27px;
    border: 0;
    z-index: 1;
    user-select: none;
    cursor: pointer;
    text-transform: uppercase;
    letter-spacing: 1px;
    white-space: unset;
    padding: .8rem 1.5rem;
    text-decoration: none;
    font-weight: 900;
    transition: all 0.7s cubic-bezier(0,.8,.26,.99);
   }   #customerbtn:before {
    position: absolute;
    pointer-events: none;
    top: 0;
    left: 0;
    display: block;
    width: 100%;
    height: 100%;
    content: '';
    transition: .7s cubic-bezier(0,.8,.26,.99);
    z-index: -1;
    background-color: #008542!important;
    box-shadow: 0 -4px rgb(21 108 0 / 50%) inset, 0 4px rgb(100 253 31 / 99%) inset, -4px 0 rgb(100 253 31 / 50%) inset, 4px 0 rgb(21 108 0 / 50%) inset;
   }
   
   #customerbtn:after {
    position: absolute;
    pointer-events: none;
    top: 0;
    left: 0;
    display: block;
    width: 100%;
    height: 100%;
    content: '';
    box-shadow: 0 4px 0 0 rgb(0 0 0 / 15%);
    transition: .7s cubic-bezier(0,.8,.26,.99);
   }
   
   #customerbtn:hover:before {
    box-shadow: 0 -4px rgb(0 0 0 / 50%) inset, 0 4px rgb(255 255 255 / 20%) inset, 
	-4px 0 rgb(255 255 255 / 20%) inset, 4px 0 rgb(0 0 0 / 50%) inset;
   }
   
   #customerbtn:hover:after {
    box-shadow: 0 4px 0 0 rgb(0 0 0 / 15%);
   }
   
   #customerbtn:active {
    transform: translateY(4px);
   }
   
   #customerbtn:active:after {
    box-shadow: 0 0px 0 0 rgb(0 0 0 / 15%);
   }
   
#roombtn{
	font-size:25px;
	width: 200px;
    height:  50px;
    text-decoration: none;
    padding: 10px 40px;
    background-color: #008542;
    color: #fff;
    text-shadow: 0 2px 0 rgb(0 0 0 / 25%);
    display: inline-flex;
    align-items: center;
    justify-content: center;
    position: absolute;
    top: 280px;
    left: 27px;
    border: 0;
    z-index: 1;
    user-select: none;
    cursor: pointer;
    text-transform: uppercase;
    letter-spacing: 1px;
    white-space: unset;
    padding: .8rem 1.5rem;
    text-decoration: none;
    font-weight: 900;
    transition: all 0.7s cubic-bezier(0,.8,.26,.99);

}#roombtn:before {
    position: absolute;
    pointer-events: none;
    top: 0;
    left: 0;
    display: block;
    width: 100%;
    height: 100%;
    content: '';
    transition: .7s cubic-bezier(0,.8,.26,.99);
    z-index: -1;
    background-color: #008542!important;
    box-shadow: 0 -4px rgb(21 108 0 / 50%) inset, 0 4px rgb(100 253 31 / 99%) inset, -4px 0 rgb(100 253 31 / 50%) inset, 4px 0 rgb(21 108 0 / 50%) inset;
   }
   
   #roombtn:after {
    position: absolute;
    pointer-events: none;
    top: 0;
    left: 0;
    display: block;
    width: 100%;
    height: 100%;
    content: '';
    box-shadow: 0 4px 0 0 rgb(0 0 0 / 15%);
    transition: .7s cubic-bezier(0,.8,.26,.99);
   }
   
   #roombtn:hover:before {
    box-shadow: 0 -4px rgb(0 0 0 / 50%) inset, 0 4px rgb(255 255 255 / 20%) inset, -4px 0 rgb(255 255 255 / 20%) inset, 4px 0 rgb(0 0 0 / 50%) inset;
   }
   
   #roombtn:hover:after {
    box-shadow: 0 4px 0 0 rgb(0 0 0 / 15%);
   }
   
   #roombtn:active {
    transform: translateY(4px);
   }
   
   #roombtn:active:after {
    box-shadow: 0 0px 0 0 rgb(0 0 0 / 15%);
   }

#reservebtn{

	font-size:20px;
	width: 200px;
    height:  50px;
    text-decoration: none;
    padding: 10px 40px;
    background-color: #008542;
    color: #fff;
    text-shadow: 0 2px 0 rgb(0 0 0 / 25%);
    display: inline-flex;
    align-items: center;
    justify-content: center;
    position: absolute;
    top: 360px;
    left: 27px;
    border: 0;
    z-index: 1;
    user-select: none;
    cursor: pointer;
    text-transform: uppercase;
    letter-spacing: 1px;
    white-space: unset;
    padding: .8rem 1.5rem;
    text-decoration: none;
    font-weight: 900;
    transition: all 0.7s cubic-bezier(0,.8,.26,.99);

}
#reservebtn:before {
    position: absolute;
    pointer-events: none;
    top: 0;
    left: 0;
    display: block;
    width: 100%;
    height: 100%;
    content: '';
    transition: .7s cubic-bezier(0,.8,.26,.99);
    z-index: -1;
    background-color: #008542!important;
    box-shadow: 0 -4px rgb(21 108 0 / 50%) inset, 0 4px rgb(100 253 31 / 99%) inset, -4px 0 rgb(100 253 31 / 50%) inset, 4px 0 rgb(21 108 0 / 50%) inset;
   }
   
   #reservebtn:after {
    position: absolute;
    pointer-events: none;
    top: 0;
    left: 0;
    display: block;
    width: 100%;
    height: 100%;
    content: '';
    box-shadow: 0 4px 0 0 rgb(0 0 0 / 15%);
    transition: .7s cubic-bezier(0,.8,.26,.99);
   }
   
   #reservebtn:hover:before {
    box-shadow: 0 -4px rgb(0 0 0 / 50%) inset, 0 4px rgb(255 255 255 / 20%) inset, -4px 0 rgb(255 255 255 / 20%) inset, 4px 0 rgb(0 0 0 / 50%) inset;
   }
   
   #reservebtn:hover:after {
    box-shadow: 0 4px 0 0 rgb(0 0 0 / 15%);
   }
   #reservebtn:active {
    transform: translateY(4px);
   }
   
#reservebtn:active:after {
    box-shadow: 0 0px 0 0 rgb(0 0 0 / 15%);
   }



#accountbtn{

	font-size:20px;
	width: 150px;
    height:  40px;
    text-decoration: none;
    padding: 10px 40px;
    background-color: #008542;
    color: #fff;
    text-shadow: 0 2px 0 rgb(0 0 0 / 25%);
    display: inline-flex;
    align-items: center;
    justify-content: center;
	float: right;
    border: 0;
    z-index: 1;
    user-select: none;
    cursor: pointer;
    text-transform: uppercase;
    letter-spacing: 1px;
    white-space: unset;
    padding: .8rem 1.5rem;
    text-decoration: none;
    font-weight: 900;
    transition: all 0.7s cubic-bezier(0,.8,.26,.99);
}
#accountbtn:before {
    position: absolute;
    pointer-events: none;
    top: 0;
    left: 0;
    display: block;
    width: 100%;
    height: 100%;
    
    transition: .7s cubic-bezier(0,.8,.26,.99);
    z-index: -1;
    background-color: #008542!important;
    box-shadow: 0 -4px rgb(21 108 0 / 50%) inset, 0 4px rgb(100 253 31 / 99%) inset, -4px 0 rgb(100 253 31 / 50%) inset, 4px 0 rgb(21 108 0 / 50%) inset;
   }
   
   #accountbtn:after {
    position: absolute;
    pointer-events: none;
    top: 0;
    left: 0;
    display: block;
    width: 100%;
    height: 100%;
    
    box-shadow: 0 4px 0 0 rgb(0 0 0 / 15%);
    transition: .7s cubic-bezier(0,.8,.26,.99);
   }
   #accountbtn:hover:before {
    box-shadow: 0 -4px rgb(0 0 0 / 50%) inset, 0 4px rgb(255 255 255 / 20%) inset, -4px 0 rgb(255 255 255 / 20%) inset, 4px 0 rgb(0 0 0 / 50%) inset;
   }
   
   #accountbtn:hover:after {
    box-shadow: 0 4px 0 0 rgb(0 0 0 / 15%);
   }
   
   #accountbtn:active {
    transform: translateY(4px);
   }
   
   #accountbtn:active:after {
    box-shadow: 0 0px 0 0 rgb(0 0 0 / 15%);
   }



#account{

    text-decoration: none;
	position: absolute;
	top: 40px;
	right: 50px;
	padding: 20px;

}

#logout, #userad,#passad{
	text-align:center; background-color: 
	red;margin:10px 10px 0px 0px;
	font-weight:BOLD;
	padding: 0px;
    font-size: 10px;
    background-color: #eb0b0b;
    color: #fff;
    text-shadow: 0 2px 0 rgb(0 0 0 / 25%);
    display: inline-flex;
    align-items: center;
    justify-content: center;
    border: 0;
    z-index: 1;
    user-select: none;
    cursor: pointer;
    text-transform: uppercase;
    letter-spacing: 1px;
    white-space: unset;
    
    text-decoration: none;
    font-weight: 900;
	width: 140px;
    height:  30px;
    transition: all 0.3s cubic-bezier(0,.8,.26,.99);
		}
	


																					</style>
																					<script type="text/javascript">
	function d()
{
	var currentDate  = new Date();
	currentMonth = currentDate.getMonth()+1;
	currentDay = currentDate.getDate();
	currentYear = currentDate.getFullYear();
		if(currentDate.getDate()<10)
		{
			currentDay   = "0" + currentDate.getDate();
		}
		if(currentDate.getMonth()<10)
		{
			currentMonth = "0" + currentDate.getMonth();
		}

		today=currentYear+"-"+currentMonth+"-"+currentDay;
		document.getElementById('date').setAttribute("min",today);	
}																				
				
	function start()
	{
		 if ( window.history.replaceState ) 
		{
		window.history.replaceState( null, null, window.location.href );
		}
	}
	function check()
	{
		var id=document.getElementById("userid");
		var adpass=document.getElementById("adpass1");
		if(id.value==""||adpass.value=="")
		{
			alert("All Fields are required");
			return false;
		}
	}
	function caction()
	{
		var dform=document.getElementById("dform");
		var btn1=document.getElementById("delete1");
		var customer=document.getElementById("customer");
		var serve=document.getElementById("reservations");
		if(customer.style.display == "none")
		{
			room.style.display = "none";
			serve.style.display = "none";
			customer.style.display = "block";
			dform.style.display = "none";
			btn1.style.visibility = "visible";
		}
		else
		{
			customer.style.display = "none";
			
		}
	}
	function raction()
	{
		var form=document.getElementById("raddform");
		var form1=document.getElementById("rdeleteform");
		var btn=document.getElementById("add1");
		var btn1=document.getElementById("delete2");
		var room=document.getElementById("room");
		var serve=document.getElementById("reservations");
		var customer=document.getElementById("customer");
		if(room.style.display == "none")
		{
			customer.style.display = "none";
			serve.style.display = "none";
			form.style.display = "none";
			form1.style.display = "none";
			room.style.display = "block";
			btn.style.visibility = "visible";
			btn1.style.visibility = "visible";
			
		}
		else
		{
			room.style.display = "none";
			
		}
	}
	function serveaction()
	{
		var form=document.getElementById("serveadd");
		var form1=document.getElementById("servedelete");
		var btn=document.getElementById("add2");
		var btn1=document.getElementById("delete3");
		var room=document.getElementById("room");
		var customer=document.getElementById("customer");
		var serve=document.getElementById("reservations");
		if(serve.style.display == "none")
		{
			customer.style.display = "none";
			room.style.display = "none";
			form.style.display = "none";
			form1.style.display = "none";
			serve.style.display = "block"
			btn.style.visibility = "visible";
			btn1.style.visibility = "visible";
			
		}
		else
		{
			serve.style.display = "none";
			
		}
	}
	function q()
	{
		var dform=document.getElementById("dform");
		var btn=document.getElementById("delete1");
		if(dform.style.display == "none")
		{
			dform.style.display = "block";
			window.location.href="#dform";
			btn.style.visibility = "hidden";
		}
		else
		{
			dform.style.display = "none";
			window.location.href="#delete1";
			btn.style.visibility = "visible";
		}
	}
	function radd()
	{
		
		var form=document.getElementById("raddform");
		var btn=document.getElementById("add1");
		var btn1=document.getElementById("delete2");
		if(form.style.display == "none")
		{
			form.style.display = "block";
			window.location.href="#form";
			btn.style.visibility = "hidden";
			btn1.style.visibility = "hidden";
		}
		else
		{
			form.style.display = "none";
			window.location.href="#radd1";
			btn.style.visibility = "visible";
			btn1.style.visibility = "visible";
		}
	}
	function rcheck2()
	{
		var roomno=document.getElementById("roomno2");
		var type=document.getElementById("type2");
		var adpass=document.getElementById("adpass2");
		if(roomno.value==""||adpass.value==""||type.value=="")
		{
			alert("All Fields are required");
			return false;
		}
	}
	function rdelete()
	{
		var form=document.getElementById("rdeleteform");
		var btn=document.getElementById("add1");
		var btn1=document.getElementById("delete2");
		if(form.style.display == "none")
		{
			form.style.display = "block";
			window.location.href="#rdeleteform";
			btn.style.visibility = "hidden";
			btn1.style.visibility = "hidden";
		}
		else
		{
			form.style.display = "none";
			window.location.href="#delete2";
			btn.style.visibility = "visible";
			btn1.style.visibility = "visible";
		}
	}
	function rcheck3()
	{
		var roomno=document.getElementById("roomno3");	
		var adpass=document.getElementById("adpass3");
		if(roomno.value==""||adpass.value=="")
		{
			alert("All Fields are required");
			return false;
		}
	}
	function sadd()
	{
		
		var form=document.getElementById("serveadd");
		var btn=document.getElementById("add2");
		var btn1=document.getElementById("delete3");
		if(form.style.display == "none")
		{
			form.style.display = "block";
			window.location.href="#serveadd";
			btn.style.visibility = "hidden";
			btn1.style.visibility = "hidden";
		}
		else
		{
			form.style.display = "none";
			window.location.href="#serveadd";
			btn.style.visibility = "visible";
			btn1.style.visibility = "visible";
		}
	}	
	function sdelete()
	{
		
		var form=document.getElementById("servedelete");
		var btn=document.getElementById("add2");
		var btn1=document.getElementById("delete3");
		if(form.style.display == "none")
		{
			form.style.display = "block";
			window.location.href="#servedelete";
			btn.style.visibility = "hidden";
			btn1.style.visibility = "hidden";
		}
		else
		{
			form.style.display = "none";
			window.location.href="#servedelete";
			btn.style.visibility = "visible";
			btn1.style.visibility = "visible";
		}
	}
	function servecheck4()
	{
		var id=document.getElementById("userve");	
		var roomno=document.getElementById("rserve");
		var dura = document.querySelector('input[name="dserve"]:checked');
		var date = document.getElementById('date');
		var adpass = document.getElementById('adpass4');
		if(id.value==""||roomno.value==""||dura==null||date.value==""||adpass.value=="")
		{
			alert("All Fields are required");
			return false;
		}
	}	
	function servecheck5()
	{
		var roomno=document.getElementById("rserve2");
		var date = document.getElementById('date2');
		var adpass = document.getElementById('adpass5');
		if(roomno.value==""||date.value==""||adpass.value=="")
		{
			alert("All Fields are required");
			return false;
		}
	}	
		function accountaction()
	{
		var div=document.getElementById("account");
		var btn=document.getElementById("userad");
		var btn1=document.getElementById("passad");
		
		var form=document.getElementById("userform");
		var form1=document.getElementById("passform");
		
		if(div.style.display == "none")
		{
			form.style.display = "none";
			form1.style.display = "none";
			div.style.display = "block";
			btn.style.visibility = "visible";
			btn1.style.visibility = "visible";
			
		}
		else
		{
			div.style.display = "none";
			
		}
	}
	function cusername()
	{

		var btn=document.getElementById("userad");
		var btn1=document.getElementById("passad");
		var form=document.getElementById("userform");
		if(form.style.display == "none")
		{
			form.style.display = "block";
			btn.style.visibility = "hidden";
			btn1.style.visibility = "hidden";
			
		}
		else
		{
			form.style.display = "none";
			btn.style.visibility = "visible";
			btn1.style.visibility = "visible";
			
		}
	}
	function cpassword()
	{

		var btn=document.getElementById("userad");
		var btn1=document.getElementById("passad");
		var form=document.getElementById("passform");
		if(form.style.display == "none")
		{
			form.style.display = "block";
			btn.style.visibility = "hidden";
			btn1.style.visibility = "hidden";
			
		}
		else
		{
			form.style.display = "none";
			btn.style.visibility = "visible";
			btn1.style.visibility = "visible";
			
		}
	}
	function admin()
	{

		var username=document.getElementById("useradmin");
		var password=document.getElementById("adminpass");
		if(username.value==""||password.value=="")
		{
			alert("All field are required!!");
			return false;
		}
	}
	function admin2()
	{

		var newpass=document.getElementById("newpass");
		var password=document.getElementById("adminpass2");
		if(newpass.value==""||password.value=="")
		{
			alert("All field are required!!");
			return false;
		}
	}
	function c()
{
	var choice=confirm("Are you sure?");
	if (choice==false)
	{
	return false;
	}
	else{return;}
}
	</script>
</head>
<body onload="start()">

<div id=alldiv> 
		<div id="topdiv"><img src="../GT pictures/bg.png" alt="Image not available" id="imageTop">
				<p id="pp">Green Trees Hotel & Resort</p>	
				<button id="accountbtn" onclick="accountaction()">Settings</button><br>
		</div>
		
		<div id="account">
			<button type="button" id="userad" onclick="cusername()">Change Username</button><br>
			<button type="button" id="passad" onclick="cpassword()">Change Password</button>
				<form method='post' id="broom" action='<?php echo htmlspecialchars($_SERVER['PHP_SELF']);?>' onsubmit="return c()">
			  				<button id="logout" type="submit" name="leave">Logout</button>
						</form>
							<form method="post" action="<?php echo htmlspecialchars($_SERVER["PHP_SELF"]);?>" id="userform" onsubmit=" return admin()">
									<input type="text" placeholder="New username" id="useradmin" name="useradmin"><br>
									<input type="password" placeholder="Admin Password" id="adminpass" name="adminpass"><br>
									<button type="submit" name="changeuser">Change</button>
									<button type="button" onclick="cusername()">Cancel</button>
							</form>
							<form method="post" action="<?php echo htmlspecialchars($_SERVER["PHP_SELF"]);?>" id="passform" onsubmit=" return admin2()">
									<input type="password" placeholder="New password" id="newpass" name="newpass"><br>
									<input type="password" placeholder="Admin Password" id="adminpass2" name="adminpass2"><br>
									<button type="submit" name="changepass">Change</button>
									<button type="button" onclick="cpassword()">Cancel</button>
							</form>
					
		</div>
		<div id="out">

			</div>
<div id="div2">


<div id="btn">
			<button id="customerbtn" onclick="caction()">Customer</button><br>
			<button id="roombtn" onclick="raction()">Rooms</button><br>
			<button id="reservebtn" onclick="serveaction()">Reservations</button><br>
		
			</div>
</div>
		</div>

<div id="room">
	<table>
	<caption>Rooms</caption>
	<br>
	<tr>
		<th>Room No.</th>
		<th>Type</th>
	</tr>
<?php
	
	$stmt = "SELECT * FROM `rooms`";
	$stmt_query = mysqli_query($con,$stmt);
	while($display = mysqli_fetch_row($stmt_query))
	{
		echo"<tr>
		<td>$display[0]</td>
		<td>$display[1]</td>
		</tr>";
	}
?>
	</table>
<div >
	<button type="button" onclick="radd()" id="add1">Add</button>
	
		<form method="post" action="<?php echo htmlspecialchars($_SERVER["PHP_SELF"]);?>" id="raddform" onsubmit=" return rcheck2()">
						<input type="text" class="serves" placeholder="Room Number" id="roomno2" name="roomno2"><br>
						<select name="type2"  class="serves" id="type2">
						<option value="" selected disabled>Type</option>
						<option value="vip">Vip</option>
						<option value="acuw/cr">ACU w/ C.R.</option>
						<option value="acuw/ocr">ACU w/o C.R.</option>
						<option value="ordinaryw/cr">Ordinary w/ C.R.</option>
						<option value="ordinaryw/ocr">Ordinary w/o C.R.</option>
						</select><br>
                        <input type="password" class="serves" placeholder="Admin Password" id="adpass2" name="adpass2"><br>
                        <button type="submit"  class="serves" name="addroom">Add</button>
						<button type="button"  class="serves" onclick="radd()">Cancel</button>
		</form>
		<form method="post" action="<?php echo htmlspecialchars($_SERVER["PHP_SELF"]);?>" id="rdeleteform" onsubmit=" return rcheck3()">
						<input type="text"  class="serves" placeholder="Room Number" id="roomno3" name="roomno3"><br>
                        <input type="password"   class="serves" placeholder="Admin Password" id="adpass3" name="adpass3"><br>
                        <button type="submit"  class="serves" name="deleteroom">Delete</button>
						<button type="button"  class="serves" onclick="rdelete()">Cancel</button>
		</form>
</div>
</div>
<div id="reservations">
	<table>
		<caption>Reservations</caption>
		<br>
		<tr>
		<th>UserID</th>
		<th>Room Number</th>
		<th>Date</th>
		<th>Duration</th>
		</tr>
<?php
	
	$stmt = "SELECT * FROM `reservation`";
	$stmt_query = mysqli_query($con,$stmt);
	while($display = mysqli_fetch_row($stmt_query))
	{
		echo"<tr>
		<td>$display[0]</td>
		<td>$display[1]</td>
		<td>$display[2]</td>
		<td>$display[3]</td>
		</tr>";
	}
?>
	</table>

	
<div id="basta">


	<button type="button" onclick="sadd()" id="add2">Add</button>
	
		<form method="post" action="<?php echo htmlspecialchars($_SERVER["PHP_SELF"]);?>" id="serveadd" onsubmit=" return servecheck4()">
						<input type="text" class="serves" placeholder="User ID" id="userve" name="userve"><br>
						<input type="text" class="serves"  placeholder="Room Number" id="rserve" name="rserve"><br>
						<p>DURATION</p>
						<label>
						<input type="radio" class="serves" name="dserve" id="three" value="3 hrs">3 hrs</input>
						</label><br>
						<label>
						<input type="radio" class="serves" name="dserve" id="six" value="6 hrs">6 hrs</input>
						</label><br>
						<label>
						<input type="radio" class="serves"name="dserve" id="twelve" value="12 hrs">12 hrs</input>
						</label><br>
						<label>
						<input type="radio" class="serves" name="dserve" id="twofour" value="24 hrs">24 hrs</input>
						</label><br><br>
						<label>DATE:<input type="date" class="serves" id="date" name="date" onclick="d()"></input></label><br>
                        <input type="password" class="serves" placeholder="Admin Password" id="adpass4" name="adpass4"><br>
                        <button type="submit" class="serves" name="addserve">Add</button>
						<button type="button" class="serves" onclick="sadd()">Cancel</button>
		</form>
		<form method="post" action="<?php echo htmlspecialchars($_SERVER["PHP_SELF"]);?>" id="servedelete" onsubmit=" return servecheck5()">
						<input type="text" placeholder="Room Number" class="serves" id="rserve2" name="rserve2"><br>
						<label>DATE:<input type="date" id="date2" name="date2" ></input></label><br>
                        <input type="password" placeholder="Admin Password" class="serves" id="adpass5" name="adpass5"><br>
                        <button  type="submit" name="delserve">Delete</button>
						<button type="button" onclick="sdelete()">Cancel</button>
		</form>
</div>
</div>
<div id="customer">
	<table>
		<caption>Customer Info</caption>
		<br>
		<tr>
		<th>UserID</th>
		<th>First Name</th>
		<th>Last Name</th>
		<th>Contact</th>
		<th>Username</th>
		<th>Password</th>
		</tr>
<?php

	$stmt = "SELECT * FROM `customer`";
	$stmt_query = mysqli_query($con,$stmt);
	while($display = mysqli_fetch_row($stmt_query))
	{
		echo"<tr>
		<td>$display[0]</td>
		<td>$display[1]</td>
		<td>$display[2]</td>
		<td>$display[3]</td>
		<td>$display[4]</td>
		<td>$display[5]</td>
		</tr>";
	}
?>
	</table>
<div>
	
				<form method="post" action="<?php echo htmlspecialchars($_SERVER["PHP_SELF"]);?>" id="dform" onsubmit=" return check()">
						<input type="text" placeholder="UserID" id="userid" name="userid"><br>
                        <input type="password" placeholder="Admin Password" id="adpass1" name="adpass1"><br>
                        <button type="submit" name="deleteuser">Delete</button>
						<button type="button" onclick="q()">Cancel</button>
				</form>
</div>
</div>


</body>
</html>

<?php
if(isset($_REQUEST['deleteuser']))
{
	$userid=$_REQUEST['userid'];
	$adpass=$_REQUEST['adpass1'];

$stmt = "SELECT * FROM `admin` WHERE password='$adpass'";
$stmt_query = mysqli_query($con,$stmt);
if(mysqli_num_rows($stmt_query)==0)
{
	echo "<script>alert('Incorrect password');</script>";
}
else
{
	$stmt = "SELECT * FROM `customer` WHERE ID='$userid'";
	$stmt_query = mysqli_query($con,$stmt);
	if(mysqli_num_rows($stmt_query)==0)
{
	echo "<script>alert('User ID not found');</script>";
}
	else
	{
		$stmt = "DELETE FROM `customer` WHERE ID ='$userid'";
		$stmt_query = mysqli_query($con,$stmt);
		echo "<script>window.location.href='adminLogin.php';alert('User deleted succesfully');</script>";
	}

}
}
else if(isset($_REQUEST['addroom']))
{
	$room=$_REQUEST['roomno2'];
	$type=$_REQUEST['type2'];
	$adpass=$_REQUEST['adpass2'];

	$stmt = "SELECT * FROM `admin` WHERE password='$adpass'";
	$stmt_query = mysqli_query($con,$stmt);
if(mysqli_num_rows($stmt_query)==0)
{
	echo "<script>alert('Incorrect password');</script>";
}
else
{
	$stmt = "SELECT * FROM `rooms` WHERE roomno ='$room'";
	$stmt_query = mysqli_query($con,$stmt);
	if(mysqli_num_rows($stmt_query)==0)
	{
	$stmt = "INSERT INTO `rooms`(`roomno`, `type`) VALUES ('$room','$type')";
	$stmt_query = mysqli_query($con,$stmt);
	echo "<script>window.location.href='adminlogin.php';alert('Room Added Successfully');</script>";
	}
	else
	{
		echo "<script>alert('Room Number already taken Successfully');</script>";
	}
}

}
else if(isset($_REQUEST['deleteroom']))
{
	$room=$_REQUEST['roomno3'];
	$adpass=$_REQUEST['adpass3'];

	$stmt = "SELECT * FROM `admin` WHERE password='$adpass'";
	$stmt_query = mysqli_query($con,$stmt);
if(mysqli_num_rows($stmt_query)==0)
{
	echo "<script>alert('Incorrect password');</script>";
}
else
{
	$stmt = "SELECT * FROM `rooms` WHERE roomno ='$room'";
	$stmt_query = mysqli_query($con,$stmt);
	if(mysqli_num_rows($stmt_query)==0)
	{
	echo "<script>alert('Room cannot be found');</script>";
	}
	else
	{
	$stmt = "DELETE FROM `rooms` WHERE roomno ='$room'";
	$stmt_query = mysqli_query($con,$stmt);	
	echo "<script>window.location.href='adminlogin.php';alert('Room deleted successfully');</script>";
	}	
}
}
else if(isset($_REQUEST['addserve']))
{
	$id=$_REQUEST['userve'];
	$room=$_REQUEST['rserve'];
	$dur=$_REQUEST['dserve'];
	$date=$_REQUEST['date'];
	$adpass=$_REQUEST['adpass4'];
	
	$stmt = "SELECT * FROM `admin` WHERE password='$adpass'";
	$stmt_query = mysqli_query($con,$stmt);
if(mysqli_num_rows($stmt_query)==0)
{
	echo "<script>alert('Incorrect password');</script>";
}
else
{
	$stmt = "SELECT * FROM `customer` WHERE ID='$id'";
	$stmt_query = mysqli_query($con,$stmt);
	if(mysqli_num_rows($stmt_query)==0)
	{
		echo "<script>alert('User ID not found');</script>";
	}
	else
	{
		$stmt = "SELECT * FROM `rooms` WHERE roomno='$room'";
		$stmt_query = mysqli_query($con,$stmt);
		if(mysqli_num_rows($stmt_query)==0)
		{
			echo "<script>alert('Room not found');</script>";
		}
		else
		{
			$stmt = "SELECT * FROM `reservation` WHERE roomno='$room' AND date='$date'";
			$stmt_query = mysqli_query($con,$stmt);
			if(mysqli_num_rows($stmt_query)==0)
			{
				$stmt="INSERT INTO `reservation`(`userID`, `roomno`, `date`, `duration`) VALUES ('$id','$room','$date','$dur')";
				$stmt_query = mysqli_query($con,$stmt);
				echo "<script>window.location.href='adminlogin.php';alert('Room reserve successfully');</script>";
			}
			else
			{
				echo "<script>alert('Room already Taken');</script>";
			}
		}
	}
}
}
else if(isset($_REQUEST['delserve']))
{
	
	$room=$_REQUEST['rserve2'];
	$date=$_REQUEST['date2'];
	$adpass=$_REQUEST['adpass5'];

	$stmt = "SELECT * FROM `admin` WHERE password='$adpass'";
	$stmt_query = mysqli_query($con,$stmt);
if(mysqli_num_rows($stmt_query)==0)
{
	echo "<script>alert('Incorrect password');</script>";
}
else
{
	$stmt = "SELECT * FROM `reservation` WHERE roomno='$room' AND date='$date'";
	$stmt_query = mysqli_query($con,$stmt);
	if(mysqli_num_rows($stmt_query)==0)
		{
			echo "<script>alert('Reservation cannot be found');</script>";
		}
	else
	{
		$stmt = "DELETE FROM `reservation` WHERE roomno='$room' AND date='$date'";
		$stmt_query = mysqli_query($con,$stmt);
		echo "<script>window.location.href='adminLogin.php';alert('Reservation Deleted successfully');</script>";
	}
}
}
else if(isset($_REQUEST['changeuser']))
{
	$user=$_REQUEST['useradmin'];
	$pass=$_REQUEST['adminpass'];

	$stmt = "SELECT * FROM `admin` WHERE password='$pass'";
	$stmt_query = mysqli_query($con,$stmt);
	if(mysqli_num_rows($stmt_query)==0)
	{
	echo "<script>alert('Incorrect password');</script>";
	}
	else
	{
		$stmt = "SELECT * FROM `customer` WHERE username='$user'";
		$stmt_query = mysqli_query($con,$stmt);
		if(mysqli_num_rows($stmt_query)==0)
		{
			$stmt = "UPDATE `admin` SET `username`='$user'";
			$stmt_query = mysqli_query($con,$stmt);
			echo "<script>window.location.href='adminlogin.php';alert('Username change successfully');</script>";
		}
		else
		{
			echo "<script>alert('Username already taken');</script>";
		}
	}
}
else if(isset($_REQUEST['changepass']))
{
	$newpass=$_REQUEST['newpass'];
	$pass=$_REQUEST['adminpass2'];

	$stmt = "SELECT * FROM `admin` WHERE password='$pass'";
	$stmt_query = mysqli_query($con,$stmt);
	if(mysqli_num_rows($stmt_query)==0)
	{
	echo "<script>alert('Incorrect password');</script>";
	}
	else
	{
	$stmt = "UPDATE `admin` SET `password`='$newpass'";
	$stmt_query = mysqli_query($con,$stmt);
	echo "<script>window.location.href='adminlogin.php';alert('Password change successfully');</script>";	
	}

}
else if(isset($_REQUEST['leave']))
{
session_unset();
session_destroy();
echo "<script>window.location='../Login Page/login.php';alert('Logout Successfully');</script>";
}
mysqli_close($con);
?>