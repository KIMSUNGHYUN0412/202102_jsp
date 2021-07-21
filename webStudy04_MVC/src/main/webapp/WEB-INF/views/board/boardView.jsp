<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<jsp:include page="/includee/preScript.jsp"/>
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/detail.css">
<style type="text/css">
td{
	text-align: left; 
}
.right{
	float: right;
}
input{
	width : 40px;
	height : 25px;
	font-size: small;
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
<table> 
		<tr>
			<td>
			${board.boNo }
			</td>
			<td>
				${board.boTitle }
			</td>
			<td>
				<h6>조회수 ${board.boHit } | 추천수 ${board.boRec } | 신고수 ${board.boRep }</h6>
				<input type="button" value="💜" name="countType" data-counttype="RECOMMEND">
				<input type="button" value="🚨" name="countType"  data-counttype="REPORT">
			</td>
		</tr>    
		<tr>  
			<td colspan="3">  
			 <h6>작성자 | </h6>  ${board.boWriter}
			 <h6 class="right">${board.boMail }</h6>
			</td> 
		</tr>
		<tr>
			<td colspan="3">
			<h6 class="right">작성일 | ${board.boDate }</h6><br>
				${board.boContent }
			</td>
		</tr> 
		<tr>
			<td colspan="3">
				<c:set var="attList" value="${board.attatchList }"></c:set>
				<c:choose>
					<c:when test="${not empty attList }">
						<c:forEach items="${attList }" var="att">
							<c:url value="/board/download.do" var="downloadURL">
								<c:param name="what" value="${att.attNo }"/>
							</c:url>
							<a href="${downloadURL }" >📥${att.attFilename}</a>
							<h6>${att.attFancysize }</h6>
							<br>  
						</c:forEach> 
					</c:when> 
					<c:otherwise>
						<h6>첨부파일 없음</h6>
					</c:otherwise>
				</c:choose> 	
			</td>
		</tr> 
		<tr>
			<td colspan="3">
			<c:url value="/board/boardInsert.do" var="insertURL"> 
				<c:param name="boParent" value="${board.boNo }">
				</c:param>
			</c:url> 
			<input type="button" value="답글쓰기" class="controlBtn gobtn"
			data-gopage="${insertURL }"> 
			<c:url value="/board/boardUpdate.do" var="updateURL"> 
				<c:param name="boNo" value="${board.boNo }">
				</c:param>  
			</c:url>  
			<input type="button" value="수정하기" id="update" class="controlBtn gobtn" data-gopage="${updateURL }">
			<input type="button" value="삭제하기" id="delete" class="gobtn">  
			<input type="button" value="목록으로" class="controlBtn gobtn"
				data-gopage="${pageContext.request.contextPath }/board/boardList.do">
			</td>
		</tr>
	</table>
<form id="incrementForm">
	<input type="hidden" name="countType">
	<input type="hidden" name="what" value="${board.boNo }">
</form> 
<form name="deleteForm" action="${pageContext.request.contextPath }/board/boardDelete.do" method="post" >
	<input type="hidden" name="boNo"  value="${board.boNo }" required> 
	<input type="hidden" name="boPass" required>  
</form>  
<script src="${pageContext.request.contextPath }/resources/js/jquery.form.min.js"></script> 
<script type="text/javascript"> 
$(function(){  
	$(document).ajaxError(function(event, xhr, options, error){
		console.log(event);
		console.log(xhr); 
		console.log(options); 
		console.log(error); 
	}).ajaxComplete(function(event, xhr, options){
		incrementForm.find("[name='countType']").val(""); 
	});   
	 
	$("input[name='countType']").on("click", function(){ 
		let countType = $(this).data("counttype");  
// 		console.log(countType);    
		incrementForm.find("input[name="+this.name+"]").val(countType);
// 		console.log($("#incrementForm").find("input[name="+this.name+"]").val());
		incrementForm.submit();  
	}); 
	 
	let incrementForm = $("#incrementForm").ajaxForm({
		type : "post",   
		dataType : "json",  
		success : function(result){ 
			if(result=="OK"){
				location.reload();
			}else{  
				alert("존재하지 않는 게시글입니다.");
			}
		}  
	});  
	
	
	$("#delete").on("click", function(){ 
		pass = prompt("비밀번호를 입력하세요");
		if(pass==null || pass.trim()=="") 
			return false;    
		document.deleteForm.boPass.value = pass;
		document.deleteForm.submit();
	});
});	
</script>
<jsp:include page="/includee/footer.jsp"/>
</body>
</html>