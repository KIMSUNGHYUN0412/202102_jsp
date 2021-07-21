<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>08/btsForm.jsp</title>
</head>
<body>

<form method="post">
	<select name="btsMember" onchange="this.form.submit();">
		<option value>멤버 선택</option>  
		<c:forEach items="${btsMap }" var="entry">
			<option value="${entry.key }">${entry.value[0] }</option>
		</c:forEach>
		
	</select>
</form>
</body>
</html>