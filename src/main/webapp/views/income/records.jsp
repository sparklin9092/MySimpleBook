<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/views/include/header.jsp"></jsp:include>
	<link rel="stylesheet" href="/css/income/records.css">
</head>
<body>
	<jsp:include page="/views/include/menuBar.jsp"></jsp:include>
	<div class="container-fluid pb-5 mb-5">
		<div class="container-xl">
			<h3 class="text-center">收入紀錄</h3>
			<div class="row mb-3 pb-2 border-bottom">
				<div class="col-xs-12 col-sm-6 col-md-2 col-lg-2 d-grid gap-2">
					<button type="button" class="btn btn-info range-btn" id="todayBtn" name="changeDateRangeBtn" data-range="1">今天</button>
				</div>
				<div class="col-xs-12 col-sm-6 col-md-2 col-lg-2 d-grid gap-2">
					<button type="button" class="btn btn-outline-info range-btn" id="yesterdayBtn" name="changeDateRangeBtn" data-range="2">昨天</button>
				</div>
				<div class="col-xs-12 col-sm-6 col-md-2 col-lg-2 d-grid gap-2">
					<button type="button" class="btn btn-outline-info range-btn" id="thisMonthBtn" name="changeDateRangeBtn" data-range="30">這個月</button>
				</div>
				<div class="col-xs-12 col-sm-6 col-md-2 col-lg-2 d-grid gap-2">
					<button type="button" class="btn btn-outline-info range-btn" id="beforeThreeMonthBtn" name="changeDateRangeBtn" data-range="90">前三個月</button>
				</div>
				<div class="col-xs-12 col-sm-6 col-md-2 col-lg-2 d-grid gap-2">
					<button type="button" class="btn btn-outline-info range-btn" id="beforeSixMonthBtn" name="changeDateRangeBtn" data-range="180">前六個月</button>
				</div>
				<div class="col-xs-12 col-sm-6 col-md-2 col-lg-2 d-grid gap-2">
					<button type="button" class="btn btn-outline-info range-btn" id="specifyDateBtn" name="changeDateRangeBtn" data-range="-1" data-bs-toggle="modal" data-bs-target="#specifyDateModal">指定日期</button>
				</div>
			</div>
			<table id="table" class="cell-border" style="width: 100%"></table>
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
	<script type="text/javascript" src="/js/income/records.js"></script>
</body>
</html>