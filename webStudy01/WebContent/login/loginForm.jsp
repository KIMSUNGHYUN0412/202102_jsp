<%@page import="java.util.Objects"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>login/loginForm.jsp</title> 
<style type="text/css">
.error{
	color : red;
}
</style>
<script src="<%=request.getContextPath() %>/resources/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/jquery-validation@1.19.3/dist/jquery.validate.min.js"></script>
<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/jquery-validation@1.19.3/dist/additional-methods.min.js"></script>
<!-- 필수파라미터 검증. 비밀번호 영문자특수문자숫자 정규식. jquery validate plugin -> demos 이용하여 client side 검증구조 완성  -->
</head>  
<body>
<% 
	//flash attribute
	String message = (String)session.getAttribute("message");
	session.removeAttribute("message");
%>
<div class="error"> 
<%-- 	<%=request.getAttribute("errors") %> --%>
	<%=message %>
	${errors }  <!--null은 자체적으로 ""로 바꿈  -->
</div>
<form name="loginForm" action="<%=request.getContextPath() %>/login/loginCheck.do" method="post">
<%-- 	<% 
 		String failId = request.getParameter("mem_id");
	%> --%>
	<ul>   
		<li> 
			아이디 : <input type="text" name="mem_id" value="${failId }" required data-msg-required="필수 데이터">
		</li> 
		<li><!--pattern="^(?=.*[a-z]+)(?=.*[A-Z]+)(?=.*[0-9]+)(?=.*[!@#\\$%\\^\\&\\*]+).{4,8}$"  --> 
			비밀번호 : <input type="text" 
					 name="mem_pass" required data-msg-pattern="형식확인">
			<input type="submit" value="로그인">  
		</li> 
	</ul>   
</form>  
<script type="text/javascript">
//	$("[name='loginForm']").validate();
	/* $("[name='loginForm']").on('submit', function(){
		let regexPtrn = this.mem_pass.pattern; 
		let password = this.mem_pass.value;
//		/regex/igm
		let regexp = new RegExp(regexPtrn, "gm");
		let result = regexp.test(password);
		console.log(regexp.exec(password));  
//		return result;     
		return true;     
	}); */
</script>
</body>
</html>