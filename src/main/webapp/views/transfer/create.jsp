<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/views/include/header.jsp"></jsp:include>
	<link rel="stylesheet" href="/css/transfer/create.css">
	<script type="text/javascript" src="/js/transfer/create.js"></script>
	<script type="text/javascript" src="/js/common/checkAmnt.js"></script>
</head>
<body>
	<jsp:include page="/views/include/menuBar.jsp"></jsp:include>
	<div class="container-fluid pb-5 mb-5">
		<div class="container-xl">
			<h3 class="text-center">新增一筆轉帳</h3>
			<div class="row">
				<div class="col">日期</div>
			</div>
			<div class="row">
				<div class="col">
					<input type="text" class="form-control" id="transferDatePicker" name="transferDate" readonly>
				</div>
			</div>
			<div class="row mx-1 my-3 border border-warning rounded">
				<div class="col-2 vertical-center in-out-title">轉出帳戶</div>
				<div class="col-10 py-3">
					<div class="row">
						<div class="col">帳戶</div>
					</div>
					<div class="row mb-2">
						<div class="col">
							<select class="form-select" id="tOutAccItemSelect" name="tOutAccItemSelect"></select>
						</div>
					</div>
					<div class="row">
						<div class="col">金額</div>
					</div>
					<div class="row mb-2">
						<div class="col">
							<input type="text" class="form-control" id="tOutAmnt" name="tOutAmnt" placeholder="0">
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col">
					<input type="checkbox" class="form-check-input" id="outSideAccCheck" name="outSideAccCheck">
					<label for="outSideAccCheck">是否為外部帳號（不是自己的帳號，或是還沒紀錄在系統裡）</label>
				</div>
			</div>
			<div class="row mx-1 my-3 border border-info rounded" id="insideAcc" name="insideAcc">
				<div class="col-2 vertical-center in-out-title">轉入帳戶</div>
				<div class="col-10 py-3">
					<div class="row">
						<div class="col">帳戶</div>
					</div>
					<div class="row mb-2">
						<div class="col">
							<select class="form-select" id="tInAccItemSelect" name="tInAccItemSelect"></select>
						</div>
					</div>
				</div>
			</div>
			<div class="row mx-1 my-3 border border-info rounded" id="outsideAcc" name="outsideAcc">
				<div class="col-2 vertical-center in-out-title">轉入外部帳戶</div>
				<div class="col-10 py-3">
					<div class="row">
						<div class="col">帳戶名稱</div>
					</div>
					<div class="row mb-2">
						<div class="col">
							<input type="text" class="form-control" id="tOutsideAccName" name="tOutsideAccName" placeholder="選填">
						</div>
					</div>
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
			<input type="hidden" id="transferDate" name="transferDate">
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
</body>
</html>