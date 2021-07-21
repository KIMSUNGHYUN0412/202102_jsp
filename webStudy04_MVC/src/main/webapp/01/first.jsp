<%@page import="java.util.Date"%>
<!-- 지시자 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<!-- scriptlet -->
<% 
	String data = new Date().toString();
%>
<!-- 표현식  -->
<h4><%=data%></h4>
</body>
</html>