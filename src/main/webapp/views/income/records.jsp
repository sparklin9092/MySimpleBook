<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/views/include/header.jsp"></jsp:include>
	<link rel="stylesheet" href="/css/income/records.css">
	<script type="text/javascript" src="/js/income/records.js"></script>
</head>
<body>
	<jsp:include page="/views/include/menuBar.jsp"></jsp:include>
	<div class="container-fluid pb-5 mb-5">
		<div class="container-xl">
			<h3 class="text-center">收入紀錄</h3>
			<div class="row my-3 pb-2 border-bottom">
				<div class="col d-grid gap-2">
					<button type="button" class="btn btn-info btn-lg" id="todayBtn" name="changeDateRangeBtn" data-range="1">今天</button>
				</div>
				<div class="col d-grid gap-2">
					<button type="button" class="btn btn-outline-info btn-lg" id="thisWeekBtn" name="changeDateRangeBtn" data-range="7">這個星期</button>
				</div>
				<div class="col d-grid gap-2">
					<button type="button" class="btn btn-outline-info btn-lg" id="thisMonthBtn" name="changeDateRangeBtn" data-range="30">這個月</button>
				</div>
				<div class="col d-grid gap-2">
					<button type="button" class="btn btn-outline-info btn-lg" id="beforeThreeMonthBtn" name="changeDateRangeBtn" data-range="90">前三個月</button>
				</div>
			</div>
			<table id="incomeRecTable" class="cell-border" style="width: 100%"></table>
		</div>
	</div>
	<jsp:include page="/views/include/footer.jsp"></jsp:include>
</body>
</html>