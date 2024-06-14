$(document).ready(function()
{
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
	$("#date").datepicker({
			showOtherMonths: true,
			selectOtherMonths: true,
			changeMonth: true,
			changeYear: true,
			dateFormat: 'yy/mm/dd'	
		});
	$("#date").click(function() {
		$("#date").datepicker('show');
	});
	
	$("#namebar").keyup(function(){
				if($("#namebar").val()===""||$("#namebar").val().trim()===""){
					$("#txtHint").html("");
				}
			else{
				var text = $("#namebar").val();
				$.get("addcheck.php?q="+text, function(response){
					$("#txtHint").html(response);
				});
			}
		});
		$('#form1').submit(function(event) {
			var srp = $('#srp').val();
			var cprice = $('#cprice').val();
			if(isNaN(srp)||isNaN(cprice)){
				event.preventDefault();
				alert("Suggested Retail Price and Current Price must be a number");
			}
		});

});