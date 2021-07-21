<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>error/errorView.jsp</title>
</head>
<body>
<h4> 지역적 에러 처리 방법에 따라 사용되는 에러 페이지</h4>
<pre>
	자라고 그렇게 시간을 줬는데
	<%
		exception.printStackTrace();
		ErrorData ed = pageContext.getErrorData();
	%>
	<%=exception.getMessage() %>
	<%=ed.getRequestURI() %>
	<%=ed.getStatusCode() %>
	<%=ed.getThrowable()==exception %>
	그땐 또 말똥말똥해서
	이제야 졸리다 
</pre>
</body>
</html>