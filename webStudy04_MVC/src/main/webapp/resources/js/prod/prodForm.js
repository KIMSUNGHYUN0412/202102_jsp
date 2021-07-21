/**
 * 
 */

//$(function() {
//	$("select[name='prodLgu']").on(
//		"change",
//		function() {
//			let lgu = $(this).val();
//			$("select[name='prodBuyer']").find("option").hide();
//			$("select[name='prodBuyer']").find("option." + lgu)
//					.show();
//			$("select[name='prodBuyer']").find("option:first")
//					.show().prop("selected", true);
//	});
//
//	 
//});

$.fn.othersSelect=function(param){ 
	let prodLgu = this; 
	let prodBuyer = param.buyerTag; 
	prodLgu.on("change", function(){
		let lgu = $(this).val(); 
		if(lgu){
			prodBuyer.find("option").hide();
			prodBuyer.find("option."+ lgu).show();
			prodBuyer.find("option:first").show().prop("selected", true);
		}else{
			prodBuyer.find("option").show(); 
			
		} 
	}) 
	return this;
}

