<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="include/header.jsp"></jsp:include>
	<link rel="stylesheet" href="/css/itemIncomeModify.css">
	<script type="text/javascript" src="/js/itemIncomeModify.js"></script>
	<script type="text/javascript" src="/js/userCheck.js"></script>
</head>
<body>
	<jsp:include page="include/menuBar.jsp"></jsp:include>
	<div class="container-fluid">
		<div class="container-xl">
			<h3 class="text-center">
				修改收入項目
			</h3>
			<div class="row">
				<div class="col">項目名稱</div>
			</div>
			<div class="row mb-2">
				<div class="col">
					<input type="text" class="form-control" id="itemIncomeName" name="itemIncomeName">
				</div>
			</div>
			<div class="row">
				<div class="col">項目狀態</div>
			</div>
			<div class="row mb-2">
				<div class="col">
					<div class="d-grid gap-2">
						<input type="radio" class="btn-check" name="itemIncomeActive" id="activeFalse" value="0">
						<label class="btn btn-outline-info" for="activeFalse">停用</label>
					</div>
				</div>
				<div class="col">
					<div class="d-grid gap-2">
						<input type="radio" class="btn-check" name="itemIncomeActive" id="activeTrue" value="1">
						<label class="btn btn-outline-info" for="activeTrue">啟用</label>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col">預設項目</div>
			</div>
			<div class="row mb-2">
				<div class="col">
					<div class="d-grid gap-2">
						<input type="radio" class="btn-check" name="itemIncomeDefault" id="defaultFalse" value="0">
						<label class="btn btn-outline-info" for="defaultFalse">否</label>
					</div>
				</div>
				<div class="col">
					<div class="d-grid gap-2">
						<input type="radio" class="btn-check" name="itemIncomeDefault" id="defaultTrue" value="1">
						<label class="btn btn-outline-info" for="defaultTrue">是</label>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col">項目建立人員</div>
			</div>
			<div class="row mb-2">
				<div class="col">
					<input type="text" class="form-control" id="createUserName" name="createUserName" disabled="disabled">
				</div>
			</div>
			<div class="row">
				<div class="col">項目建立時間</div>
			</div>
			<div class="row mb-2">
				<div class="col">
					<input type="text" class="form-control" id="createDateTime" name="createDateTime" disabled="disabled">
				</div>
			</div>
			<input type="hidden" id="itemId" name="itemId" value="${itemId}">
			<div class="row border-top py-3">
				<div class="col text-start">
					<button type="button" class="btn btn-outline-danger btn-lg mx-2" id="cancelBtn" name="cancelBtn">取消</button>
				</div>
				<div class="col text-center">
					<button type="button" class="btn btn-outline-warning btn-lg mx-2" id="deleteBtn" name="deleteBtn">刪除</button>
				</div>
				<div class="col text-end">
					<button type="button" class="btn btn-outline-success btn-lg mx-2" id="confirmBtn" name="confirmBtn">確定</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>