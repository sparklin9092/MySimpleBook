<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/views/include/header.jsp"></jsp:include>
	<link rel="stylesheet" href="/css/account/accountCreate.css">
	<script type="text/javascript" src="/js/account/accountCreditCardLimitYearMonth.js"></script>
	<script type="text/javascript" src="/js/account/accountCreate.js"></script>
	<script type="text/javascript" src="/js/common/userCheck.js"></script>
	<script type="text/javascript" src="/js/common/checkAmnt.js"></script>
</head>
<body>
	<jsp:include page="/views/include/menuBar.jsp"></jsp:include>
	<div class="container-fluid pb-5 mb-5">
		<div class="container-xl">
			<h3 class="text-center">新增帳戶</h3>
			<div class="row">
				<div class="col">帳戶類型</div>
			</div>
			<div class="row mb-2">
				<div class="col">
					<select class="form-select" id="accountTypeSelect" name="accountTypeSelect"></select>
				</div>
			</div>
			<div class="row">
				<div class="col">帳戶名稱</div>
			</div>
			<div class="row mb-2">
				<div class="col">
					<input type="text" class="form-control" id="accountName" name="accountName" placeholder="請輸入帳戶名稱">
				</div>
			</div>
			<div class="row">
				<div class="col">初始額度</div>
			</div>
			<div class="row mb-2">
				<div class="col">
					<input type="number" class="form-control" id="initAmnt" name="initAmnt" placeholder="請輸入初始額度">
				</div>
			</div>
			<div class="row">
				<div class="col">預設帳戶</div>
			</div>
			<div class="row mb-2">
				<div class="col">
					<div class="d-grid gap-2">
						<input type="radio" class="btn-check" name="accountDefault" id="defaultFalse" value="0" checked>
						<label class="btn btn-outline-info" for="defaultFalse">否</label>
					</div>
				</div>
				<div class="col">
					<div class="d-grid gap-2">
						<input type="radio" class="btn-check" name="accountDefault" id="defaultTrue" value="1">
						<label class="btn btn-outline-info" for="defaultTrue">是</label>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col">
					<input type="checkbox" class="form-check-input" id="enableLimitDate" name="enableLimitDate">
					<label for="enableLimitDate">使用期限（建議信用卡可以開啟這個功能，系統會幫助您自動停用卡片）</label>
				</div>
			</div>
			<div class="row mb-2">
				<div class="col">
					<select class="form-select" id="limitYear" name="limitYear" disabled="disabled"></select>
				</div>
				<div class="col">
					<select class="form-select" id="limitMonth" name="limitMonth" disabled="disabled"></select>
				</div>
			</div>
			<div class="row border-top py-3">
				<div class="col text-start">
					<button type="button" class="btn btn-outline-danger btn-lg mx-2" id="cancelBtn" name="cancelBtn">取消</button>
				</div>
				<div class="col text-end">
					<button type="button" class="btn btn-outline-success btn-lg mx-2" id="confirmBtn" name="confirmBtn">確定</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>