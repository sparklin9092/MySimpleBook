<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/views/include/header.jsp"></jsp:include>
	<link rel="stylesheet" href="/css/account/types/modify.css">
</head>
<body>
	<jsp:include page="/views/include/menuBar.jsp"></jsp:include>
	<div class="container-fluid pb-5 mb-5">
		<div class="container-xl">
			<h3 class="text-center">
				修改帳戶類型
			</h3>
			<div class="row">
				<div class="col">類型名稱</div>
			</div>
			<div class="row mb-2">
				<div class="col">
					<input type="text" class="form-control" id="accountTypeName" name="accountTypeName"placeholder="請輸入類型名稱">
				</div>
			</div>
			<div class="row">
				<div class="col">類型狀態</div>
			</div>
			<div class="row mb-2">
				<div class="col">
					<div class="d-grid gap-2">
						<input type="radio" class="btn-check" name="accountTypeActive" id="activeFalse" value="0">
						<label class="btn btn-outline-info" for="activeFalse">停用</label>
					</div>
				</div>
				<div class="col">
					<div class="d-grid gap-2">
						<input type="radio" class="btn-check" name="accountTypeActive" id="activeTrue" value="1">
						<label class="btn btn-outline-info" for="activeTrue">啟用</label>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col">提高排序</div>
			</div>
			<div class="row mb-2">
				<div class="col">
					<div class="d-grid gap-2">
						<input type="radio" class="btn-check" name="accountTypeDefault" id="defaultFalse" value="0">
						<label class="btn btn-outline-info" for="defaultFalse">否</label>
					</div>
				</div>
				<div class="col">
					<div class="d-grid gap-2">
						<input type="radio" class="btn-check" name="accountTypeDefault" id="defaultTrue" value="1">
						<label class="btn btn-outline-info" for="defaultTrue">是</label>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col">建立時間</div>
			</div>
			<div class="row mb-2">
				<div class="col">
					<input type="text" class="form-control" id="createDateTime" name="createDateTime" readonly>
				</div>
			</div>
			<input type="hidden" id="typeId" name="typeId" value="${typeId}">
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
	<jsp:include page="/views/include/footer.jsp"></jsp:include>
	<script type="text/javascript" src="/js/account/types/modify.js"></script>
</body>
</html>