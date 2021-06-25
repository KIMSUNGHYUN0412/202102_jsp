<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>05/factorial.jsp</title>
<%-- <script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jquery-3.6.0.min.js"></script> --%>
<jsp:include page="/includee/preScript.jsp"/>
<style>
/* @import url('https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@300&display=swap'); */
body{
/*    font-family: 'Noto Sans KR', sans-serif; */
/*    padding: 50px; */
} 
pre{
/*    font-family: 'Noto Sans KR', sans-serif; */
   border: 2px dashed black;
   padding: 20px;
}
#resultArea{ 
   margin-top: 20px;
   border: 1px solid hotpink;
   padding: 20px;  
}
</style>
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
   <form action="<%=request.getContextPath() %>/05/factorial">
      <input type="radio" name="mime" value="json" data-type="json" data-success="parseJson">JSON
      <input type="radio" name="mime" value="plain" data-type="text" data-success="parsePlain" checked>PLAIN
      <input type="radio" name="mime" value="xml" data-type="xml" data-success="parseXml">XML
      <input type="number" name="left" min="1" max="10">!
   </form>
   <div id="resultArea">
      
   </div> 
   
<script type="text/javascript">
	let resultArea = $("#resultArea");
	function parsePlain(resp){ 
		console.log(resp); 
		resultArea.html(resp);
	} 
	function parseJson(resp){
		console.log(resp); 
		resultArea.html(resp.expression);
	}
	function parseXml(resp){
		console.log(resp); 
		resultArea.html($(resp).find("expression").text()); 
	}
 	$("form:first").on("submit",function(event){
 		event.preventDefault();
 		let url = this.action;
 		let data = $(this).serialize();
 		let method = this.method;
 		console.log(data);
 		let radio = $(this).find("[name='mime']:checked");
 		let dataType = $(radio).data("type");
 		let success = eval($(radio).data("success"));
 		console.log(success);
		$.ajax({
			url : url,
			data : data,
			method : method,
			dataType : dataType,
			success : success,  
			error : function(errorResp) {

			}
		});
 		return false;
 	}).find(":input").on("change",function(){
 		$(this.form).submit();
 	});
</script>
<%-- <script>
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
</script> --%>
</body>
</html>