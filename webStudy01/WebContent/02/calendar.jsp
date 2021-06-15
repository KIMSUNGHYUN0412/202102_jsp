<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%
     // 오늘 날짜로 달력 취득
     Calendar calendar = Calendar.getInstance();   
     Calendar today = Calendar.getInstance();

      // request 객체로 부터 넘어온 값이 있으면 처리
     if(request.getParameter("year") != null){
        calendar.set(Calendar.YEAR, Integer.parseInt(request.getParameter("year")));
     }
     if(request.getParameter("month") != null){
        calendar.set(Calendar.MONTH, Integer.parseInt(request.getParameter("month")));
     }
      
     // 날을 1일로 셋팅
     calendar.set(Calendar.DATE, 1);
      
     
     // 1일의 '요일'을 취득
     int oneDayNum = calendar.get(Calendar.DAY_OF_WEEK);
     // 현재달의 최대 일 수
     int monthMaxNum = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
     // 현재달의 주 수
     int weekSize = calendar.getActualMaximum(Calendar.WEEK_OF_MONTH);
     // 현재 '년'을 취득
     int year = calendar.get(Calendar.YEAR);
     // 현재 '월'을 취득(0월 부터 11월 까지)
     int month = calendar.get(Calendar.MONTH);
     // 현재 '일'을 취득
     int day = calendar.get(Calendar.DATE);
     // 현재달의 '마지막일'을 취득 
     int lastDay = calendar.getActualMaximum(Calendar.DATE);
   
     //오늘 년 
     int tyear = today.get(Calendar.YEAR);
     //오늘 월
     int tmonth = today.get(Calendar.MONTH);
     //오늘 일
     int tday = today.get(Calendar.DATE);
%> 
<meta charset="UTF-8">
<title>02/calendar.jsp</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<link  rel="stylesheet" href="../css/calendar.css">
<script type="text/javascript">
   $(function(){
      $('#div1').fadeIn();
      
      $('#today').css('background','#D6A1F8').css('border-color', 'white');
      
      
      $('.t2').click(function(){
         $('.t2').css('border-color', 'transparent');
         $(this).css('border-color','#D6A1F8');
      });
      
      $('#today').click(function(){
         $('.t2').css('border-color', 'transparent');
         $(this).css('border-color','white');
      });
   
      $('#top1').click(function(){
         year = <%= year %>;
         code = '<span id="y1">' + year + '</span>년';
         $(this).html(code);
         $('#div1').hide();
         $('#div3').hide();
         $('#div2').fadeIn();
         $(this).attr('id', 'top2'); 
         
         t4yeart = $('#top2 #y1').text();
         t2month = '#<%=tmonth+1%>';  
         if(t4yeart=='<%=tyear%>'){  
   	      $(t2month).css('background','#D6A1F8'); 
         } 
      });
         
      
      $('.t3').click(function(){
         t3year = $('#y1').html();
         t3month = $(this).html()-1;
         location.href = "calendar.jsp?year=" + t3year + "&month=" + t3month;
      });
      
      
      $('#before1').on('click', function(){ 
    	  $('#table2').hide();
          nyear = $('#top2 #y1').html();
          $('#top2 #y1').html(nyear-1); 
          
          t4yeart = $('#top2 #y1').text();
          t2month = '#<%=tmonth+1%>';  
          if(t4yeart=='<%=tyear%>'){  
    	      $(t2month).css('background','#D6A1F8'); 
          }else{
        	  $(t2month).css('background','none'); 
          }
          $('#table2').fadeIn();
       });
      
      $('#after1').on('click', function(){ 
    	  $('#table2').hide();
          nyear = $('#top2 #y1').html();
          nyear = parseInt(nyear);
          $('#top2 #y1').html(nyear+1); 
          
          t4yeart = $('#top2 #y1').text();
          t2month = '#<%=tmonth+1%>';  
          if(t4yeart=='<%=tyear%>'){  
    	      $(t2month).css('background','#D6A1F8'); 
          }else{
        	  $(t2month).css('background','none'); 
          }
          $('#table2').fadeIn(); 
       });
      
<%
      int nth = year / 20;
      int start = 0;
      int end = 0;
      for(int i = year - 100; i <= year + 100; i++){
         if(nth == (i / 20)){
            if(i % 20 == 0){
               start = i;
            }else if(i % 20 == 19){
               end = i;
            }
         }
      }
%>
      var start = <%= start %>;
      var end = <%= end %>;
      
      $(document).on('click', '#top2, #y1', function(){
          console.log(start);
          console.log(end);
          code = "<span id='start'>" + start + "</span> - <span id='end'>" + end + "</span>";
          $('#div1').hide();
          $('#div2').hide(); 
          $('#div3').fadeIn();
          $('#top2').attr('id','top3').html(code).css('pointer-events', 'none');
          
          	t4yearid = '#'+<%=tyear%>;  
         	t4yeart2 = $('#table3 '+t4yearid).text();
         	if(t4yeart2=='<%=tyear%>'){   
    		      $(t4yearid).css('background','#D6A1F8'); 
       	   	}else{
        	 	 $(t4yearid).css('background','none'); 
        	}
          
       });
      
      $('#before').on('click', function(){
         $('#table3').hide();
         if(start > year - 100){
            start = start - 20;
            end = end - 20;
            $('#after').css('pointer-events', 'auto');
         }else{
            $(this).css('pointer-events', 'none');
         }
         var num = 0;
         for(var i = 0; i < 20; i++){
            num = parseInt($(".t4:eq("+ i +")").html());
            num = $(".t4:eq(" + i + ")").html(num - 20);
         }
         $('#start').html($('.t4:eq(0)').html());
         $('#end').html($('.t4:eq(19)').html());
         $('#table3').fadeIn();
         
         
       	t4yearid = '#'+<%=tyear%>;  
      	t4yeart2 = $('#table3 '+t4yearid).text();
      	if(t4yeart2=='<%=tyear%>'){   
 		      $(t4yearid).css('background','#D6A1F8'); 
    	   	}else{
     	 	 $(t4yearid).css('background','none'); 
     	}
      });
      
      $('#after').on('click', function(){
         $('#table3').hide();
         if(end < year + 100){
            start = start + 20;
            end = end + 20;
            $('#before').css('pointer-events', 'auto');
         }else{
            $(this).css('pointer-events', 'none');
         }
         var num = 0;
         for(var i = 0; i < 20; i++){
            num = parseInt($(".t4:eq("+ i +")").html());
            num = $(".t4:eq(" + i + ")").html(num + 20);
         }
         $('#start').html($('.t4:eq(0)').html());
         $('#end').html($('.t4:eq(19)').html());
         $('#table3').fadeIn();
         
         
       	t4yearid = '#'+<%=tyear%>;  
      	t4yeart2 = $('#table3 '+t4yearid).text();
      	if(t4yeart2=='<%=tyear%>'){   
 		      $(t4yearid).css('background','#D6A1F8'); 
    	   	}else{
     	 	 $(t4yearid).css('background','none'); 
     	}
      });
      
      $('.t4').click(function(){
         t4year = $(this).html();
         console.log(t4year);
         code = '<span id="y1">' + t4year + '</span>년';
         $('#top3').html(code).attr('id', 'top2').css('pointer-events', 'auto');
         $('#div3').hide();
         $('#div1').hide();
         $('#div2').fadeIn(); 
          
         t4yeart = $('#top2 #y1').text();
         t2month = '#<%=tmonth+1%>';  
         if(t4yeart=='<%=tyear%>'){  
   	      	$(t2month).css('background','#D6A1F8'); 
         } else{
        	 $(t2month).css('background','none'); 
         }
      });
      

       
   });
</script> 
</head> 
<body>   
   <form>
      <h4 id="top1"> <span id="yy"><%= year %>년</span><span id="mm"> <%= month + 1 %>월</span></h4>
      <div id="div1">
         <a class="month-btn" href="calendar.jsp?year=<%= year %>&month=<%= month + 1 %>"><input type="button" value="∨" class="after"></a> 
         <a class="month-btn" href="calendar.jsp?year=<%= year %>&month=<%= month - 1 %>"><input type="button" value="∧" class="before"></a>
         <br><br> 
         <table id='table1'>
            <tr>
               <td class="t1">일</td>
               <td class="t1">월</td> 
               <td class="t1">화</td>
               <td class="t1">수</td>
               <td class="t1">목</td>
               <td class="t1">금</td>
               <td class="t1">토</td>
            </tr>
            <tr>
<% 
               for(int i = 1; i < oneDayNum; i++){
%>   
               <td></td>
<%  
               }
               for(int i = 1; i <= lastDay; i++){
                  calendar.set(Calendar.DATE, i);
                  int daynum = calendar.get(Calendar.DAY_OF_WEEK);
                  if(daynum == 1) {
%> 
            </tr>
            <tr> 
<% 
                  }

                  if(tyear==calendar.get(Calendar.YEAR)&&tmonth==calendar.get(Calendar.MONTH)&&tday==calendar.get(Calendar.DATE)){
%>
                       <td class="t2" id="today"><%= calendar.get(Calendar.DATE) %></td>
<%
                  }else{
%> 
                       <td class="t2"><%= calendar.get(Calendar.DATE) %></td>
<% 
                  } 

                  if(i == lastDay && daynum < 7){
                     for(int j = daynum + 1; j <= 7; j++){
%>
                       <td></td>
<% 
                     }
                  }
               }
%>
            </tr>
         </table>
      </div> 
      <div id="div2">
         <input type="button" value="∨" class="after" id="after1"> 
         <input type="button" value="∧"  class="before" id="before1">
         <br>
         <table id='table2'>
            <tr>
<% 
            for(int i = 1; i <= 12; i++){
              
%>
                     <td class = 't3' id="<%= i %>"><%= i %></td>
<%
               
            if(i % 4 == 0){
%>
               </tr>
<%
               }
            }
%>
         </table>
      </div>
      <div id="div3">
         <input type="button" value="∨" class="after" id="after"> 
         <input type="button" value="∧"  class="before" id="before">
         <br>
         <table id='table3'>
            <tr>
<% 
            for(int i = start; i <= end; i++) {
               if(i == tyear){ 
%>
                   <td class = 't4' id="<%= i %>"><%= i %></td>
<%
               }else{ 
%>
                     <td class = 't4'><%= i %></td>
<%
               }               
            if(i % 5 == 4){
%>
               </tr>
<%               
               }
            }
%>
         </table>
      </div>
   </form>
</body>
</html>