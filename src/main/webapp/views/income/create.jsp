<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/views/include/header.jsp"></jsp:include>
	<link rel="stylesheet" href="/css/income/create.css">
</head>
<body>
	<jsp:include page="/views/include/menuBar.jsp"></jsp:include>
	<div class="container-fluid pb-5 mb-5">
		<div class="container-xl">
			<h3 class="text-center">新增一筆收入</h3>
			<div class="row">
				<div class="col">日期</div>
			</div>
			<div class="row mb-2">
				<div class="col">
					<input type="text" class="form-control" id="incomeDatePicker" name="incomeDate" readonly>
				</div>
			</div>
			<div class="row">
				<div class="col">項目</div>
			</div>
			<div class="row mb-2">
				<div class="col">
					<select class="form-select" id="incomeItemSelect" name="incomeItemSelect"></select>
				</div>
			</div>
			<div class="row">
				<div class="col">帳戶</div>
			</div>
			<div class="row mb-2">
				<div class="col">
					<select class="form-select" id="accountItemSelect" name="accountItemSelect"></select>
				</div>
			</div>
			<div class="row">
				<div class="col">金額</div>
			</div>
			<div class="row mb-2">
				<div class="col">
					<input type="text" class="form-control" id="amount" name="amount" placeholder="0">
				</div>
			</div>
			<div class="row">
				<div class="col">備註</div>
			</div>
			<div class="row mb-2">
				<div class="col">
					<input type="text" class="form-control remark-height" id="remark" name="remark" placeholder="（最多可以輸入200個字）">
				</div>
			</div>
			<input type="hidden" id="incomeDate" name="incomeDate">
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
	<jsp:include page="/views/include/footer.jsp"></jsp:include>
	<script type="text/javascript" src="/js/income/create.js"></script>
	<script type="text/javascript" src="/js/common/checkAmnt.js"></script>
</body>
</html>