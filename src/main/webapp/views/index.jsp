<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/views/include/header.jsp"></jsp:include>
	<link rel="stylesheet" href="/css/index.css">
	<script type="text/javascript" src="/js/index.js"></script>
</head>
<body>
	<div class="login-box">
		<form class="rounded shadow login-form login-form-padding">
			<div class="h1 text-center">我的記帳本</div>
			<div>
				<div class="h4 mt-3">帳號</div>
				<div>
					<input type="text" class="form-control" id="userName" name="userName" placeholder="帳號、E-Mail或手機號碼">
				</div>
				<div class="h4 mt-3">密碼</div>
				<div>
					<input type="password" class="form-control" id="userPwd" name="userPwd" placeholder="請輸入密碼">
				</div>
				<div class="text-center mt-5">
					<button type="button" class="btn btn-primary" id="loginBtn" name="loginBtn">登入</button>
				</div>
			</div>
		</form>
	</div>
</body>
</html>