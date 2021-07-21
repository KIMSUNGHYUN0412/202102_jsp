<%@page import="kr.or.ddit.servlet01.ImageListServlet"%>
<%@page import="java.io.File"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>03/resourceIdentify.jsp</title>
</head>
<body>
<h4> 자원의 식별</h4>
<pre>
	1. file system resource : d:\contents\cat2.jpg
	2. web resource : (URL/URI), http://localhost:port/contextPath/images/cat2.jpg
	3. classpath resource : /kr/or/ddit/servlet01/cat2.jpg
	<%
		File fileSystemRes = new File("d:/contents/cat2.jpg");
		String realPath = application.getRealPath("/images/cat2.jpg"); 
		File webRes = new File(realPath);
		String realPath2 = ImageListServlet.class.getResource("/kr/or/ddit/servlet01/cat2.jpg").getFile();
		File classPathRes = new File(realPath2); 
		
	%> 
	<%=fileSystemRes.length()%>
	<%=realPath%> : <%=webRes.length() %>
	<%=realPath2%> : <%=classPathRes %>
	
	** web resource 식별 방법
	URI(Uniform resource Identifier) 
	 -URL(Uniform Resource Locator) : 자원의 위치를 기준으로 식별 
	 -URN(Uniform Resource Name) : 자원의 등록된 이름으로 식별
	 -URC(Uniform Resource Content) : 자원의 등록된 컨텐츠로 식별
	<%=request.getRequestURI() %>
	<%=request.getRequestURL() %>
	
	자원에 접근하는 경로 표기법
	1. 상대 경로 : 경로가 생략된 구조 wild card(.,..)
				현재 위치(브라우저의 주소)를 기준으로 실제 자원의 절대 경로를 판단함.
	2. 절대 경로 : "/"로 시작
		1) client side : context root부터 시작되는 경로 표기 (request.getContextPath) 
		2) server side : context root 이후의 경로 표기 (server side에서는 상대경로표기 XX)
</pre>  
<img src="../images/cat2.jpg" alt=""/>
<img src="http://localhost/webStudy01/images/cat2.jpg" alt=""/>
<img src="<%=request.getContextPath() %>/images/cat2.jpg" alt=""/>
</body>
</html>