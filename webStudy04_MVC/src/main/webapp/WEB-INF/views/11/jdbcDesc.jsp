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
tfoot{
	text-align: center;
}
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
			<th>PROPERTYNAME</th>      
			<th>PROPERTYVALUE</th>
			<th>DESCRIPTION</th>
		</tr> 
	</thead>
	<tbody>
 
	</tbody>
	<tfoot>
		<tr>
			<td colspan="3">  
				<div id="pagingArea"></div>				
				<div id="searchUI">	
<!-- 					<select name="searchType">  -->
<!-- 						<option value>전체</option> -->
<!-- 						<option value="name">PROPERTYNAME</option> -->
<!-- 						<option value="value">PROPERTYVALUE</option> -->
<!-- 						<option value="desc">DESCRIPTION</option> -->
<!-- 					</select>  -->
					<input type="text" name="search">
					<input type="button" value="검색" id="searchBtn">
				</div>
 			</td>  
		</tr>  
	</tfoot> 
</table>
<form id='searchForm'> 
	<input type="hidden" name="search">
<!-- 	<input type="hidden" name="searchType"> -->
<!-- 	<input type="hidden" name="searchWord"> -->
	<input type="hidden" name="page">
</form> 
<script type="text/javascript">
	$(document).ajaxError(function(event, xhr, options, error){
		console.log(event);
		console.log(xhr);
		console.log(options);
		console.log(error); 
	}).ajaxComplete(function(event, xhr, options){
		searchForm.find("[name='page']").val("");
		searchForm.get(0).reset();
	}); 

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
	//페이징 처리
	let searchUI = $("#searchUI").on('click', '#searchBtn',function(){
		let inputs = searchUI.find(":input[name]");
		$(inputs).each(function(idx, input){
			let name = this.name;
			let value = $(this).val();
			searchForm.find("[name='"+name+"']").val(value);
			 
		});
		searchForm.submit(); 
	});
	
	//페이징 처리
	let pagingArea = $('table tfoot #pagingArea').on("click", ".pageLink", function(event){
		event.preventDefault();
		let page = $(this).data("page"); 
		searchForm.find('[name="page"]').val(page);
		searchForm.submit(); 
		return false;  
	}).css("cursor", "pointer");
	
	let searchForm = $('#searchForm').on('submit', function(event){
		event.preventDefault();
		let formData = new FormData(this);
// 		let data = {};
// 		for(let key of formData.keys()){ 
// 			data[key] = formData.get(key); 
// 		} 
		let data = $(this).serialize(); 
		console.log(data);
		$.ajax({  
<%-- 			url : "<%=request.getContextPath()%>/11/jdbcDesc.do", --%>
			data : data,
			dataType : "json", 
			success : function(resp) {
				tbody.empty(); 
				pagingArea.empty();
				let trs = [];  
				console.log(resp)
				$.each(resp.dataList, function(i,v){
					trs.push($("<tr>").append(makeTdFromData(v)));
				});  
				tbody.append(trs);     
				pagingArea.append(resp.pagingHTML);				
			}
			
		});
		return false;
	}).submit(); 
</script>
</body>
</html>