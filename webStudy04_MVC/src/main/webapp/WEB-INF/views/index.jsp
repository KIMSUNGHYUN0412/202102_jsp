<%@page import="kr.or.ddit.vo.MemberVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<h4>웰컴 페이지</h4>    
누적 방문자 수 : ${userCount }, 현재 방문자 수 : ${currentUserCount }
<!-- 로그인 되어있다면 접속자 리스트 보여주기 -->
<!-- 모두가 공유가능한 리스트 생성 / 로그인했을때 리스트에 추가 / 로그아웃했을때 리스트에서 제거 -->
<c:if test="${not empty authMember }">
<br>접속자 리스트 : ${currentUserList } 
</c:if>
<br><br>  
<fmt:requestEncoding value="utf-8"/>  
${param.message }
<c:choose> 
	<c:when test="${empty authMember }">
		<h4>
			<a href="${pageContext.request.contextPath }/login/loginForm.jsp">로그인하러 가기</a><br>
			<a href="${pageContext.request.contextPath }/member/memberInsert.do">회원가입</a>
		</h4>  
	</c:when> 
	<c:otherwise> 
		<h4>   
			<a href="${pageContext.request.contextPath }/mypage.do">${authMember.memName}님</a>
			<c:if test="${not empty authMember.memImg }">
				<img src="data:image/*;base64, ${authMember.base64Img }"/>
			</c:if>
			<br><br> 
			<a href="${pageContext.request.contextPath }/login/logout.do">로그아웃</a>
		</h4>
	</c:otherwise>
</c:choose> 


 