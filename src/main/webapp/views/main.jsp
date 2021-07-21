<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/views/include/header.jsp"></jsp:include>
	<link rel="stylesheet" href="/css/main.css">
	<script type="text/javascript" src="/js/main.js"></script>
	<script type="text/javascript" src="/js/common/userCheck.js"></script>
</head>
<body>
	<jsp:include page="/views/include/menuBar.jsp"></jsp:include>
	<div class="container-fluid pb-4 mb-4">
		<div class="container-xl">
			<div id="carouselExampleFade" class="carousel slide carousel-fade" data-bs-ride="carousel">
				<div class="carousel-inner text-center bg-light py-3 rounded-pill display-6" id="richCodeBox" name="richCodeBox"></div>
			</div>
			<div class="row my-3">
				<div class="col d-grid gap-2">
					<button type="button" class="btn btn-outline-info btn-lg" id="transferBtn" name="transferBtn">轉帳</button>
				</div>
				<div class="col d-grid gap-2">
					<button type="button" class="btn btn-outline-success btn-lg" id="incomeBtn" name="incomeBtn">收入</button>
				</div>
				<div class="col d-grid gap-2">
					<button type="button" class="btn btn-outline-danger btn-lg" id="spendBtn" name="spendBtn">支出</button>
				</div>
			</div>
		</div>
	</div>
	<div class="container-fluid pb-4 mb-4">
		<div class="container-xl">
			<div class="row border border-info rounded-top">
				<h4 class="col text-center my-2">
					今天的<span class="text-info">轉帳</span>紀錄
				</h4>
			</div>
			<div class="row py-2 text-center border-start border-end border-bottom border-info">
				<div class="col">轉出帳戶</div>
				<div class="col">轉入帳戶</div>
				<div class="col">轉帳金額</div>
			</div>
			<div class="row py-2 text-center border-start border-end border-info">
				<div class="col" id="transRecords" name="transRecords"></div>
			</div>
			<div class="row pb-2 border-start border-end border-bottom border-info rounded-bottom">
				<div class="col d-grid gap-2">
					<button class="btn btn-info">查看更多轉帳</button>
				</div>
			</div>
		</div>
	</div>
	<div class="container-fluid pb-4 mb-4">
		<div class="container-xl">
			<div class="row border border-success rounded-top">
				<h4 class="col text-center my-2">
					今天的<span class="text-success">收入</span>紀錄
				</h4>
			</div>
			<div class="row py-2 text-center border-start border-end border-bottom border-success">
				<div class="col">收入項目</div>
				<div class="col">收入金額</div>
			</div>
			<div class="row py-2 text-center border-start border-end border-success">
				<div class="col" id="incomeRecords" name="incomeRecords"></div>
			</div>
			<div class="row pb-2 border-start border-end border-bottom border-success rounded-bottom">
				<div class="col d-grid gap-2">
					<button class="btn btn-success">查看更多收入</button>
				</div>
			</div>
		</div>
	</div>
	<div class="container-fluid pb-4 mb-4">
		<div class="container-xl">
			<div class="row border border-danger rounded-top">
				<h4 class="col text-center my-2">
					今天的<span class="text-danger">支出</span>紀錄
				</h4>
			</div>
			<div class="row py-2 text-center border-start border-end border-bottom border-danger">
				<div class="col">支出項目</div>
				<div class="col">支出金額</div>
			</div>
			<div class="row py-2 text-center border-start border-end border-danger">
				<div class="col" id="spendRecords" name="spendRecords"></div>
			</div>
			<div class="row pb-2 border-start border-end border-bottom border-success rounded-bottom">
				<div class="col d-grid gap-2">
					<button class="btn btn-danger">查看更多支出</button>
				</div>
			</div>
		</div>
	</div>
	<div class="container-fluid pb-5 mb-5">
		<div class="container-xl">
			<div class="row border border-primary rounded-top">
				<h4 class="col text-center my-2">
					目前的<span class="text-primary">帳戶</span>餘額
				</h4>
			</div>
			<div class="row py-2 text-center border-start border-end border-bottom border-primary">
				<div class="col">帳戶名稱</div>
				<div class="col">帳戶餘額</div>
			</div>
			<div class="row py-2 text-center border-start border-end border-primary">
				<div class="col" id="accRecords" name="accRecords"></div>
			</div>
			<div class="row pb-2 border-start border-end border-bottom border-primary rounded-bottom">
				<div class="col d-grid gap-2">
					<button class="btn btn-primary">查看更多帳戶</button>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="/views/include/footer.jsp"></jsp:include>
</body>
</html>