<%@page import="kr.or.ddit.vo.MemberVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>12/elDesc.jsp</title>
</head>
<body>
<h4> 표현 언어 (Expression Language : EL)</h4>
<pre>
	: (속성)값을 출력(표현)할 목적만 가진 언어.
	\${속성명}
	<%
		String sample = "데이터";
	//가장 작은 영역의 scope부터 찾는다.
		pageContext.setAttribute("sampleAttr", "페이지 데이터");
		request.setAttribute("sampleAttr", sample);
		session.setAttribute("sampleAttr", "세션 데이터");
		application.setAttribute("sampleAttr", "어플리케이션 데이터"); 
		
		pageContext.setAttribute("text1", "  ");
		pageContext.setAttribute("array1", new String[]{}); 
	%>
	<%=sample %>, ${requestScope.sampleAttr}, <%=request.getAttribute("sampleAttr") %>
	 ${sampleAttrr}, <%=request.getAttribute("sampleAttrr") %> <!-- (el 자동 whitespace) , null -->
	 
	 지원 기능
	 1. 연산자  <!-- //할당연산자 X -> 증감연산자(++,--) X ...3.0부터는 됨..--> 연산의 중심은 연산자~
	 		산술연산 : (+-*/%) ${5+2} 
	 					${"5"+"2"} -> 문자를 파싱해서 산술연산  
	 					\${"a"+"1"} -> 파싱 에러 
	 					${5/2} -> 실수 연산
	 					${abc-def} -> 속성값 null -> 0으로 강제치환 
<%-- 	 		논리연산 : &&(and) ||(or) !(not) ${true and true}  ${"true" and "true"}  ${true and abc}  ${abc and def}  ${not abc} ->속성값 null ->false --%>
	 		비교연산 : >(gt), <(lt), >=(ge), <=(le), ==(eq), !=(ne)
	 					${3 lt 4} ${3 gt abc} ${true != true} ${sampleAttr eq "페이지 데이터"} ->피연산자 객체->equals메서드 호출
	 		단항연산자 : empty ${empty sampleAttra} ->속성 유무 판단->속성이 없으면 true 
	 							${empty text1} -> 있으면 type체크 -> length>0일때만 있다고 판단
	 							${empty array1} ->  collection객체면 size>0일때만 있다고 판단
	 		삼항연산자 : (조건식 ? 참 : 거짓)
	 					${not empty abc ? "없다" : "있다"} 
	 2. 자바 객체(자바빈규약) 메소드 호출 : ${applicationScope.sampleAttr.length()} <!-- 2.2부터 됨.. -->
	 <%
	 	MemberVO member = MemberVO.builder().memName("김은대").build();
	 	pageContext.setAttribute("member", member);
	 %>
	 ${member.getMemName()} , ${member.memName}->내부적으로 getter호출
	 ${member.getMemTest() }, ${member.memTest }
	 3. collection 접근 방법 <a href="elCollection.jsp">collection 접근 방법</a>
	 4. 기본 객체 (11 -> Map)
	 	Scope : pageScope, requestScope, sessionScope, applicationScope
	 			${sessionScope.sampleAttr }, ${sessionScope['sampleAttr'] }
	 			${pageScope.member.memName }, ${pageScope['member'].memName }, ${pageScope['member']['memName'] } 
	 	요청parameter : param(Map&lt;String,String>), paramValues(Map&lt;String,String[]>)
						${param.param1 },  ${paramValues.param1[1] }	 			 
		요청header : header(Map&lt;String,String>), headerValues(Map&lt;String,String[]>)
						${header.accept }
						${header['accept'] } 
						${header.user-agent }->.표기법단점, ${header['user-agent'] }
		<%
			Cookie[] cookies = request.getCookies();
			for(Cookie tmp : cookies){
				if(tmp.getName().equals("JSESSIONID")){
					out.print(tmp.getValue());
				}
			}
		%>
		cookie(Map&lt;String,Cookie>) : ${cookie.JSESSIONID.getValue()}
										${cookie.JSESSIONID.value}
										${cookie['JSESSIONID']['value']}
		컨텍스트 파라미터 : initParam 
					<%=application.getInitParameter("cParam1") %>
					${initParam.cParam1 }, ${initParam['cParam1']}
		pageContext :
				<%=pageContext.getRequest() %> 
				${pageContext.request.contextPath }
		
</pre> 
</body>
</html>