<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page session="true"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Home</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/views/bootstrap-4.0.0-dist/css/bootstrap.min.css">
</head>
<body>
	<div>
		<div>Welcome ${sessionScope.loggedUser.firstName}
			${sessionScope.loggedUser.lastName}</div>
	</div>

	<form action="api/pincode" name="pincodeForm" method="get">
	<input type="hidden" name="jwtToken" value="${param.jwtToken}">
		<select name="valuefor">
			<option value="0">All</option>
			<option value="1">Single</option>
		</select> 
		<input type="text" placeholder="Pincode" name="pincode">
		<input type="submit">
	</form>
	<a href="user/signup">SignUp</a>
	<!-- <a href="logout">Logout</a> -->
	<script	src="${pageContext.request.contextPath}/views/js/jquery-3.4.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/views/js/popper.min.js"></script>
	<script	src="${pageContext.request.contextPath}/views/bootstrap-4.0.0-dist/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/views/js/app.js"></script>

</body>
</html>