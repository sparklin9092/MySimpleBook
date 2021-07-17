<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="include/header.jsp"></jsp:include>
	<link rel="stylesheet" href="/css/itemAccountTypeCreate.css">
	<script type="text/javascript" src="/js/itemAccountTypeCreate.js"></script>
	<script type="text/javascript" src="/js/userCheck.js"></script>
</head>
<body>
	<jsp:include page="include/menuBar.jsp"></jsp:include>
	<div class="container-fluid">
		<div class="container-xl">
			<h3 class="text-center">
				新增帳戶類型
			</h3>
			<div class="row">
				<div class="col">類型名稱</div>
			</div>
			<div class="row mb-2">
				<div class="col">
					<input type="text" class="form-control" id="itemAccountTypeName" name="itemAccountTypeName">
				</div>
			</div>
			<div class="row">
				<div class="col">預設類型</div>
			</div>
			<div class="row mb-2">
				<div class="col">
					<div class="d-grid gap-2">
						<input type="radio" class="btn-check" name="itemAccountTypeDefault" id="defaultFalse" value="0" checked>
						<label class="btn btn-outline-info" for="defaultFalse">否</label>
					</div>
				</div>
				<div class="col">
					<div class="d-grid gap-2">
						<input type="radio" class="btn-check" name="itemAccountTypeDefault" id="defaultTrue" value="1">
						<label class="btn btn-outline-info" for="defaultTrue">是</label>
					</div>
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