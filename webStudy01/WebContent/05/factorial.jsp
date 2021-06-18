<%-- <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>05/factorial.jsp</title>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/jquery-3.6.0.min.js"></script>
</head>
<body>
<pre>
	반갑습니다
	저는 못합니다....
	항상 건강하세요
	똘또올 하네!!!
left 입력을 통해 숫자를 입력받고,
값이 변경되는 순간 서버로 비동기 요청 발생시킴.
서버에서 factorial 연산을 수행한 후,
선택한 mime의 형태로 응답을 전송
plain : "2! = 2"
json : 
{
	left : 2,
	operator : !,
	expression : "2! = 2"
}
xml :
&lt;result>
	&lt;left>2&lt;/left>
	&lt;expression>2! = 2&lt;/expression>
&lt;/result>
</pre>
<form action="/05/factorial">
<input type="radio" name="mime" value="json">JSON
<input type="radio" name="mime" value="plain">PLAIN
<input type="radio" name="mime" value="xml">XML
<input type="number" name="left" min="1" max="10"> !
</form> 
<div id="resultArea">
<script type="text/javascript">

	$('input[name=left]').on('change',function(){
		num = $(this).val().trim()
		if(num=="")return false; 
		$.ajax({
			url : "",
			data : "",
			method : "",
			dataType : "",
			success : function(resp) {

			},
			error : function(errorResp) {

			}
		});
	});

	
</script>
</div>
</body>
</html> --%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jquery-3.6.0.min.js"></script>
<style>
@import url('https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@300&display=swap');
body{
   font-family: 'Noto Sans KR', sans-serif;
   padding: 50px;
}
pre{
   font-family: 'Noto Sans KR', sans-serif;
   border: 2px dashed black;
   padding: 20px;
}
div{
   margin-top: 20px;
   border: 1px solid hotpink;
   padding: 20px;
}
</style>
<title>05/factorial.jsp</title>
</head>
<body>
<pre>
left 입력을 통해 숫자를 입력받고,
값이 변경되는 순간 서버로 비동기 요청 발생
서버에서 factorial연산을 수행한 후,
선택한 mime의 형태로 응답 전송
plain: "2! = 2"
json: {
      left: 2
      operator: !,
      expression: "2! = 2"
   }
xml:
&lt;result>
   &lt;left>2&lt;/left>
   &lt;expression>2! = 2&lt;/expression>
&lt;/result>
</pre>
   <form action="/05/factorial">
      <input type="radio" name="mime" value="json" checked>JSON
      <input type="radio" name="mime" value="plain">PLAIN
      <input type="radio" name="mime" value="xml">XML
      <input type="number" name="left" min="1" max="10">!
   </form>
   <div id="resultArea">
      
   </div>
<script>
   $('input[type=number]').on('change', function(){
      let left = $(this).val();
      let mime = $('input[name=mime]:checked').val();
      let data = {};
      if(left && mime){
         data.mime = mime;
         data.left = left;
      }
      if(mime == 'plain') mime = 'text';
      $.ajax({
         url : '<%= request.getContextPath() %>/05/factorial',
         data : data,
         method : 'post',
         dataType : mime, 
         success : function(res) {
            $('#resultArea').html(res);
         },
         error : function(xhr) {
            alert(xhr.status);
         }
      });
   });
</script>
</body>
</html>