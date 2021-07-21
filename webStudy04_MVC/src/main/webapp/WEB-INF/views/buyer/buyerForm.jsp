<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/resources/css/detail.css">
<jsp:include page="/includee/preScript.jsp" />
<style>
table th {
	background: #F3C8E8;
}

td {
	border-color: #F3C8E8;
}
</style>
</head>
<body>

	<c:if test="${not empty message }">
		<script type="text/javascript">
			alert("${message}");
		</script>
	</c:if>

<form id="buyerForm" method="post">
		<table>
		<c:if test="${req eq 'UPDATE' }"> 
			<tr>
				<th>거래처 코드*</th>
				<td><input type="text" name="buyerId" readonly value="${buyer.buyerId}" />
				<label id="buyerId-error" class="error" for="buyerId">${errors["buyerId"]}</label></td>
			</tr>
		</c:if>  
			<tr>
				<th>거래처 명*</th>
				<td><input type="text" name="buyerName"
					value="${buyer.buyerName}" />
				<label id="buyerName-error" class="error" for="buyerName">${errors["buyerName"]}</label></td>
			</tr>
	<c:choose>
		<c:when test="${req eq 'INSERT'}">
			<tr>
				<th>상품 분류 코드*</th>
				<td>
					<select name="buyerLgu">
					<c:if test="${not empty lprodList }">
						<option value>상품분류전체</option>
						<c:forEach items="${lprodList }" var="lprod">
							<option value='${lprod["LPROD_GU"] }'>${lprod["LPROD_NM"] }</option>
						</c:forEach>
					</c:if> 
					</select>  
					
				<label id="buyerLgu-error" class="error" for="buyerLgu">${errors["buyerLgu"]}</label></td>
			</tr>
		</c:when>
		<c:otherwise> 
			<tr>
				<th>상품 분류 코드</th>
				<td><input type="text" name="buyerLgu" readonly
					value="${buyer.buyerLgu}" />
				<label id="buyerLgu-error" class="error" for="buyerLgu">${errors["buyerLgu"]}</label></td>
			</tr>
		</c:otherwise>
	</c:choose>
			<tr>
				<th>은행</th>
				<td><input type="text" name="buyerBank"
					value="${buyer.buyerBank}" />
				<label id="buyerBank-error" class="error" for="buyerBank">${errors["buyerBank"]}</label></td>
			</tr>
			<tr>
				<th>계좌 번호</th>
				<td><input type="text" name="buyerBankno"
					value="${buyer.buyerBankno}" />
				<label id="buyerBankno-error" class="error" for="buyerBankno">${errors["buyerBankno"]}</label></td>
			</tr>
			<tr>
				<th>예금주</th>
				<td><input type="text" name="buyerBankname"
					value="${buyer.buyerBankname}" />
				<label id="buyerBankname-error" class="error" for="buyerBankname">${errors["buyerBankname"]}</label></td>
			</tr>
			<tr>
				<th>우편 번호</th>
				<td><input type="text" name="buyerZip"
					value="${buyer.buyerZip}" />
				<label id="buyerZip-error" class="error" for="buyerZip">${errors["buyerZip"]}</label></td>
			</tr>
			<tr>
				<th>주소1</th>
				<td><input type="text" name="buyerAdd1"
					value="${buyer.buyerAdd1}" />
				<label id="buyerAdd1-error" class="error" for="buyerAdd1">${errors["buyerAdd1"]}</label></td>
			</tr>
			<tr>
				<th>주소2</th>
				<td><input type="text" name="buyerAdd2"
					value="${buyer.buyerAdd2}" />
				<label id="buyerAdd2-error" class="error" for="buyerAdd2">${errors["buyerAdd2"]}</label></td>
			</tr>
			<tr>
				<th>전화 번호*</th>
				<td><input type="text" name="buyerComtel"
					value="${buyer.buyerComtel}" />
				<label id="buyerComtel-error" class="error" for="buyerComtel">${errors["buyerComtel"]}</label></td>
			</tr>
			<tr>
				<th>FAX 번호*</th>
				<td><input type="text" name="buyerFax"
					value="${buyer.buyerFax}" />
				<label id="buyerFax-error" class="error" for="buyerFax">${errors["buyerFax"]}</label></td>
			</tr>
			<tr>
				<th>담당자 메일 주소*</th>
				<td><input type="text" name="buyerMail"
					value="${buyer.buyerMail}" />
				<label id="buyerMail-error" class="error" for="buyerMail">${errors["buyerMail"]}</label></td>
			</tr>
			<tr>
				<th>담당자 명</th>
				<td><input type="text" name="buyerCharger"
					value="${buyer.buyerCharger}" />
				<label id="buyerCharger-error" class="error" for="buyerCharger">${errors["buyerCharger"]}</label></td>
			</tr>
			<tr>
				<th>담당자 연락처</th>
				<td><input type="text" name="buyerTelext"
					value="${buyer.buyerTelext}" />
				<label id="buyerTelext-error" class="error" for="buyerTelext">${errors["buyerTelext"]}</label></td>
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
				<input type="reset" value="취소"> 
				<input type="button" value="목록으로" class="controlBtn" data-gopage="${pageContext.request.contextPath }/buyer/buyerList.do">
			</th>
			
		</tr> 
		</table> 
	</form>

<jsp:include page="/includee/footer.jsp" /> 
</body>
</html>