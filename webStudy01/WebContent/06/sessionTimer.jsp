<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>06/sessionTimer.jsp</title>
<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/custom.js"></script>
<script type="text/javascript">
   $(function(){ 
/*       $('#messageArea').hide(); */ 
        
      let element = $('#timerArea').sessionTimer({
         timeout: <%= session.getMaxInactiveInterval() %>, 
     	 url: "<%= request.getContextPath() %>/sessionExtend"
      }); 
       
/*       $('#noBtn').on('click', function(){  
         $('#messageArea').hide();
      });  */
       
<%--       $('#yesBtn').on('click', function(){
         $.ajax({
            url : '<%= request.getContextPath() %>/sessionExtend',
            method: 'head',
            dataType : 'json',
            success : function(res) {
               clearInterval(interval);
               element = $('#timerArea').sessionTimer({
                  timeout: <%= session.getMaxInactiveInterval() %>,
                  url: "<%= request.getContextPath() %>/sessionExtend"
               });
               $('#messageArea').hide();
            },
            error : function(xhr) {
               alert(xhr.status);
            }
         });
      }); --%>
      
   });
</script>
<style>
@import url('https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@300&display=swap');
body{
   font-family: 'Noto Sans KR', sans-serif;
   padding: 50px;
}
pre{
   font-family: 'Noto Sans KR', sans-serif;
   border: 2px dashed lightblue;
}
input[type=button]{
	margin-right: 20px;
}
</style>
</head>
<body>
   <h4>세션 타이머</h4>
   <%= session.getId() %>: <%= session.getMaxInactiveInterval() %>
   <h1 id="timerArea"></h1>
   <pre> 
   1. 1초마다 출력되는 시간을 디스카운트
   2. 1분 남은 시점에 메시지 출력
      - 세션 연장 여부 확인
   3. 세션 연장을 선택한 경우,
      1) 타이머 리셋
      2) 세션을 연장하기 위한 새로운 요청 발생(비동기 - /sessionExtend, body가 없는 응답)
   </pre>
<!--    <div id="messageArea">
   세션을 연장하겠습니까?<br>
      <input type="button" id="yesBtn" value="예">
      <input type="button" id="noBtn" value="아니오">
   </div> -->
<%-- <script>
   
   
   let timer=$('#timerArea');
   let min = 0;
   let sec = 2;
   

   setInterval(function(){
      --sec
      if(sec < 0){
         sec = 59;
         min -= 1;
      }

      timer.html(min + ":" + sec.toString().padStart(2, '0'));
      
      if(min == 1 && sec == 0){
         $('#messageArea').show();
      }
      
      if(min < 0 && sec < 0){
         
      }
   }, 1000);

   $('#noBtn').on('click', function(){
      $('#messageArea').hide();
   });
   
   $('#yesBtn').on('click', function(){
      min = 1;
      sec = 2;
      $.ajax({
         url : '<%= request.getContextPath() %>/sessionExtend',
         dataType : 'json',
         success : function(res) {
            
         },
         error : function(xhr) {
            alert(xhr.status);
         }
      });
   });
   
</script> --%>
</body>
</html>