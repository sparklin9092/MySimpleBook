<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="include/header.jsp"></jsp:include>
	<link rel="stylesheet" href="/css/itemSpend.css">
	<script type="text/javascript" src="/js/itemSpend.js"></script>
	<script type="text/javascript" src="/js/userCheck.js"></script>
</head>
<body>
	<jsp:include page="include/menuBar.jsp"></jsp:include>
	<div class="container-fluid pb-5 mb-5">
		<div class="container-xl">
			<h3 class="text-center">支出項目管理</h3>
			<div class="text-end">
				<button type="button" class="btn btn-success btn-lg my-3" id="createBtn" name="createBtn">新增</button>
			</div>
			<table id="spendItemTable" class="cell-border" style="width: 100%"></table>
			<!-- <div class="create-btn" id="createBtn" name="createBtn">新增</div> -->
		</div>
	</div>
</body>
</html>