/**
 * 
 */

$.fn.othersSelect=function(param){ 
	let buyerLgu = this; 
	let buyerId = param.buyerTag; 
	buyerLgu.on("change", function(){
		let lgu = $(this).val(); 
		if(lgu){
			buyerId.find("option").hide();
			buyerId.find("option."+ lgu).show();
			buyerId.find("option:first").show().prop("selected", true);
		}else{ 
			buyerId.find("option").show(); 
			
		} 
	}) 
	return this;
}