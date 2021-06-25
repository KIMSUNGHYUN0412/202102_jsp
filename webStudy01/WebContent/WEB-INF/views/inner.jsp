<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>web-inf/views/inner.jsp</title>
</head>
<body>
여기까지 오기위한 경로는...?
<pre>
<%=request.getAttribute("contents") %>
</pre>
</body>
</html>