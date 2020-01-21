<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="true"%> <%@ page isELIgnored="false"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>    
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"    
"http://www.w3.org/TR/html4/loose.dtd">    
<html lang="en">    
<head>    
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">    
<title><tiles:insertAttribute name="title" ignore="true" /></title> 
<link rel="stylesheet" href="${pageContext.request.contextPath}/views/bootstrap/3.3.7/css/bootstrap.min.css" />
<%-- <link rel="stylesheet" href="${pageContext.request.contextPath}/views/font-awesome/css/fontawesome.min.css" /> --%>
 <link rel = "stylesheet" href = "https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.3.0/css/font-awesome.min.css">
<link href="${pageContext.request.contextPath}/views/css/app.css" rel="stylesheet" id="modal-css">
</head>    
<body>    
    <div>
    	<tiles:insertAttribute name="header" />
    </div>  
    	<div class="container">
			<div class="row">  
				<div style="float:left;padding:10px;width:100%;">    
       			 	<tiles:insertAttribute name="body" />
       			 </div> 
        	</div>
        </div>
     <div id="modal">
	 </div>   
     <div style="clear:both">
     	<tiles:insertAttribute name="footer" />
     </div>    
	    	
    <script	src="${pageContext.request.contextPath}/views/js/jquery-3.4.1.min.js"></script>
	<script	src="${pageContext.request.contextPath}/views/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/views/js/app.js" type="text/javascript"></script>
</body>    
</html>    