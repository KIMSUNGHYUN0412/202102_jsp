<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>07/destination.jsp</title>
</head>
<body> 
도착페이지
<pre>
	request scope : <%= request.getAttribute("contents")%>
	session scope : <%= session.getAttribute("contents")%>
</pre> 
</body> 
</html>