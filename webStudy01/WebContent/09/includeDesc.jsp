<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>09/includeDesc.jsp</title>
<jsp:include page="/includee/preScript.jsp"></jsp:include>
</head>
<body> 
<h4> include의 종류</h4>
<pre>
	: include 되는 시점과 대상에 따른 분류
	1. 정적 include : jsp 소스 파싱 단계, 소스 코드가 내포됨
		코드의 중복을 제거하는데 활용됨(비추천)
	<%-- <%@ include file="/02/standard.jsp" %> --%>  <!-- 코드공유가능해서 유지보수어렵..비추... -->
	web.xml 활용도 가능
	<%-- 
		String testResult = test();
	--%> 
	2. 동적 include : runtime, 실행 결과가 내포됨
	<%
		String dest = "/02/standard.jsp"; 
//		request.getRequestDispatcher(dest).include(request, response); 
//		pageContext.include(dest); //페이지모듈화, 버퍼를 한번 비움 (상태코드문제우려)
	%> 
	<!--custom tag/action tag - serverside  가독성.ui구성편리--> 
	<!--include action tag  모든페이지에 적용가능하게함.  but html다시 열려서 domtree구조 파괴->동적include사용시 include한 페이지의 루트코드 지워야함 -->
	<jsp:include page="/02/standard.jsp"/>  
	남은 잔여 코드<%--  <%=testResult %>  --%>
</pre>
</body>
</html>