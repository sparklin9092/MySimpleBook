<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="include/header.jsp"></jsp:include>
	<link rel="stylesheet" href="/css/spend.css">
	<script type="text/javascript" src="/js/spend.js"></script>
</head>
<body>
	<jsp:include page="include/menuBar.jsp"></jsp:include>
	<div class="container-fluid">
		<div class="container-xl">
			<h3 class="text-center">新增一筆支出</h3>
			<div class="input-group mb-3">
				<span class="input-group-text">日期</span>
				<input type="text" class="form-control" id="spendDatePicker" name="spendDate">
			</div>
			<div class="input-group mb-3">
				<label class="input-group-text" for="spendItemSelect">項目</label>
				<select class="form-select" id="spendItemSelect"></select>
			</div>
			<div class="input-group mb-3">
				<label class="input-group-text" for="spendItemSelect">帳戶</label>
				<select class="form-select" id="accountItemSelect"></select>
			</div>
			<div class="input-group mb-3">
				<span class="input-group-text">金額</span>
				<input type="number" class="form-control">
			</div>
			<div>
				<button type="button" class="btn btn-primary" id="spendBtn" name="spendBtn">確定</button>
			</div>
		</div>
	</div>
</body>
</html>