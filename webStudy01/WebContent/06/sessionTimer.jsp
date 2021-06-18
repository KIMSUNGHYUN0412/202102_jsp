<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>06/sessionTimer.jsp</title>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/custom.js"></script>
<script type="text/javascript">
	$.customAlert("메시지");
	$(function(){  
		let element = $("#timerArea").sessionTimer({
			timeout : <%=session.getMaxInactiveInterval() %>,
			url: "<%=request.getContextPath()%>/sessionExtend"
		}); 
		console.log("============================="); 
		console.log(element); 
	});
</script>  
</head>
<body>  
<h4>세션 타이머</h4>
<%=session.getId()%> : <%=session.getMaxInactiveInterval() %>
 <h4 id="timerArea"></h4> 
 <pre>
 1. 1초마다 출력되는 시간을 디스카운트
 2. 1분 남은 시점에 메시지를 출력
 	세션 연장 여부를 확인 
 3. 세션 연장을 선택한 경우,  
 	1) 타이머 리셋
 	2) 세션 연장을 위한 새로운 요청 발생(비동기- /sessionExtend, body가 없는 응답) 
 </pre>
 	<div id="messageArea">
 		세션을 연장하겠습니까?<br>
 		<input type="button" id="yesBtn" value="예">
 		<input type="button" id="noBtn" value="아니오">
 	</div>
<%-- <script type="text/javascript">
	let timerArea = $("#timerArea");
	let mi = 2;  
	let ss = 00;
	$("#messageArea").hide();
	timerArea.text(mi + ":" + ss)
	interval = setInterval(function() {
		ss = 60; 
		timerArea.text(--mi + ":" + (--ss));
		if(ss==0 && mi==1){
			$("#messageArea").show();
			ss=60; 
		} 
		if(ss==0 && mi==0)clearInterval(interval);
	}, 1000); 
	
	$('#noBtn').on('click',function(){
		$("#messageArea").hide();
	});
	$('#yesBtn').on('click', function( ){
		
 		$.ajax({
			url : "<%=request.getContextPath()%>/sessionExtend",
			method : "head",
			dataType : "",
			success : function(resp) {

			},
			error : function(errorResp) {

			}
		}); 
	});
</script> 	 --%>
</body>
</html>