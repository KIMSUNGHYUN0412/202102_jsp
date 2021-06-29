<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>11/jdbcDesc.jsp</title>
<jsp:include page="/includee/preScript.jsp"/>
<style>
table{ 
	border-collapse : collapse;
}
th, td{
	border : 1px solid gray;
}
input[type=text]{
	width : 500px;
	margin-right: 10px;
}
input{
	float: right;
}
</style>
</head>
<body> 
<h4>JDBC(Java DataBase Connectivity)</h4>
<pre> 
	: 데이터베이스 프로그래밍 단계
	1. 벤더가 제공하는 드라이버를 찾고, 빌드패스에 추가.
	<%
// 	데이터베이스로부터 raw 데이터 조회후, 
// 	모든 property value에 조회날짜를 추가할 것.
	%> 
	6. 질의 결과 사용
	7. 자원 해제
</pre> 
<table> 
	<thead>
		<tr>
			<th>PROPERTY_NAME</th>
			<th>PROPERTY_VALUE</th>
			<th>DESCRIPTION</th>
		</tr>
	</thead>
	<tbody>
 
	</tbody>
	<tfoot>
		<tr>
			<td colspan="3"> 
				<input type="submit" value="검색">
				<input type="text" name="search">
 			</td> 
		</tr>
	</tfoot>
</table>
<script type="text/javascript">
	function makeTdFromData(propVO){
		let tds = [];
		for(let propName in propVO){
			let td = $('<td>').html(propVO[propName]);
			tds.push(td);
		}  
		return tds;
	}
	let tbody = $('table tbody');
	$.ajax({ 
		url : "<%=request.getContextPath()%>/11/jdbcDesc.do",
		dataType : "json", 
		success : function(resp) {
			tbody.empty();
			let trs = [];
			$.each(resp, function(i,v){
				trs.push($("<tr>").append(makeTdFromData(v)));
					 
			}); 
			tbody.append(trs);  
		},
		error : function(errorResp) {

		}
	});
</script>
</body>
</html>