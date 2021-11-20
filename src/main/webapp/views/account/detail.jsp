<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/views/include/header.jsp"></jsp:include>
	<link rel="stylesheet" href="/css/account/detail.css">
</head>
<body>
	<jsp:include page="/views/include/menuBar.jsp"></jsp:include>
	<div class="container-fluid pb-5 mb-5">
		<div class="container-xl">
			<h3 class="text-center">帳戶明細</h3>
			<div class="row">
				<div class="col">帳戶名稱</div>
				<div class="col">帳戶類型</div>
			</div>
			<div class="row mb-2">
				<div class="col">
					<input type="text" class="form-control" id="accountName" name="accountName" readonly>
				</div>
				<div class="col">
					<input type="text" class="form-control" id="accountTypeName" name="accountTypeName" readonly>
				</div>
			</div>
			<div class="row">
				<div class="col">目前額度</div>
			</div>
			<div class="row mb-2">
				<div class="col">
					<input type="text" class="form-control" id="accountAmnt" name="accountAmnt" readonly>
				</div>
			</div>
			<input type="hidden" id="accountId" name="accountId" value="${accountId}">
			<table id="table" class="cell-border" style="width: 100%"></table>
			<div class="row border-top py-3">
				<div class="col text-start">
					<button type="button" class="btn btn-outline-danger btn-lg mx-2" id="cancelBtn" name="cancelBtn">返回</button>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="/views/include/footer.jsp"></jsp:include>
	<script type="text/javascript" src="/js/account/detail.js"></script>
</body>
</html>