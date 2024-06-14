$(document).ready(function() {
	$("body").fadeIn(1000);
		
		$("input").mouseenter(function(){
			$(this).css({backgroundColor: "rgb(157, 157, 157)"},100);
		});
		$("input").mouseleave(function(){
			$(this).css({backgroundColor: "whitesmoke"},100);
		});
		$("button").mouseenter(function(){
			$(this).css({backgroundColor: "rgb(157, 157, 157)"},100);
		});
		$("button").mouseleave(function(){
			$(this).css({backgroundColor: "whitesmoke"},100);
		});
			$("#form").submit(function() {
				var name = $('#sname').val();
				if(name==null){
					alert("Seleck a sneaker");	
				return false;
				
			}
			else{
				if (confirm("Are you sure you want to delete this record?")) {
					return true;
				} 
				else {
					return false;
				}
			}
				
			});
			$('#form1').submit(function(event) {
			var srp = $('#srp').val();
			var cprice = $('#cprice').val();
			var name = $('#sname').val();
			if(isNaN(srp)||isNaN(cprice)){
				event.preventDefault();
				alert("Suggested Retail Price and Current Price must be a number");
			}
			else if(name==null){
				event.preventDefault();
				alert("Seleck a sneaker");
			}
		});
});