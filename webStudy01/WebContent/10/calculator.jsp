<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  
<form action="<%=request.getContextPath() %>/calculate.do">
	<input type="radio" name="mime" value="plain">PLAIN
	<input type="radio" name="mime" value="json">JSON
	<input class="form-control" type="number" name="left">
	<select name="operator"> 
		<option value>연산자</option>
		<option value="add">+</option>
		<option value="sub">-</option>
		<option value="mul">*</option>
		<option value="div">/</option> 
		<option value="mod">%</option>
	</select> 
	<input class="form-control" type="number" name="right">
	<input type="submit" value="=">
	<span id="resultArea"></span> 
</form> 
 <script>
 	$("form").on("submit",function(event){
 		event.preventDefault();
 		return false;
 	});
 </script> 