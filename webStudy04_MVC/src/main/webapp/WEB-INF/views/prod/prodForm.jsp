<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/detail.css">
<jsp:include page="/includee/preScript.jsp" />
</head>
<body>
 
<c:if test="${not empty message }">
	<script type="text/javascript"> 
		alert("${message}");  
	</script>  
</c:if>

	<form id="prodForm" method="post" enctype="multipart/form-data">
		<table>
			<c:if test="${req eq 'UPDATE' }"> 
				<tr>
					<th>상품 코드*</th>  
					<td><input type="text" name="prodId" readonly
						value="${prod.prodId}" />
					<label id="prodId-error" class="error" for="prodId">${errors["prodId"]}</label></td>
				</tr>
			</c:if> 
			<tr>
				<th>상품 명*</th>
				<td><input type="text" name="prodName" 
					value="${prod.prodName}" />
				<label id="prodName-error" class="error" for="prodName">${errors["prodName"]}</label></td>
			</tr>
		<c:choose>
			<c:when test="${req eq 'INSERT'}">
			<tr>
				<th>상품 분류 코드*</th>
				<td>
					<select name="prodLgu">
					<c:if test="${not empty lprodList }">
						<option value>상품분류전체</option>
						<c:forEach items="${lprodList }" var="lprod">
							<option value='${lprod["LPROD_GU"] }'>${lprod["LPROD_NM"] }</option>
						</c:forEach>
					</c:if> 
					</select> 
					
				<label id="prodLgu-error" class="error" for="prodLgu">${errors["prodLgu"]}</label></td>
			</tr>
			<tr> 
				<th>거래처 코드*</th>
				<td>
					<select name="prodBuyer">
					<c:if test="${not empty buyerList }">
						<option value>거래처전체</option>
						<c:forEach items="${buyerList }" var="buyer">
							<option value='${buyer.buyerId }' class="${buyer.buyerLgu }">${buyer.buyerName }</option>
						</c:forEach> 
					</c:if> 
					</select>
					 
				<label id="prodBuyer-error" class="error" for="prodBuyer">${errors["prodBuyer"]}</label></td>
			</tr>
		</c:when>
		<c:otherwise> 
			<tr>
				<th>상품 분류 코드</th>
				<td><input type="text" name="prodLgu" readonly
					value="${prod.prodLgu}" />
				<label id="prodLgu-error" class="error" for="prodLgu">${errors["prodLgu"]}</label></td>
			</tr>
			<tr>  
				<th>거래처 코드</th>
				<td><input type="text" name="prodBuyer" readonly
					value="${prod.prodBuyer}" />
				<label id="prodBuyer-error" class="error" for="prodBuyer">${errors["prodBuyer"]}</label></td>
			</tr> 
		</c:otherwise>
	</c:choose>
			<tr>
				<th>매입가*</th>
				<td><input type="number" name="prodCost" 
					value="${prod.prodCost}" />
				<label id="prodCost-error" class="error" for="prodCost">${errors["prodCost"]}</label></td>
			</tr>
			<tr>
				<th>소비자가*</th>
				<td><input type="number" name="prodPrice" 
					value="${prod.prodPrice}" />
				<label id="prodPrice-error" class="error" for="prodPrice">${errors["prodPrice"]}</label></td>
			</tr>
			<tr>
				<th>판매가*</th>
				<td><input type="number" name="prodSale" 
					value="${prod.prodSale}" />
				<label id="prodSale-error" class="error" for="prodSale">${errors["prodSale"]}</label></td>
			</tr>
			<tr>
				<th>상품 개략 설명*</th>
				<td><input type="text" name="prodOutline" 
					value="${prod.prodOutline}" />
				<label id="prodOutline-error" class="error" for="prodOutline">${errors["prodOutline"]}</label></td>
			</tr>
			<tr>
				<th>상품 상세 설명</th>
				<td><input type="text" name="prodDetail"
					value="${prod.prodDetail}" />
				<label id="prodDetail-error" class="error" for="prodDetail">${errors["prodDetail"]}</label></td>
			</tr>
			<tr>
				<th>이미지(소)*</th>
				<td> 
				<input type="file" name="prodImage" accept="image/*"> 
				<label id="prodImage-error" class="error" for="prodImage">${errors["prodImage"]}</label></td>
			</tr>
			<tr>
				<th>재고수량*</th>
				<td><input type="number" name="prodTotalstock" 
					value="${prod.prodTotalstock}" />
				<label id="prodTotalstock-error" class="error" for="prodTotalstock">${errors["prodTotalstock"]}</label></td>
			</tr>
			<tr>
				<th>신규일자(등록일)</th>
				<td><input type="date" name="prodInsdate"  readonly
					value="${prod.prodInsdate}" />  
				<label id="prodInsdate-error" class="error" for="prodInsdate">${errors["prodInsdate"]}</label></td>
			</tr>
			<tr> 
				<th>안전 재고수량*</th>
				<td><input type="number" name="prodProperstock"  
					value="${prod.prodProperstock}" />
				<label id="prodProperstock-error" class="error"
					for="prodProperstock">${errors["prodProperstock"]}</label></td>
			</tr>
			<tr> 
				<th>크기</th>
				<td><input type="text" name="prodSize" value="${prod.prodSize}" />
				<label id="prodSize-error" class="error" for="prodSize">${errors["prodSize"]}</label></td>
			</tr>
			<tr>
				<th>색상</th>
				<td><input type="text" name="prodColor"
					value="${prod.prodColor}" />
				<label id="prodColor-error" class="error" for="prodColor">${errors["prodColor"]}</label></td>
			</tr>
			<tr>
				<th>배달 특기 사항</th>
				<td><input type="text" name="prodDelivery"
					value="${prod.prodDelivery}" />
				<label id="prodDelivery-error" class="error" for="prodDelivery">${errors["prodDelivery"]}</label></td>
			</tr>
			<tr>
				<th>단위(수량)</th>
				<td><input type="text" name="prodUnit" value="${prod.prodUnit}" />
				<label id="prodUnit-error" class="error" for="prodUnit">${errors["prodUnit"]}</label></td>
			</tr>
			<tr>
				<th>총 입고 수량</th>
				<td><input type="number" name="prodQtyin" 
					value="${prod.prodQtyin}" />
				<label id="prodQtyin-error" class="error" for="prodQtyin">${errors["prodQtyin"]}</label></td>
			</tr>
			<tr>
				<th>총 판매 수량</th> 
				<td><input type="number" name="prodQtysale"
					value="${prod.prodQtysale}" />
				<label id="prodQtysale-error" class="error" for="prodQtysale">${errors["prodQtysale"]}</label></td>
			</tr>
			<tr>
				<th>개당 마일리지 점수</th>
				<td><input type="number" name="prodMileage"
					value="${prod.prodMileage}" />
				<label id="prodMileage-error" class="error" for="prodMileage">${errors["prodMileage"]}</label></td>
			</tr>
			<tr>
				<th colspan="2"> 
		<c:choose>
			<c:when test="${req eq 'INSERT'}">
				<input type="submit" value="등록" id="insert">
			</c:when> 
			<c:otherwise>
				<input type="submit" value="저장" id="update">
			</c:otherwise>
		</c:choose>
						<input type="button" value="목록으로" class="controlBtn" data-gopage="${pageContext.request.contextPath }/prod/prodList.do">
					</th>
					
				</tr>
		</table> 
	</form> 
<script src="${pageContext.request.contextPath }/resources/js/prod/prodForm.js"></script>
<script type="text/javascript"> 
	let buyerTag = $("select[name='prodBuyer']").val("${prod.prodBuyer}");
	$("select[name='prodLgu']").othersSelect({
		buyerTag:buyerTag 
	}).val("${prod.prodLgu}").change();  
</script>
	<jsp:include page="/includee/footer.jsp" /> 
</body>
</html>