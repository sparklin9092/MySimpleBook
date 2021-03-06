<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/views/include/header.jsp"></jsp:include>
	<link rel="stylesheet" href="/css/user/guest.css">
</head>
<body>
	<jsp:include page="/views/include/menuBar.jsp"></jsp:include>
	<div class="container-fluid pb-5 mb-5">
		<div class="container-xl">
			<h3 class="text-center">帳號綁定</h3>
			<div class="mb-3 p-2 bg-light rounded-pill text-center">任意選擇一種方式，綁定您的帳號。</div>
			<div class="row px-2 mb-3" id="accPwdArea" name="accPwdArea">
				<div class="col form-check">
					<input class="form-check-input bind-type-size" type="radio" name="bindType" id="accPwdTitle" data-bindtype="1" checked>
					<label class="form-check-label bind-type-size" for="accPwdTitle">帳號與密碼</label>
				</div>
			</div>
			<div class="row border border-primary rounded p-3 mb-3" id="accPwd" name="accPwd">
				<div class="col">
					<div class="row">
						<div class="col">帳號</div>
					</div>
					<div class="row mb-2">
						<div class="col-xs-12 col-sm-12 col-md-11 col-lg-11 mt-2">
							<input type="text" class="form-control" id="userAcc" name="userAcc" placeholder="請輸入您的帳號">
						</div>
						<div class="col-xs-12 col-sm-12 col-md-1 col-lg-1 mt-2 text-end">
							<button type="button" class="btn btn-warning" id="checkAcc" name="checkAcc">檢查</button>
						</div>
					</div>
					<div class="row">
						<div class="col">密碼</div>
					</div>
					<div class="row mb-2">
						<div class="mt-2">
							<input type="password" class="form-control" id="userPwd" name="userPwd" placeholder="請輸入您的密碼">
						</div>
					</div>
				</div>
			</div>
			<!--
			<div class="row px-2">
				<div class="col form-check">
					<input class="form-check-input bind-type-size" type="radio" name="bindType" id="phoneTitle" data-bindtype="2">
					<label class="form-check-label bind-type-size" for="phoneTitle">手機號碼 <span class="text-danger">(*推薦使用)</span></label>
				</div>
			</div>
			<div class="row border border-primary rounded p-3 mb-3" id="phone" name="phone">
				<div class="col">
					<div class="row">
						<div class="mt-2">
							<input type="text" class="form-control" id="userPhone" name="userPhone" placeholder="請輸入您的手機號碼">
						</div>
					</div>
				</div>
			</div>
			-->
			<div class="row px-2 mb-3">
				<div class="col form-check">
					<input class="form-check-input bind-type-size" type="radio" name="bindType" id="emailTitle" data-bindtype="3">
					<label class="form-check-label bind-type-size" for="emailTitle">電子信箱 (Email)</label>
				</div>
			</div>
			<div class="row border border-primary rounded p-3 mb-3" id="email" name="email">
				<div class="col">
					<div class="row">
						<div class="mt-2">
							<input type="text" class="form-control" id="userEmail" name="userEmail" placeholder="請輸入您的 Email">
						</div>
					</div>
				</div>
			</div>
			<div class="row border-top py-3">
				<div class="col text-start">
					<button type="button" class="btn btn-outline-danger btn-lg mx-2" id="cancelBtn" name="cancelBtn">取消</button>
				</div>
				<div class="col text-end">
					<button type="button" class="btn btn-outline-success btn-lg" id="bindBtn" name="bindBtn">綁定</button>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="/views/include/footer.jsp"></jsp:include>
	<script type="text/javascript" src="/js/user/guest.js"></script>
</body>
</html>