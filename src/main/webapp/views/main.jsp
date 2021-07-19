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
	<div class="container-fluid pb-5 mb-5">
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
	<jsp:include page="/views/include/footer.jsp"></jsp:include>
</body>
</html>