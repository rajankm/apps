<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>SignUp</title>
</head>
<body>
	<form action="signup" method="post" name="user">
		<input type="hidden" name="roleName" value="roleName"> 
		<input type="text" name="firstName" placeholder="First Name">
		<input type="text" name="LastName" placeholder="Last Name">
		<input type="email" name="email" placeholder="email"> 
		<input type="password" name="password" placeholder="password"> 
		<input type="submit" value="Register">
	</form>
</body>
</html>