<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/views/include/header.jsp"></jsp:include>
	<link rel="stylesheet" href="/css/account/list.css">
</head>
<body>
	<jsp:include page="/views/include/menuBar.jsp"></jsp:include>
	<div class="container-fluid pb-5 mb-5">
		<div class="container-xl">
			<h3 class="text-center">帳戶管理</h3>
			<div class="text-end">
				<button type="button" class="btn btn-success btn-lg" id="createBtn" name="createBtn">新增</button>
			</div>
			<table id="accountTable" class="cell-border" style="width: 100%"></table>
		</div>
	</div>
	<jsp:include page="/views/include/footer.jsp"></jsp:include>
	<script type="text/javascript" src="/js/account/list.js"></script>
</body>
</html>