<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.time.ZoneId"%>
<%@page import="java.util.TimeZone"%>
<%@page import="java.util.Locale"%>
<%@page import="java.text.DateFormatSymbols"%>
<%@page import="java.util.Calendar"%>
<%@page import="static java.util.Calendar.*"%>  
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
	
	String yearStr = request.getParameter("year");
	String monthStr = request.getParameter("month");
	
	Locale locale = request.getLocale(); //클라이언트쪽의 우선순위 가장 높은 locale정보
	String language = request.getParameter("language");
	if(language!=null){
		locale  = Locale.forLanguageTag(language); 
	} 
	 
	DateFormatSymbols dfs = DateFormatSymbols.getInstance(locale);
	
	Calendar cal = getInstance();
	 
	/*서버현재시각 */ 
 	Calendar today = getInstance(); 
	String timezoneId = today.getTimeZone().getID();
	String paramzoneId = request.getParameter("timezone");
	if(paramzoneId!=null){    
		timezoneId = paramzoneId;
	}  
	TimeZone time=TimeZone.getTimeZone(timezoneId);  
	today.setTimeZone(time);  
	
	int tyear = today.get(YEAR);
	int tmonth = today.get(MONTH);  
	int tdate = today.get(DAY_OF_MONTH);
	  
	 
	if(yearStr!=null && yearStr.matches("\\d{4}")){   
		cal.set(YEAR, Integer.parseInt(yearStr));
	} 
	if(monthStr!=null && monthStr.matches("\\d{1,2}")){
		cal.set(MONTH,Integer.parseInt(monthStr));
	}
  

	
	int year = cal.get(YEAR); 
	int month = cal.get(MONTH);
	
	cal.set(DAY_OF_MONTH, 1);
	int offset = cal.get(DAY_OF_WEEK) - 1;
	int lastDate = cal.getActualMaximum(DAY_OF_MONTH);
	
	cal.add(MONTH, -1); 
	int beforeYear = cal.get(YEAR);
	int beforeMonth = cal.get(MONTH); 
	
	cal.add(MONTH, 2);
	int nextYear = cal.get(YEAR);
	int nextMonth = cal.get(MONTH); 
	
	cal.add(MONTH, -1);
	


	
%>
<!-- <!DOCTYPE html>
<html> 
<head>
<meta charset="UTF-8">
<title>04/calendar.jsp</title> -->
<!-- <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script> -->
<style type="text/css">

/* div{

	margin: 50px;  
} */ 
/* h4{ 
	display : inline-block; 
	font-size: 14px;
	margin : 5px 0;  
} 
a{ 
	text-decoration: none;
	color : grey;  
	font-size: 12px;
	margin-left: 8px;
	margin-right: 8px;
}
a:hover{ 
	color : purple;
} */
td{
	text-align: center;
	font-size : 15px;
	width: 100px;  
	height: 80px;   
}   
thead{
	text-align: center;
}
/* input[type=number]{    
	width: 70px;  
	height: 10px;
	margin-left: 10px; 
	font-size: 12px;
} 
select{
	font-size: 12px;
}
thead{ 
	font-size: 12px;
}
*/
.sunday{
	color : red;
}
.saturday{
	color : blue;
}
.current{ 
	color : white;
	background: green; 
}  
</style>  
<!-- </head>    -->
<!-- <body> -->
<%-- <%=Calendar.SUNDAY %>  
<%=Calendar.MONDAY %> --%> 
<%-- <%=String.format("%tc", cal) %> --%>    
<div>    
<h4> <%=String.format(locale, "%tc", today) %></h4><br>
<h4>  
<a href="#" class="moveA" data-year="<%=beforeYear%>" data-month="<%=beforeMonth%>">이전달</a> 
<%=String.format(locale,"%1$tY. %1$tB", cal) %>  <!-- 1$를 쓰면 포맷에 넣을 값이 하나여도 됨 -->
<a href="#" class="moveA" data-year="<%=nextYear%>" data-month="<%=nextMonth%>">다음달</a>
</h4>    
<form id="calendarForm">  
	<input type="hidden" name="service" value="CALENDAR">
	<input type="number" name="" placeholder=" <%=year%>" value="<%=year%>">
	<select name="month">
	<option value>월 선택</option> 
		<%
			String optionPtrn = "<option %s value='%s'>%s</option>";
			String[] months = dfs.getMonths();
			for(int tmp=0; tmp<=11; tmp++){ 
				String selected = tmp==month?"selected":""; 
				out.println(   
						String.format(optionPtrn, selected,  tmp, months[tmp])
						); 
			}
		%>
	</select> 
	<select name="language">
		<%   
			Locale[] locales = Locale.getAvailableLocales(); 
			for(Locale tmpLoc : locales){
				String tag = tmpLoc.toLanguageTag(); //'hr-HR'
				String name = tmpLoc.getDisplayName(); //크로아티아어 (크로아티아)
				if(name.isEmpty()) continue;  
				String selected = tmpLoc.equals(locale)? "selected" : "";
				out.println(  
						String.format(optionPtrn, selected, tag, name)
						);
			}
		%>
	</select> 
	<select name="timezone">
		<%
		 	 
			String[] timezones = TimeZone.getAvailableIDs(); 
			for(String tmpTime : timezones){   
				String selected = tmpTime.equals(timezoneId)? "selected" : ""; 
				out.println(  
						String.format(optionPtrn, selected, tmpTime, tmpTime)
						);
			}
		%>
	</select>
</form> <br>
<table class="table table-bordered">  
	<thead>   
		<tr>
			<%
				String[] weekDays = dfs.getWeekdays();
				String thPtrn = "<th>%s</th>";
				for(int idx= SUNDAY; idx<= SATURDAY; idx++){
					out.println(
							String.format(thPtrn, weekDays[idx])
							);
				}
			%>
		</tr>
	</thead>
	<tbody>
		<%
			String pattern = "<td class='%s %s'>%s</td>";
			int number = 1;
			for(int row=1; row<=6; row++){
				out.println("<tr>");  
				for(int col=SUNDAY; col<=SATURDAY; col++){  
					int dateNumber = number++ - offset; 
					String currentclass = (dateNumber==tdate && year==tyear && month==tmonth) ? "current" : ""; 
					String printNumber = dateNumber >= 1 && dateNumber <= lastDate ? dateNumber+"" : "&nbsp;";
					String dayclass = col==SUNDAY? "sunday" : (col==SATURDAY? "saturday" : "");
					out.println(String.format(pattern,currentclass, dayclass, printNumber)); 
 
				}
				out.println("</tr>"); 
			}
		%>
	</tbody>
</table>
</div>  

<script type="text/javascript">
	let calForm = $("#calendarForm").on("change", ":input",function(){
		console.log(this.form);  //html element자체
		console.log(calForm); 	//element찾은 후 해당 객체를 jquery 형태로 바꿈
		console.log(calForm[0]); 
		this.form.submit(); //submit이벤트 발생안함
//		$("#calendarForm").submit();
//		calForm.submit(); //submit이벤트 발생
	}).on("submit", function(){ 
		console.log("=====================");
		return true;    
	});  
	 
	$(".moveA").on("click", function(event){
		event.preventDefault(); 
		let year = $(this).data("year"); 
		let month = $(this).data("month");
		calForm.find("input[name='year']").val(year);
		$(calForm.get(0).month).val(month); // calForm.get(0) = calForm[0]
		calForm.submit();
		return false;
	}); 
</script>
<!-- </body> -->
<!-- </html> -->