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
			<div class="row">
				<div class="col">查詢日期</div>
			</div>
			<div class="row mb-3 pb-2 border-bottom">
				<div class="col-xs-12 col-sm-6 col-md-2 col-lg-2 d-grid gap-2">
					<button type="button" class="btn btn-info range-btn" id="todayBtn" name="changeDateRangeBtn" data-range="1">今天</button>
				</div>
				<div class="col-xs-12 col-sm-6 col-md-2 col-lg-2 d-grid gap-2">
					<button type="button" class="btn btn-outline-info range-btn" id="yesterdayBtn" name="changeDateRangeBtn" data-range="2">昨天</button>
				</div>
				<div class="col-xs-12 col-sm-6 col-md-2 col-lg-2 d-grid gap-2">
					<button type="button" class="btn btn-outline-info range-btn" id="thisWeekBtn" name="changeDateRangeBtn" data-range="7">這個星期</button>
				</div>
				<div class="col-xs-12 col-sm-6 col-md-2 col-lg-2 d-grid gap-2">
					<button type="button" class="btn btn-outline-info range-btn" id="thisMonthBtn" name="changeDateRangeBtn" data-range="30">這個月</button>
				</div>
				<div class="col-xs-12 col-sm-6 col-md-2 col-lg-2 d-grid gap-2">
					<button type="button" class="btn btn-outline-info range-btn" id="beforeThreeMonthBtn" name="changeDateRangeBtn" data-range="90">前三個月</button>
				</div>
				<div class="col-xs-12 col-sm-6 col-md-2 col-lg-2 d-grid gap-2">
					<button type="button" class="btn btn-outline-info range-btn" id="specifyDateBtn" name="changeDateRangeBtn" data-range="-1" data-bs-toggle="modal" data-bs-target="#specifyDateModal">指定日期</button>
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
	<!-- 指定日期 彈跳視窗 -->
	<div class="modal fade" id="specifyDateModal" tabindex="-1">
		<div class="modal-dialog modal-dialog-centere">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="specifyDateModalLabel">指定日期</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
				</div>
				<div class="modal-body">
					<input type="text" class="form-control" id="datePicker" name="datePicker" readonly>
					<input type="hidden" id="specifyDate" name="specifyDate">
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">離開</button>
					<button type="button" class="btn btn-primary" id="specifyDateModalBtn" name="specifyDateModalBtn">確定</button>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="/views/include/footer.jsp"></jsp:include>
	<script type="text/javascript" src="/js/account/detail.js"></script>
</body>
</html>