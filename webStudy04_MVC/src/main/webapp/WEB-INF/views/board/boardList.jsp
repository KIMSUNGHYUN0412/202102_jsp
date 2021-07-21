<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<jsp:include page="/includee/preScript.jsp"/>
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/list.css">
<style>
	td:nth-child(3){ 
		text-align: left;
	}
</style>
</head>
<body>
<c:if test="${not empty message }">
	<script> 
		alert("${message}");
	</script> 
<c:remove var="message" scope="session"/>
</c:if>    
<h4>프리보올드~~~~~~~~</h4>
<input type="button" value="글쓰기"  id="insert">
<div id="searchUI">
	<select name="searchType">
		<option value>전체</option>
		<option value="title">제목</option>
		<option value="writer">작성자</option>
		<option value="content">내용</option>
	</select> 
	<input type="text" name="searchWord">   
	<input type="date" name="startDate" >~ <input type="date" name="endDate" >  
	<button type="button" id="searchBtn">🔍</button> 
</div>
<table> 
	<thead> 
		<tr>
			<th>일련번호</th>
			<th>번호</th>
			<th>제목</th>
			<th>작성자</th>
			<th>작성일</th> 
			<th>조회수</th>
			<th>추천수</th>
			<th>신고수</th>
		</tr>
	</thead>
	<tbody>
		<c:set var="boardList" value="${pagingVO.dataList }"/>
		<c:if test="${empty boardList }">
			<tr>
				<td colspan="8">
					<h5>안타깝지만서도 게시글이 없서유</h5>
				</td> 
			</tr>
		</c:if>
		<c:if test="${not empty boardList }">
			<c:forEach items="${boardList }" var="board" >
				<tr id="${board.boNo}"> 
					<td>${board.rnum }</td>
					<td>${board.boNo }</td>
					<td>${board.boTitle }</td> 
					<td>${board.boWriter }</td>
					<td>${board.boDate }</td>
		 			<td>${board.boHit }</td> 
		 			<td>${board.boRec }</td> 
		 			<td>${board.boRep }</td> 
				</tr>
			</c:forEach>
		</c:if>
	</tbody>  
	<tfoot>
		<tr>
			<th colspan="8" id="pagingArea">  
				<h5>${pagingVO.pagingHTML }</h5>
			</th> 
		</tr>  
	</tfoot>
</table>
<form id="searchForm">
	<input type="text" name = "searchType">
	<input type="text" name = "searchWord">
	<input type="text" name="startDate" >~ <input type="text" name="endDate" >  
	<input type="text" name = "page">   
</form> 
<%-- <script src="${pageContext.request.contextPath }/resources/js/paging.js"></script>  --%>
<script type="text/javascript">  
	$("#pagingArea").on("click", ".pageLink", function(event){
		event.preventDefault(); 
		let page = $(this).data("page");
		searchForm.find("[name='page']").val(page);
		searchForm.submit();
		return false;
	}).css("cursor", "pointer");
	
	let searchForm = $("#searchForm");
	let searchUI = $("#searchUI").on("click", "#searchBtn", function(){
		let inputs = searchUI.find(":input[name]");
		$(inputs).each(function(idx, input){
			let name = this.name; 
			let value = $(this).val();
			searchForm.find("[name='"+name+"']").val(value);
			 
		}); 
		searchForm.submit(); 
	});
	
	$("tbody").on("click", "tr[id]", function(){
		let boNo = this.id;   
		location.href = "${pageContext.request.contextPath}/board/boardView.do?what="+boNo;
	}); 
	
	$("#insert").on("click", function(){ 
		location.href="${pageContext.request.contextPath}/board/boardInsert.do";
	})
	
</script>
<jsp:include page="/includee/footer.jsp"/>
</body>
</html>