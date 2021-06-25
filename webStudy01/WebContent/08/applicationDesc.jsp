<%@page import="java.io.IOException"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.nio.file.StandardCopyOption"%>
<%@page import="java.nio.file.Paths"%>
<%@page import="java.nio.file.Path"%>
<%@page import="java.net.URL"%>
<%@page import="java.nio.file.Files"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.io.FileOutputStream"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.File"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>08/applicationDesc.jsp</title>
</head>
<body>
<h4>ServletContext application</h4>
<pre>
	: Servlet container(WAS)와 해당 컨테이너 내에서 운영되는
	   어플리케이션(context)에 대한 정보를 가진 객체 /싱글톤
		1. 컨텍스트 초기화 파라미터 확보 
		<%=application.getInitParameter("contentsPath") %>
		2. 로그 기록 
		<%
			application.log("명시적으로 기록할 로그 데이터");
		%>
		3. 서버나 컨텍스트에 대한 정보 확인
		<%=application.getServerInfo() %>, 
		<%=application.getMajorVersion() %>.<%=application.getMinorVersion() %>
		4. 웹리소스 확보(****)
		/resources/images/cat2.jpg
		<%
			String imageURL = "/resources/images/cat2.jpg";
/* 			String realPath = application.getRealPath(imageURL);
			File readFile = new File(realPath);
			FileInputStream fis = new FileInputStream(readFile); */
			String destURLStr = "/08/cat2.jpg";
			URL destURL = application.getResource(destURLStr);
			Path target = null;
			if(destURL==null){
				String destRP = application.getRealPath(destURLStr);	
				target = Paths.get(destRP);
			}else{
				target = Paths.get(destURL.toURI());
				
			}  
			try{
				InputStream is = application.getResourceAsStream(imageURL);
				Files.copy(is, target, StandardCopyOption.REPLACE_EXISTING); 
				if(1==1) throw new SQLException("강제 발생 예외");
			}catch(SQLException e){
				System.err.println(e.getMessage());
				throw new IOException(e);
			}
		%> 
		   
		/08/cat3.jpg
		<img src="<%=application.getContextPath() %>/08/cat2.jpg">
</pre>
</body>
</html>