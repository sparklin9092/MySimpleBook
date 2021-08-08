<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/views/include/header.jsp"></jsp:include>
	<link rel="stylesheet" href="/css/index.css">
</head>
<body>
	<div class="login-box">
		<div class="rounded shadow login-form login-form-padding">
			<h1 class="text-center">致富寶典</h1>
			<div>
				<div class="h4 mt-3">使用者</div>
				<div>
					<input type="text" class="form-control" id="userAcc" name="userAcc" placeholder="帳號、電子信箱">
					<!-- <input type="text" class="form-control" id="userAcc" name="userAcc" placeholder="帳號、電子信箱、手機號碼"> -->
				</div>
				<div class="text-end">
					<input type="checkbox" id="rememberAcc" name="rememberAcc" checked>
					<label for="rememberAcc">記住帳號</label>
				</div>
				<div class="h4 mt-3">密碼</div>
				<div>
					<input type="password" class="form-control" id="userPwd" name="userPwd" placeholder="請輸入密碼">
				</div>
				<div class="text-center mt-4 mb-4">
					<button type="button" class="btn btn-success btn-lg" id="loginBtn" name="loginBtn">登入</button>
				</div>
				<hr>
				<div class="text-center mt-4">
					<button type="button" class="btn btn-primary btn-lg" id="guestBtn" name="guestBtn">訪客試用</button>
				</div>
			</div>
		</div>
	</div>
	<input type="hidden" id="user" name="user" value="${user}">
	<jsp:include page="/views/include/footer.jsp"></jsp:include>
	<script type="text/javascript" src="/js/index.js"></script>
</body>
</html>