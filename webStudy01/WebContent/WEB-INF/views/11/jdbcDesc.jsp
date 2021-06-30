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
	width : 300px;
	margin-right: 10px;
} 
/* input{ */
/* 	float: right; */
/* } */
</style>
</head>
<body> 
<h4>JDBC(Java DataBase Connectivity)</h4>
<pre> 
	: 데이터베이스 프로그래밍 단계
	1. 벤더가 제공하는 드라이버를 찾고, 빌드패스에 추가.
	2. 드라이버 클래스 로딩 
	3. Connection 생성
	4. 쿼리 객체 생성 
		- Statement 
		-PreparedStatement
		-CallableStatement 
	5. 쿼리 실행 : DML 
		- ResultSet executeQuery() : SELECT
		- int executeUpdate() : INSERT, UPDATE, DELETE
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
				<div id="searchUI">
					<input type="text" name="search">
					<input type="button" value="검색" id="searchBtn">
				</div>
 			</td>  
		</tr> 
	</tfoot>
</table>
<form id='searchForm'>
	<input type="text" name="search">
	<input type="text" name="page">
</form> 
<script type="text/javascript">
//	데이터베이스로부터 raw 데이터 조회후, 
//	모든 property value에 조회날짜를 추가할 것.
	function makeTdFromData(propVO){
		let tds = [];
		for(let propName in propVO){
			let td = $('<td>').html(propVO[propName]);
			tds.push(td);
		}  
		return tds;
	}
	let tbody = $('table tbody');
	let searchUI = $("#searchUI").on('click', '#searchBtn',function(){
		let inputs = searchUI.find(":input[name]");
		$(inputs).each(function(idx, input){
			let name = this.name;
			let value = $(this).val();
			searchForm.find("[name='"+name+"']").val(value);
			
		});
		searchForm.submit(); 
	})
	let searchForm = $('#searchForm').on('submit', function(event){
		event.preventDefault();
		let formData = new FormData(this);
// 		let data = {};
// 		for(let key of formData.keys()){
// 			data[key] = formData.get(key); 
// 		}
		let data = $(this).serialize();
		$.ajax({  
<%-- 			url : "<%=request.getContextPath()%>/11/jdbcDesc.do", --%>
			data : data,
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
		return false;
	}).submit(); 
</script>
</body>
</html>