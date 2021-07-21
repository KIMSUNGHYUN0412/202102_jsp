<%@page import="java.util.Map"%>
<%@page import="kr.or.ddit.vo.BuyerVO"%>
<%@page import="kr.or.ddit.vo.ProdVO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/list.css">
<jsp:include page="/includee/preScript.jsp"/> 

</head>
<body>
	<h3>PRODLIST</h3><br>
	<br>
	<table>
	<thead>
		<tr>
			<td colspan="8">
				<div id="searchUI">	 
					분류 : 
					<select name="prodLgu">
					<c:if test="${not empty lprodList }">
						<option value>상품분류전체</option>
						<c:forEach items="${lprodList }" var="lprod">
							<option value='${lprod["LPROD_GU"] }'>${lprod["LPROD_NM"] }</option>
						</c:forEach>
					</c:if> 
					</select>
					거래처 : 
					<select name="prodBuyer">
					<c:if test="${not empty buyerList }">
						<option value>거래처전체</option>
						<c:forEach items="${buyerList }" var="buyer">
							<option value='${buyer.buyerId }' class="${buyer.buyerLgu }">${buyer.buyerName }</option>
						</c:forEach> 
					</c:if> 
					</select>
					상품명 : 
					<input type="text" name="prodName" > 
					<input type="button" value='검색' id="searchBtn">  
					<input type="button" value="상품등록" id="prodInsert">
				</div>
			</td>
		</tr>
		<tr>
			<th>상품 명</th> 
			<th>상품 분류</th>
			<th>거래처</th>
			<th>매입가</th>
			<th>판매가</th>
			<th>재고수량</th> 
			<th>등록일</th>
			<th>개당 마일리지</th>
		</tr>
	</thead>
	<tbody>
	
	</tbody>	
	<tfoot>
		<tr>    
			<th colspan="8" id="pagingArea"></th>
		</tr>
	</tfoot> 
	</table>
<br><br> 
<form id='searchForm'>
	<input type="text" name="prodLgu" > 
	<input type="text" name="prodBuyer" > 
	<input type="text" name="prodName" >
	<input type="text" name="page">
</form> 
 
<script src="${pageContext.request.contextPath }/resources/js/paging.js"></script> 
<script src="${pageContext.request.contextPath }/resources/js/jquery.form.min.js"></script> 
<script src="${pageContext.request.contextPath }/resources/js/prod/prodForm.js"></script>
<script> 
$(function(){ 
	$(document).ajaxError(function(event, xhr, options, error){
		console.log(event);
		console.log(xhr);
		console.log(options);
		console.log(error); 
	}).ajaxComplete(function(event, xhr, options){
		searchForm.find("[name='page']").val("");
// 		searchForm.get(0).reset();
	});   
 
	 
 
// 	$("select[name='prodLgu']").on("change", function(){
// 		let lgu = $(this).val();
// 		$("select[name='prodBuyer']").find("option").hide(); 
// 		$("select[name='prodBuyer']").find("option:first").show();
// 		$("select[name='prodBuyer']").find("option."+ lgu).show();
// 	});
 
	let buyerTag = $("select[name='prodBuyer']").val("${pagingVO.detailSearch.prodBuyer}")
	$("select[name='prodLgu']").othersSelect({
		buyerTag:buyerTag    
	}).val("${pagingVO.detailSearch.prodLgu}").change();

	let tbody = $('table tbody').on('click', 'tr', function(){
		let prod = $(this).data("prod");
		if(!prod) return false; 
		let prodId = prod.prodId; 
		location.href = "${pageContext.request.contextPath }/prod/prodView.do?what="+prodId;
	});
	
	let pagingArea = $('#pagingArea')
	

	function makeTrTag(prod){ 
		return $("<tr>").append(
			$("<td>").html(prod.prodName),		
			$("<td>").html(prod.lprodNm),		
			$("<td>").html(prod.buyer.buyerName),		
			$("<td>").html(prod.prodCost),		
			$("<td>").html(prod.prodSale),	
			$("<td>").html(prod.prodTotalstock),		
			$("<td>").html(prod.prodInsdate),		
			$("<td>").html(prod.prodMileage)		
		).data("prod", prod);   
	}  
	  
	
	let searchForm = $("#searchForm").paging({
		 	pagingArea : "#pagingArea",
		 	pageLink : ".pageLink",
		 	searchUI : "#searchUI",
		 	btnSelector : "#searchBtn",
		 	pageKey : "page",
		 	pageParam : "page"  
		}).ajaxForm({
				dataType : "json",
				success: function(pagingVO){
					tbody.empty();
					pagingArea.empty(); 
					let prodList = pagingVO.dataList;
					let trTags = []; 
					if(prodList.length > 0){
						$.each(prodList, function(i,prod){
							trTags.push(makeTrTag(prod));
			 			}) 
						pagingArea.html(pagingVO.pagingHTML);
					}else{
						trTags.push(
							$("<tr>").html(
								$("<td>").attr("colspan", "8").html("조회된 상품이 없습니다.")	
							)		
						);
					} 
					tbody.append(trTags);
				}//success end 
		}).submit();
	
		
	$("#prodInsert").on('click',function(){
		location.href = "${pageContext.request.contextPath }/prod/prodInsert.do";
	})
	
});	
</script>
<jsp:include page="/includee/footer.jsp"></jsp:include>
</body>
</html>