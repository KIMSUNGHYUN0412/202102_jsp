<%@page import="java.io.File"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">  
<title>WEB-INF/views/serverBrowser.jsp</title>
<jsp:include page="/includee/preScript.jsp"/>
<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/js/fancytree/skin-win8/ui.fancytree.min.css">
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/fancytree/jquery.fancytree-all-deps.min.js"></script>
</head>
<body>
<div id="tree"></div>
<script> 
	$("#tree").fancytree({ 
		  source: {
			    url: location.pathname, 
			    cache: false 
			  },
			  lazyLoad: function(event, data){
			      var node = data.node;
			      // Load child nodes via Ajax GET /getTreeData?mode=children&parent=1234
			      data.result = {
			    	url: location.pathname, 
			        data: {base : node.key},
			        cache: false
			      };
			  }  
	});
</script>
</body>
</html>