<%@ page language="java" pageEncoding="UTF-8"%>
<%--
	response.setContentType("text/plain; charset=UTF-8");
	response.setHeader("Content-type", "text/plain; charset=UTF-8");
--%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>06/responseDesc.jsp</title>
</head>
<body>
<h4>HttpServletResponse (response 기본객체)</h4>
<pre>
	: 서버에서 클라이언트로 전송되는 데이터를 캡슐화한 객체.
	
	1. Response Line : protocol, status code(상태 코드)
<%-- 	<% 상태 코드 변경
		response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "강제 서버 에러");
		return;
	%> --%>
	 ** 상태 코드 : 요청 처리의 결과를 표현하는 숫자 체계
		 100~ : ing...
		 200~ : OK
		 300~ : 처리 완료를 이해 클라이언트로부터 추가적인 액션이 필요한 경우
		 	304(Not Modified), 302/307(Moved< Location)
		 400~ : client side fail 
		 	404(Not Found), 405(Not supported method)
		 	415(Not supported Media type)
		 	400(Bad Request)
		 	401(UnAuthorized), 403(Forbidden) 
		 500~ : server side fail
		 	500(Internal Server Error)
	2. Response Header : Meta data, setHeader(name, value);
		* Content-Type : body 영역의 데이터 mime
		* Cache-Control(1.1), Pragma(1.0), Expires(만료시간 제어) : 캐시를 제어할 때 사용됨
			<a href="cacheControl.jsp">캐시 제어 예제</a>
		* Refresh : auto request
			<a href="autoRequest.jsp">Refresh를 통한 자동 요청</a>
		* Location 
		
	3. Response Body 
</pre>
<img src="<%=request.getContextPath() %>/resources/images/cat3.jpg">
</body>
</html>