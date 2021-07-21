<%@page import="java.util.Enumeration"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>03/requestDesc.jsp</title>
</head>
<body>
<h4>HttpServletRequest request</h4>
<pre>
	: 클라이언트와 그로부터 발생한 요청에 대한 모든 정보를 가진 객체
	
	http request spec
	1. Request Line : Protocol URL Method - 명령 식별 
		-Method : 요청을 발생시킨 목적(의도), 요청 패키징 방법
		 R - GET 
		 C - POST
		 U - PUT/PATCH
		 D - DELETE
		 options : preflight요청으로 특정 메소드의 지원 여부를 확인할 목적의 요청에 사용
		 head : response를 받아올 때 body를 제외하고 싶은 요청에 사용
		 trace : server debugging
	2. Request Header : 클라이언트에 대한 메타 정보 영역 (meta data)
	3. Request Body(Message Body, Contents Body) : 서버로 전송할 메시지 영역
	
	Line : <%=request.getProtocol()%> <%=request.getRequestURI() %> <%=request.getMethod() %>
	Body : <%=request.getInputStream().available() %>
		   <%=request.getCharacterEncoding() %> 
		   <%=request.getContentLength() %>  
		   <%=request.getContentType() %> 
</pre> 
<table>
	<thead>
		<tr>
			<th>헤더명</th>
			<th>헤더값</th>
		</tr>
	</thead>
	<tbody>
		<%
		Enumeration<String> names = request.getHeaderNames();
		while(names.hasMoreElements()){
			%>
			<tr>
			<% 
			String headerName = names.nextElement();
			String headerValue = request.getHeader(headerName);
			%>
			<th><%=headerName %></th>
			<td><%=headerValue %></td>
			</tr>
			<% 
		}
		%>
	</tbody>
</table>
</body>
</html>