<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<jsp:include page="/includee/preScript.jsp"/>
<style>
	body{
		padding-top : 30px;
		padding-left: 30px;
		font-family: 'Noto Sans KR', sans-serif;
	}
	table{
		border-collapse: collapse;
		text-align: center;
	}
	th{
		background : #F3C8E8;
	} 
	th,td{
		border : 2px solid #F3C8E8;
		padding : 10px; 
	} 
	tbody tr:hover{
		background: #F6E7F2;
	}   
	#searchForm input{
		border : none;
	}
	#buyerInsert{
		float : right;
	}
	#searchUI{
		float: left;
	}
</style>
</head>
<body>
	<h3>πκ±°λμ² λͺ©λ‘π</h3>	
	<br><br>
	<table>
		<thead>
			<tr>
				<td colspan="7">
					<div id="searchUI">
						μνλΆλ₯ : 
						<select name="buyerLgu">
							<option value>----μ μ²΄----</option>
							<c:if test="${not empty lprodList }">
							<c:forEach items="${lprodList }" var="lprod">
								<option value='${lprod["LPROD_GU"] }'>${lprod["LPROD_NM"] }</option>
							</c:forEach>
							</c:if> 
						</select>
						κ±°λμ²:
						<select name="buyerId">
							<option value>----μ μ²΄----</option> 
							<c:if test="${not empty buyerList }">
							<c:forEach items="${buyerList }" var="buyer">
								<option value='${buyer.buyerId }' class="${buyer.buyerLgu }">${buyer.buyerName }</option>
							</c:forEach>  
							</c:if> 
						</select> 
						<input type="button" value='κ²μ' id="searchBtn">   
					</div> 
					<input type="button" value="κ±°λμ²λ±λ‘" id="buyerInsert">
				</td>
			</tr>
			<tr>
				<th>κ±°λμ²λͺ</th>
				<th>μνλΆλ₯</th>
				<th>μ νλ²νΈ</th>
				<th>ν©μ€λ²νΈ</th>
				<th>λ΄λΉμ λ©μΌ</th>
				<th>λ΄λΉμλͺ</th>
				<th>λ΄λΉμ μ°λ½μ²</th>
			</tr> 
		</thead>
		<tbody>
		
		</tbody>
		<tfoot>
			<tr>    
				<th colspan="7" id="pagingArea"></th>
			</tr> 
		</tfoot> 
	</table> 
<br><br> 
<form id='searchForm'>
	<input type="text" name="buyerLgu" > 
	<input type="text" name="buyerId" >
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
  
	
	 
	let buyerTag = $("select[name='buyerId']").val("${pagingVO.detailSearch.buyerId}")
	$("select[name='buyerLgu']").othersSelect({
		buyerTag:buyerTag      
	}).val("${pagingVO.detailSearch.buyerLgu}").change();
	
 
	$("select[name='buyerLgu']").on("change", function(){
		let lgu = $(this).val();
		$("select[name='buyerBuyer']").find("option").hide();  
		$("select[name='buyerBuyer']").find("option:first").show();
		$("select[name='buyerBuyer']").find("option."+ lgu).show();
	});

	let tbody = $('table tbody').on('click', 'tr', function(){
		let buyer = $(this).data("buyer");
		if(!buyer) return false; 
		let buyerId = buyer.buyerId; 
		location.href = "${pageContext.request.contextPath }/buyer/buyerView.do?what="+buyerId;
	});
	
	let pagingArea = $('#pagingArea')
	

	function makeTrTag(buyer){
		return $("<tr>").append(
			$("<td>").html(buyer.buyerName),		
			$("<td>").html(buyer.lprodNm),		  
			$("<td>").html(buyer.buyerComtel),		 
			$("<td>").html(buyer.buyerFax),		
			$("<td>").html(buyer.buyerMail),	
			$("<td>").html(buyer.buyerCharger),		
			$("<td>").html(buyer.buyerTelext)		
		).data("buyer", buyer);    
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
					let buyerList = pagingVO.dataList;
					let trTags = []; 
					if(buyerList.length > 0){
						$.each(buyerList, function(i,buyer){
							trTags.push(makeTrTag(buyer));
			 			}) 
						pagingArea.html(pagingVO.pagingHTML);
					}else{
						trTags.push(
							$("<tr>").html(
								$("<td>").attr("colspan", "7").html("μ‘°νλ μνμ΄ μμ΅λλ€.")	
							)		
						);
					}  
					tbody.append(trTags);
				}//success end 
		}).submit();
	 
		
	$("#buyerInsert").on('click',function(){
		location.href = "${pageContext.request.contextPath }/buyer/buyerInsert.do";
	})
	 
});	
</script>
<jsp:include page="/includee/footer.jsp"></jsp:include>
</body>
</html>