<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>07/implicitObject.jsp</title>
</head>
<body>
<h4>기본객체(내장객체)</h4>
<pre>
<%-- <%=	request.getContextPath()%>
<%=	((HttpServletRequest)pageContext.getRequest()).getContextPath()%>
${pageContext.request.contextPath} <!-- 기본객체 의미다름. pageContext만 같이 사용 가능 --> --%>
	: jsp container에 의해 서블릿 소스가 파싱될 때 자동으로 생성되는 객체
	1. request(*) : client와 request에 대한 정보를 가진 객체
	2. response(*) : client로 전송될 response에 대한 정보를 가진 객체
	3. out(JspWriter, *) : 응답데이터를 버퍼에 기록할 출력 스트림 
		: buffer를 제어하거나 상태를 확인할 때도 활용됨
	4. session(HttpSession, *) : 하나의 클라이언트가 하나의 브라우저를 사용할 때, 해당 클라이언트를 식별할 용도로 사용됨 
		<a href="sessionDesc.jsp">session desc</a>
	5. application(ServletContext, **) : 현재 서버와 어플리켕션 자체에 대한 정보를 가진 객체
		<a href="../08/applicationDesc.jsp">application desc</a>
	6. config(ServletConfig) 
	7. page(Object)==this : jsp 인스턴스 자체
 	8. exception(Throwable) : 에러나 예외가 발생했을 때 그 상황을 처리할 목적의 페이지에서 사용됨
 		page지시자의 isErrorPage="true"인 경우에만 활성화됨.
	9. pageContext(*****) : 모든 기본객체 중 가장 먼저 생성되고, 나머지 기본객체에 대한 참조를 가짐.
</pre>
</body>
</html>