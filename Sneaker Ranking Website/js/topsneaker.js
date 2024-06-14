$(document).ready(function()
{
	$("body").fadeIn(1000);
		$("#searchbar").keyup(function(){
				if($("#searchbar").val()===""||$("#searchbar").val().trim()===""){
					$("#txtHint").html("No suggestion");
				}
			else{
				var text = $("#searchbar").val();
				$.get("showhint.php?q="+text, function(response){
					$("#txtHint").html(response);
					$("#txtHint").on("click","span",function(){
						var text = $(this).text();
						$.get("searchProcess2.php?q="+text, function(response){
						$("#result").html(response);
						$('html, body').animate({
						scrollTop: $('#result').offset().top
							}, 1);
						});
					});
				});
			}
		});
		$("button,input").mouseenter(function(){

			$(this).css({backgroundColor: "rgb(157, 157, 157)"},100);
			
		});
		$("button,input").mouseleave(function(){

			$(this).css({backgroundColor: " whitesmoke"},100);
		});
});