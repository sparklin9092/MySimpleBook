<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/views/include/header.jsp"></jsp:include>
	<link rel="stylesheet" href="/css/user/pwd.css">
	<script type="text/javascript" src="/js/user/pwd.js"></script>
</head>
<body>
	<jsp:include page="/views/include/menuBar.jsp"></jsp:include>
	<div class="container-fluid pb-5 mb-5">
		<div class="container-xl">
			<h3 class="text-center">修改密碼</h3>
			<div class="row">
				<div class="col">舊密碼</div>
			</div>
			<div class="row my-2">
				<div class="col">
					<input type="password" class="form-control" id="oldPwd" name="oldPwd" placeholder="請輸入舊密碼">
				</div>
			</div>
			<div class="row">
				<div class="col">新密碼</div>
			</div>
			<div class="row my-2">
				<div class="col">
					<input type="password" class="form-control" id="newPwd" name="newPwd" placeholder="請輸入新密碼">
				</div>
			</div>
			<div class="row border-top py-3">
				<div class="col text-start">
					<button type="button" class="btn btn-outline-danger btn-lg mx-2" id="cancelBtn" name="cancelBtn">取消</button>
				</div>
				<div class="col text-end">
					<button type="button" class="btn btn-outline-primary btn-lg mx-2" id="confirmBtn" name="confirmBtn">確定</button>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="/views/include/footer.jsp"></jsp:include>
</body>
</html>