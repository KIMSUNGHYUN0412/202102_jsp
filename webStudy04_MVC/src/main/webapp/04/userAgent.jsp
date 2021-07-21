<%@page import="kr.or.ddit.enumtype.BrowserType"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Enumeration"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- <%!
public static enum BrowserType{
	MSIE("익스플로러 구버전"), 
	TRIDENT("익스플로러 최신버전"), 
	OPERA("오페라"), 
	FIREFOX("파이어폭스"), 
	EDG("엣지"), 
	CHROME("크롬"), 
	SAFARI("사파리"), 
	OTHER("기타");
	private String browserName; 
	
	private BrowserType(String browserName){
		this.browserName = browserName;
	}
	
	public String getBrowserName(){
		return this.browserName;
	}
}
%> --%>
<!DOCTYPE html>
<html> 
<head>
<meta charset="UTF-8">
<title>04/userAgent.jsp</title> 
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		const PATTERN = "당신의 브라우저는<span> %s </span>입니다. OS의 종류는<span> %o </span>입니다.";
		let resultArea = $("#resultArea");
		$("a:first").on("click", function(){
			event.preventDefault();
			$.ajax({ //문자열 기반 동작 함수
				url : "<%=request.getContextPath()%>/04/getBrowserName",
				dataType : "json", // request header(Accept) /response header(Content-Type)
									// text: text/plain;, html:text/html, json: application/json, script: text/javascript
				success : function(resp) {
					let brows = null;
					let os = null;
					if(typeof resp == "string"){
						brows = resp;
					}else{
						brows = resp.browser;
						os = resp.os;
					} 
					resultArea.empty();  
					resultArea.append(
						$("<p>").html(PATTERN.replace("%s", brows).replace("%o", os))
	 				);  
				},  
				error : function(errorResp) {
					console.log(errorResp);
				}
			});
			return false;
		});
	});
</script>
<style>
div{
	margin : 50px;
	border : 1px solid grey;
	width : 500px;
	padding : 10px;
}
span{
	color: blue;
}
p{
	margin: 0;
}
</style>
</head>
<body>
<a href="#" >브라우저의 이름 받아오기(비동기로)</a>
<div id="resultArea"></div>

<!--  사용자의 브라우저를 식별하고, 각 브라우저에 맞는 메시지 출력   -->
<!-- "당신의 브라우저는 ㅇㅇㅇ 입니다." 형식으로 포맷팅 메시지를 사용함 -->

<%-- <%
	String format = "<h4>당신의 브라우저는<span> %s</span>입니다.</h4>"; 
/* 	String userAgent = request.getHeader("user-agent").toUpperCase(); */
	String userAgent = request.getHeader("user-agent");
	
/* 	Map<String,String> browserMap = new LinkedHashMap<>();  //이전entry의 link가지고 있어 순서 집합처럼 사용 가능
	browserMap.put("MSIE", "익스플로러 구버전");
	browserMap.put("TRIDENT", "익스플로러 최근버전");
	browserMap.put("OPERA", "오페라");
	browserMap.put("FIREFOX", "파이어폭스");
	browserMap.put("EDG", "엣지");
	browserMap.put("CHROME", "크롬");
	browserMap.put("SAFARI", "사파리");
	browserMap.put("OTHER", "기타");
	
	String browser = browserMap.get("OTHER");
	
	for(Entry<String,String> entry : browserMap.entrySet()){
		if(userAgent.indexOf(entry.getKey()) > -1){
			browser = entry.getValue();
			break;
		} 
	} */
	String browser = BrowserType.parseUserAgent(userAgent); 
	
	out.print(String.format(format, browser));
	
%> --%>

</body>
</html>