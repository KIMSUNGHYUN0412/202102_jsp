<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>11/performance.jsp</title>
</head>
<body>
<h4>성능 체크</h4>
<pre>
	소요 시간 : process time + latency time
	<a href="oneConnOneProcess.jsp">1번의 연결과 1번의 처리 : 40ms</a>
	<a href="100Conn100Process.jsp">100번의 연결과 100번의 처리 : 1700ms</a>
	<a href="oneConn100Process.jsp">1번의 연결과 100번의 처리 : 70ms</a>
</pre>
</body> 
</html>