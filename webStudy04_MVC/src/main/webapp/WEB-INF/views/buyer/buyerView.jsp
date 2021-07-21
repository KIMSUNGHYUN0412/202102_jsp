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
<style>
	table th{
		background: #F3C8E8;
	}  
	td{
		border-color: #F3C8E8;
	}
</style>  
</head>
<body>
	<table>
		<tr>
			<th>거래처 코드</th>
			<td>${buyer.buyerId}</td>
		</tr>
		<tr>
			<th>거래처 명</th>
			<td>${buyer.buyerName}</td>
		</tr>
		<tr>
			<th>상품 분류 코드</th>
			<td>${buyer.buyerLgu}</td>
		</tr> 
		<tr>
			<th>상품 분류 명</th>
			<td>${buyer.lprodname}</td> 
		</tr> 
		<tr> 
			<th>은행</th>
			<td>${buyer.buyerBank}</td>
		</tr>
		<tr>
			<th>계좌 번호</th>
			<td>${buyer.buyerBankno}</td>
		</tr>
		<tr>
			<th>예금주</th>
			<td>${buyer.buyerBankname}</td>
		</tr>
		<tr>
			<th>우편 번호</th>
			<td>${buyer.buyerZip}</td>
		</tr>
		<tr>
			<th>주소1</th>
			<td>${buyer.buyerAdd1}</td>
		</tr>
		<tr>
			<th>주소2</th>
			<td>${buyer.buyerAdd2}</td>
		</tr>
		<tr>
			<th>전화 번호</th>
			<td>${buyer.buyerComtel}</td>
		</tr>
		<tr>
			<th>FAX 번호</th>
			<td>${buyer.buyerFax}</td>
		</tr>
		<tr>
			<th>담당자 메일 주소</th>
			<td>${buyer.buyerMail}</td>
		</tr>
		<tr>
			<th>담당자 명</th>
			<td>${buyer.buyerCharger}</td>
		</tr>
		<tr>
			<th>담당자 연락처</th>
			<td>${buyer.buyerTelext}</td>
		</tr>
		<tr>
			<td colspan='2'>
			<c:url value="/buyer/buyerUpdate.do" var="updateURL" >
				<c:param name="what" value="${buyer.buyerId }"/>
			</c:url> 
				<a href="${updateURL }">거래처 수정</a>
			</td>
		</tr> 
		<tr>
			<th>거래상품 정보</th>
			<td> 
				<table>
					<tr>
						<th>상품코드</th>
						<th>상품명</th>
						<th>매입가</th>
					</tr> 
				<c:set var="prodList" value="${buyer.prodList }"></c:set>
				<c:choose>
					<c:when test="${not empty prodList }">
						<c:forEach items="${prodList }" var="prod">
							<tr>
								<td>${prod.prodId}</td>
								<td>${prod.prodName}</td>
								<td>${prod.prodCost}</td>
							</tr>
						</c:forEach>
					</c:when> 
					<c:otherwise>
						<tr> 
							<td colspan="3">거래상품 정보가 존재하지 않습니다.</td>
						</tr>
					</c:otherwise>
				</c:choose>  
				</table>
			</td> 
		</tr>
	</table>
	
<jsp:include page="/includee/footer.jsp"></jsp:include>
</body>
</html>