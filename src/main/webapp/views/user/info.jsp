<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/views/include/header.jsp"></jsp:include>
	<link rel="stylesheet" href="/css/user/info.css">
	<script type="text/javascript" src="/js/user/info.js"></script>
</head>
<body>
	<jsp:include page="/views/include/menuBar.jsp"></jsp:include>
	<div class="container-fluid pb-5 mb-5">
		<div class="container-xl">
			<h3 class="text-center">基本資料</h3>
			<div class="row">
				<div class="col">名稱</div>
			</div>
			<div class="row my-2">
				<div class="col">
					<input type="text" class="form-control" id="userName" name="userName" placeholder="請輸入您的名稱">
				</div>
				<div class="col-1 text-end">
					<button type="button" class="btn btn-warning" id="changeName" name="changeName">檢查</button>
				</div>
			</div>
			<div class="row">
				<div class="col">密碼</div>
			</div>
			<div class="row my-2">
				<div class="col">
					<input type="text" class="form-control" id="userPwd" name="userPwd" readonly>
				</div>
				<div class="col-1 text-end">
					<button type="button" class="btn btn-warning" id="changePwd" name="changePwd">修改</button>
				</div>
			</div>
			<div class="row">
				<div class="col">電子信箱 (E-mail)</div>
			</div>
			<div class="row my-2">
				<div class="col">
					<input type="text" class="form-control" id="userEmail" name="userEmail" placeholder="(選填) 請輸入您的電子信箱 (E-mail)">
				</div>
				<div class="col-1 text-end">
					<button type="button" class="btn btn-success" id="bindEmail" name="bindEmail">綁定</button>
				</div>
			</div>
			<div class="row">
				<div class="col">手機號碼</div>
			</div>
			<div class="row my-2">
				<div class="col">
					<input type="text" class="form-control" id="userPhone" name="userPhone" placeholder="(選填) 請輸入您的手機號碼">
				</div>
				<div class="col-1 text-end">
					<button type="button" class="btn btn-success" id="bindPhone" name="bindPhone">綁定</button>
				</div>
			</div>
			<div class="row">
				<div class="col">建立時間</div>
			</div>
			<div class="row my-2">
				<div class="col">
					<input type="text" class="form-control" id="createDate" name="createDate" readonly>
				</div>
			</div>
			<div class="row border-top py-3">
				<div class="col text-start">
					<button type="button" class="btn btn-outline-danger btn-lg mx-2" id="deleteBtn" name="deleteBtn">刪除帳號</button>
				</div>
				<div class="col text-end">
					<button type="button" class="btn btn-outline-primary btn-lg mx-2" id="confirmBtn" name="confirmBtn">修改基本資料</button>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="/views/include/footer.jsp"></jsp:include>
</body>
</html>