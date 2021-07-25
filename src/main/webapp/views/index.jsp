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
		<div class="rounded shadow login-form login-form-padding">
			<div class="h1 text-center">致富寶典</div>
			<div>
				<div class="h4 mt-3">帳號</div>
				<div>
					<input type="text" class="form-control" id="userAcc" name="userAcc" placeholder="帳號、E-Mail或手機號碼">
				</div>
				<div class="h4 mt-3">密碼</div>
				<div>
					<input type="password" class="form-control" id="userPwd" name="userPwd" placeholder="請輸入密碼">
				</div>
				<div class="text-center mt-5 mb-4">
					<button type="button" class="btn btn-success btn-lg" id="loginBtn" name="loginBtn">登入</button>
				</div>
				<hr>
				<div class="text-center mt-4">
					<button type="button" class="btn btn-primary btn-lg" id="guestBtn" name="guestBtn">訪客登入</button>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="/views/include/footer.jsp"></jsp:include>
</body>
</html>