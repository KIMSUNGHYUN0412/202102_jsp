<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<jsp:include page="/includee/preScript.jsp" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/js/fancytree/skin-win8/ui.fancytree.min.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/resources/js/fancytree/jquery.fancytree-all-deps.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/base.css">
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/table.css">
</head>
<body>
<div class="row">
	<div id="tree" class="col-3"></div>
	<div id="detail" class="ml-2 col-4">
		<form id="empModifyForm" action="${pageContext.request.contextPath }/employee/updateEmp.do" method="post">
		<table>
			<tr>
				<th>직원번호</th>
				<td class="empno">
					<input type="text" name="empno" class="form-control" readonly/>
				</td>
			</tr>
			<tr>
				<th>직원명</th>
				<td class="ename">
					<input type="text" name="ename" class="form-control" disabled/>
				</td>
			</tr>
			<tr>
				<th>부서</th>
				<td class="deptno">
					<select name="deptno" class="form-control">
						<option value>-- 부서 --</option>
						<c:forEach items="${deptList}" var="deptMap">
								<option value="${deptMap['deptno']}">
									${deptMap['dname']}
								</option>	 
						</c:forEach> 
					</select>
				</td>
			</tr> 
			
			<tr>
				<th>입사일</th>
				<td class="hiredate">
					<input type="text" name="hiredate" class="form-control" disabled   />
				</td>
			</tr>
			<tr>
				<th>업무</th>
				<td class="job">
					<input type="text" name="job" class="form-control"   />
				</td>
			</tr>
			<tr>
				<th>급여</th>
				<td class="sal">
					<input type="text" name="sal" class="form-control"   />
				</td>
			</tr>
			<tr>
				<th>인센티브</th>
				<td class="comm">
					<input type="text" name="comm" class="form-control"   />
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<div class="d-flex justify-content-center">
					<input type="submit" value="수정" class="btn btn-primary mr-2" disabled/>
					<input type="button" value="삭제" class="btn btn-danger" disabled id="delBtn" />
<!-- 					<input type="button" value="등록" class="btn btn-info mr-2" disabled/> -->
					</div>
				</td>
			</tr>
		</table>
		</form>
	</div>
</div>
<form  id="empRemoveForm" action="${pageContext.request.contextPath }/employee/deleteEmp.do" method="post">
	<input type="hidden" name="empno"  />
</form>
<script type="text/javascript">
// 비동기 요청 에러 메시지 처리========================================================================
	function makeAlert(message){
		let alertDiv = 
			$("<div>")
				.addClass("alert alert-danger alert-dismissible fade show")
				.attr({
					id:"alertArea",
					role:"alert"
				}).append(
					message,
					$("<button>")
						.addClass("close")
						.html($("<span>").html("&times;"))
						.on("click", function(){
							$(this).parents("div.alert").alert('close');
						})
				);
		return alertDiv;
	}
	$(document).ajaxError(function( event, jqxhr, settings, thrownError ){
		let message = "url : %u, status : %s\n%r"
						.replace('%u', settings.url)
						.replace('%s', jqxhr.status)
						.replace('%r', jqxhr.responseText);
		$("#alertArea").alert('close');
		$("body").append(makeAlert(message));
	});
// ========================================================================================	
// 수정/삭제==================================================================================	
	function makeData(form){
		let data = {} 
		let formData = new FormData(form);
		let names = new Set(formData.keys());
		for(let name of names){ 
			let values = formData.getAll(name)
								 .flatMap(value>=value.trim())
								 .filter(value>=value);
			if(values)
				data[name] = values.length>1?values:values[0];
			
		}
		return data;
	}
	let empModifyForm = $("#empModifyForm").on("submit", function(event){
		event.preventDefault();
		let data = makeData(this);
		let options = {
			url:this.action,
			method:this.method,
			contentType:"application/json;charset=utf-8",
			dataType:"json",
			data:JSON.stringify(data),
			success:function(resp){
				if(resp.success){
					// 수정 성공
					jQuery.ui.fancytree.getTree("#tree").reload();
					empModifyForm.get(0).reset();
					empRemoveForm.get(0).reset();
				}
			}
		}		
		$.ajax(options);
		return false;
	});
	let empRemoveForm = $("#empRemoveForm").on("submit", function(event){
		event.preventDefault();
		let options = {
			url:this.action,
			method:this.method,
			dataType:"json",
			data:$(this).serialize(),
			success:function(resp){
				if(resp.success){
					// 삭제 성공
					jQuery.ui.fancytree.getTree("#tree").reload();
					empModifyForm.get(0).reset();
					empRemoveForm.get(0).reset();
				}
			}
		}		
		$.ajax(options);
		return false;
	});
	$("#delBtn").on("click", function(){
		if(!confirm("삭제 확인")) return;
		empRemoveForm.get(0).empno.value = empModifyForm.get(0).empno.value;
		empRemoveForm.submit();
	});
// ========================================================================================	
	
// fancytree생성============================================================================	
	$("#tree").fancytree({
		source:{
			url:location.pathname,
			cache:true
		},
		lazyLoad: function(event, data){
		      var node = data.node;
		      data.result = {
		    	url: location.pathname,
		        data: {mgr:node.key},
		        cache: false
		      };
		},
		activate: function(event, data){
			$("#alertArea").alert('close');
		    var node = data.node;
		    var employee = node.data.adaptee;
		    for(let prop in employee){
		    	let input = empModifyForm.get(0)[prop];
		    	if(input){
		    		$(input).val(employee[prop]);
		    	}
		    }
		    empModifyForm.find("[type='submit']").prop("disabled", false);
		    empModifyForm.find("#delBtn").prop("disabled", !employee.leaf);
	    },
	    beforeSelect: function(event, data){
	    	console.log("before"); 
	        // A node is about to be selected: prevent this for folders:
	        if( data.node.isFolder() ){
	          return false;
	        }
      	}
	}); 
</script>
<jsp:include page="/includee/footer.jsp" />
</body>
</html>
