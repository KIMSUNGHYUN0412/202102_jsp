/**
 * 
 */
$(".controlBtn").on("click",function(){
	let gopage = $(this).data("gopage");
	if(gopage){
		location.href = gopage;
	}
});