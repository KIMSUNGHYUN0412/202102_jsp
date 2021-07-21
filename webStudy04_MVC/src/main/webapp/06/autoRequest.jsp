<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- <meta http-equiv="Refresh" content="5;url=http://www.naver.com">  -->
<title>06/autoRequest.jsp</title>  
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/jquery-3.6.0.min.js"></script>
</head>
<body>
<%--
	response.setIntHeader("Refresh", 1);
--%>
<h4><span id="timer"></span>초 뒤에 네이버로 이동</h4>
<h4>현재 서버의 시간 : <%=new Date() %></h4>
<h4 id="watch">현재 클라이언트의 시간 : <span></span></h4>

<h4> 자동 요청을 통해 데이터를 갱신하는 방법 </h4>
<pre> 
	1. server side : Refresh 응답 헤더를 통해 자동 요청 
	2. client side
		1) HTML : meta태그의 http-equiv를 통해 refresh 라는 html 헤더를 설정
		2) Javascript : 스케쥴링 함수의 활용
			** 스케쥴링 함수
				- setInterval : 주기적인 반복작업에 활용
				- serTimeout : 작업의 지연 시간을 설정하고, 한번 실행하는 구조
</pre>
<script type="text/javascript">
/* 	let timer = $("#timer");
	let initValue = 5; 
	setInterval(function() {
		$("#watch span").text(new Date());
		timer.html(--initValue);
	}, 1000); */
</script>
</body>
</html>