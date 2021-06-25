<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>08/btsForm.jsp</title>
</head>
<body>
<%
	Map<String,String[]> btsMap = (Map)application.getAttribute("btsMap");
%>
<form method="post">
	<select name="btsMember" onchange="this.form.submit();">
		<option value>멤버 선택</option>  
		<%
			for(Entry<String, String[]> entry : btsMap.entrySet()){
				String code = entry.getKey();
				String[] info = entry.getValue();
				%>
				<option value="<%=code%>"><%=info[0] %></option>
				<%  
			}
		%>
	</select>
</form>
</body>
</html>