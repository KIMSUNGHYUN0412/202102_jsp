<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<jsp:include page="/includee/preScript.jsp" />
<script src="${pageContext.request.contextPath }/resources/ckeditor/ckeditor.js"></script>
<link rel="stylesheet" 
	href="${pageContext.request.contextPath }/resources/css/detail.css">
</head>
<body>
<c:if test="${not empty message }">
	<script>
		alert("${message}");
	</script>
</c:if>
<form id="boardForm"  method="post" enctype="multipart/form-data">
<c:if test="${req eq 'update'}">
<input type="text" name="boNo" value="${board.boNo }" placeholder="글번호"> 
</c:if> 
<input type="text" name="boParent" value="${board.boParent }" placeholder="답글작성시 상위글번호"> 
	<table>   
 		<tr>  
			<th>제목*</th>
			<td><input type="text" name="boTitle"
				value="${board.boTitle}" /><label id="boTitle-error"
				class="error" for="boTitle">${errors["boTitle"]}</label></td>
		</tr>
		<tr>
			<th>작성자*</th>
			<td><input type="text" name="boWriter"
				value="${board.boWriter}" /><label id="boWriter-error"
				class="error" for="boWriter">${errors["boWriter"]}</label></td>
		</tr>
		<tr>
			<th>IP*</th>
			<td><input type="text" name="boIp" value="${pageContext.request.remoteAddr}" readonly/><label 
				id="boIp-error" class="error" for="boIp">${errors["boIp"]}</label></td>
		</tr> 
		<tr> 
			<th>이메일</th> 
			<td><input type="text" name="boMail" value="${board.boMail}" /><label
				id="boMail-error" class="error" for="boMail">${errors["boMail"]}</label></td>
		</tr>
		<tr>
			<th>비밀번호*</th>
			<td><input type="text" name="boPass" value="" /><label
				id="boPass-error" class="error" for="boPass">${errors["boPass"]}</label></td>
		</tr> 
		<tr>
			<th>내용</th>
			<td> 
<!-- 			<input type="text" name="boContent" -->
<%-- 				value="${board.BoContent}" /> --%>
				<textarea rows="10" cols="50" name="boContent"  id="boContent">
				 ${board.boContent} 
				</textarea> 
				<label id="boContent-error"
				class="error" for="boContent">${errors["boContent"]}</label></td>
		</tr> 
		<tr>
			<th>첨부파일</th>
			<td>
			<c:set var="attList" value="${board.attatchList }"></c:set>
			<c:if test="${not empty attList }">
				<c:forEach items="${attList }" var="att">
					<div class="savedFile">${att.attFilename} 
					<button type="button" class="delete" data-attno="${att.attNo }">X</button>
				 	<br>    
				 	</div>
				</c:forEach>     
				<div id="deleteAtt"></div> 
			</c:if> 
			<div class="addFile">
			<input type="file" name="boFiles"/><button type="button" class="plus">+</button>
			<label id="boFiles-error"   
				class="error" for="boFiles">${errors["boFiles"]}</label>
			</div>
			</td>  
		</tr>   
<!-- 		<tr> -->
<!-- 			<th>부모글번호</th> -->
<!-- 			<td><input type="text" name="boParent" -->
<%-- 				value="${board.boParent}" /><label id="boParent-error" --%>
<%-- 				class="error" for="boParent">${errors["boParent"]}</label></td> --%>
<!-- 		</tr> -->
		<tr> 
			<th colspan="2">
				<c:choose> 
					<c:when test="${req eq 'insert'}">
						<input type="submit" value="등록" id="insert">
					</c:when>
					<c:otherwise>
						<input type="submit" value="저장" id="update">
					</c:otherwise>
				</c:choose> <input type="button" value="목록으로" class="controlBtn"
				data-gopage="${pageContext.request.contextPath }/board/boardList.do">
			</th>
		</tr>
	</table>
</form>
<script> 
CKEDITOR.replace("boContent", {
	filebrowserImageUploadUrl : "${pageContext.request.contextPath}/board/uploadImage.do?type=Images" 
});  
$(function(){
	$("table").on("click", ".plus",function(){
// 		<input type="file" name="boFiles"/><button type="button" class="plus">+</button>
		plusFile = $(".plusFile").last();
		buttons = [];
		 
		buttons.push($("<input>").attr({   
			"type" : "file",
			"name" : "boFiles", 
			"class" : "plusFile" 
		}));   
		 
		buttons.push($("<button>").attr({ 
			"type" : "button", 
			"class" : "plus",  
		}).text("+"));   
		  
		buttons.push($("<button>").attr({ 
			"type" : "button", 
			"class" : "minus",  
		}).text("-"));    
		
		$(".addFile").append($("<div>").attr("class", "addDiv"));
		$(".addDiv").last().append(buttons);
	});  
	
	$("table").on("click", ".minus",function(){
		$(this).parent().remove();
	});
	
	
	
	$("table").on('click', '.delete', function(){
		attNo = $(this).data("attno");
		$("#deleteAtt").append(
				$("<input>").attr(
						{"type" : "number",
						"name" : "delAttNos",
						"value" : attNo}
					)
		).hide();
		$(this).parent().remove();
	});
});
</script>
<jsp:include page="/includee/footer.jsp" />
</body>
</html>