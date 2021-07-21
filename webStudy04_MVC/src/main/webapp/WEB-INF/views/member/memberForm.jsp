<%@page import="java.util.Objects"%>
<%@page import="java.util.HashMap"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="java.util.Map"%>
<%@page import="kr.or.ddit.vo.MemberVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<jsp:include page="/includee/preScript.jsp" />
<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/jquery-validation@1.19.3/dist/jquery.validate.min.js"></script>
<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/jquery-validation@1.19.3/dist/additional-methods.min.js"></script>
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.25/css/jquery.dataTables.css">
<script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.25/js/jquery.dataTables.js"></script>

<style> 
@import
	url('https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@300&display=swap');

body {
	padding-top: 30px;
	padding-left: 30px;
	font-family: 'Noto Sans KR', sans-serif;
}

span {
	font-weight: bold;
	font-size: 1.1em;
	color: purple;
}

table {
	border-collapse: collapse;
}

td {
	border: 2px solid lightblue;
	width : 400px;
}

th {
	background: lightblue;
	text-align: center;
	width : 200px;
	padding: 3px;
}

 
input{
	width : 350px;
}

label {
	color: red;
}
</style>
</head>
<body>
	
<c:if test="${not empty message }">
	<script type="text/javascript"> 
		alert("${message}");  
	</script>  
</c:if>

	<form id="memberForm" method="post" enctype="multipart/form-data">
		<table>
			<tr>
				<th>회원 ID</th>
				<td><input type="text" name="memId"  
					value="${member.memId}" />
					<br>
				<label id="memId-error" class="error" for="memId">${errors['memId'] }</label></td>
			</tr>
			<tr>
				<th>비밀 번호</th> 
				<td><input type="text" name="memPass" 
					<%-- value="<%=member.getMemPass()%>" --%> /><br>
				<label id="memPass-error" class="error" for="memPass">${errors['memPass'] }</label></td>
			</tr>
			<tr>
				<th>회원 명</th>
				<td><input type="text" name="memName" 
					value="${member.memName}" /><br>
				<label id="memName-error" class="error" for="memName">${errors['memName'] }</label></td>
			</tr>
			<tr>
				<th>회원 이미지</th>
				<td> 
					<input type="file" name="memImage" accept="image/*"> 
					<label id="memImage-error" class="error" for="memImage">${errors['memImage'] }</label></td>
			</tr>
			<tr>
				<th>주민등록번호1</th>
				<td><input type="text" name="memRegno1" 
					value="${member.memRegno1}" /><br>
				<label id="memRegno1-error" class="error" for="memRegno1">${errors['memRegno1'] }</label></td>
			</tr>
			<tr>
				<th>주민등록번호2</th> 
				<td><input type="text" name="memRegno2" 
					value="${member.memRegno2}" /><br>
				<label id="memRegno2-error" class="error" for="memRegno2">${errors['memRegno2'] }</label></td>
			</tr>
			<tr>
				<th>생일</th>
				<td><input type="date" name="memBir"
					value="${member.memBir}" /><br>
				<label id="memBir-error" class="error" for="memBir">${errors['memBir'] }</label></td>
			</tr>
			<tr>
				<th>우편 번호</th>
				<td><input type="text" name="memZip"
					value="${member.memZip}" />
				<input type="button" value="우편번호검색" id="searchZip">	
				<br>
				<label id="memZip-error" class="error" for="memZip">${errors["memZip"]}</label></td>
			</tr>
			<tr>
				<th>주소1</th>
				<td><input type="text" name="memAdd1"
					value="${member.memAdd1}" /><br>
				<label id="memAdd1-error" class="error" for="memAdd1">${errors['memAdd1'] }</label></td>
			</tr>
			<tr>
				<th>주소2</th>
				<td><input type="text" name="memAdd2"
					value="${member.memAdd2}" /><br>
				<label id="memAdd2-error" class="error" for="memAdd2">${errors['memAdd2'] }</label></td>
			</tr>
			<tr> 
				<th>집 전화 번호</th>
				<td><input type="text" name="memHometel"
					value="${member.memHometel}" /><br>
				<label id="memHometel-error" class="error" for="memHometel">${errors['memHometel'] }</label></td>
			</tr>
			<tr>
				<th>회사 전화 번호</th>
				<td><input type="text" name="memComtel"
					value="${member.memComtel}" /><br>
				<label id="memComtel-error" class="error" for="memComtel">${errors['memComtel'] }</label></td>
			</tr>
			<tr>
				<th>이동 전화 번호</th>
				<td><input type="text" name="memHp" 
					value="${member.memHp}" /><br>
				<label id="memHp-error" class="error" for="memHp">${errors['memHp'] }</label></td>
			</tr>
			<tr>
				<th>이메일 주소</th>
				<td><input type="text" name="memMail" 
					value="${member.memMail}" /><br>
				<label id="memMail-error" class="error" for="memMail">${errors['memMail'] }</label></td>
			</tr>
			<tr>
				<th>직업</th> 
				<td><input type="text" name="memJob"
					value="${member.memJob}" /><br>
				<label id="memJob-error" class="error" for="memJob">${errors['memJob'] }</label></td>
			</tr>
			<tr>
				<th>취미</th>
				<td><input type="text" name="memLike"
					value="${member.memLike}" /><br>
				<label id="memLike-error" class="error" for="memLike">${errors['memLike'] }</label></td>
			</tr>
			<tr>
				<th>기념일 명</th>
				<td><input type="text" name="memMemorial"
					value="${member.memMemorial}" /><br>
				<label id="memMemorial-error" class="error" for="memMemorial">${errors['memMemorial'] }</label></td>
			</tr>
			<tr>
				<th>기념일 날짜</th>
				<td><input type="date" name="memMemorialday"
					value="${member.memMemorialday}" /><br>
				<label id="memMemorialday-error" class="error" for="memMemorialday">${errors['memMemorialday'] }</label></td>
			</tr>
			<%if(session.getAttribute("authMember")!=null){ %>	
   
			<tr>  
				 
				<th>마일리지</th>
				<td><input type="text" name="memMileage" 
					value="${member.memMileage}" /><br>
				<label id="memMileage-error" class="error" for="memMileage">${errors['memMileage'] }</label></td>
			</tr>
			<tr>
				<th>삭제 여부</th>
				<td><input type="text" name="memDelete" 
					value="${member.memDelete}" /><br>
				<label id="memDelete-error" class="error" for="memDelete">${errors['memDelete'] }</label></td>
			</tr>
			<tr>  
				<th colspan="2"><input type="submit" value="저장"></th>
			</tr>
			<%}else{ %>     
			<tr>  
				<th colspan="2"><input type="submit" value="회원가입" id="insert"></th>
			</tr>
			<% } %> 
		</table>  
	</form>
	
 
	
	
<!-- The Modal --> 
<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-scrollable">
    <div class="modal-content">
      <div class="modal-header">  
        <h5 class="modal-title" id="exampleModalLabel">Modal title</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body" >
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary">Save changes</button>
      </div>
    </div>  
  </div>
</div> 


<script type="text/javascript">
$(function(){
	$("#memberForm").validate(); 


// 	function makeTdFromData(zipVO){
// 		let tds = [];
// 		for(let zipName in zipVO){
// 			let td = $('<td>').html(zipVO[zipName]);
// 			tds.push(td);
// 		}  
// 		return tds; 
// 	}
	
	
// 	let exampleModal = $("#exampleModal").modal({
// 		show : false
// 	}).on('show.bs.modal', function(event){
// 		let searchZip = event.relatedTatget;
// 		if(searchZip) return false;
// 		$.ajax({
<%-- 			url : "<%=request.getContextPath()%>/member/searchZipList.do", --%>
// 			dataType : "json",  
// 			success : function(resp) {  
// 				exampleModal.find(".modal-body").empty();
// 				let zipTable = $("<table>").prop('id', 'zipTable').prop('class', 'display');
//  				zipTable.append("<thead><tr><th>우편번호</th><th colspan='4'>주소</th></tr></thead>");
//  				zipTable.append("<tbody>");
//  				let trs = [];   
//  				$.each(resp, function(i,v){
// 					trs.push($("<tr>").append(makeTdFromData(v)));
// 				});  
 				
//  				zipTable.append(trs); 
//  				zipTable.append("</tbody>");
//  				exampleModal.find(".modal-body").append(zipTable); 
 				
//  				zipTable.DataTable();   
// 			},    
// 			error : function(errorResp) { 
   
// 			}
// 		});
// 	}).on('hidden.bs.modal', function(){
// 		exampleModal.find(".modal-body").empty(); 
// 	});
	
// 	$("#searchZip").on('click', function(){
// 		exampleModal.modal('show', this);
// 	}); 
	
});		
	</script>
<jsp:include page="/includee/footer.jsp"></jsp:include>
</body>
</html>