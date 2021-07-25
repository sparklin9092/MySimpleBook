<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/views/include/header.jsp"></jsp:include>
	<link rel="stylesheet" href="/css/verify/mail.css">
	<script type="text/javascript" src="/js/verify/mail.js"></script>
</head>
<body>
	<div class="mail-box">
		<div class="rounded shadow mail-form mail-form-padding">
			<div class="h1 text-center">致富寶典-電子信箱(Email)綁定</div>
			<div>
				<div class="h4 mt-3">您要綁定的電子信箱</div>
				<div>
					<input type="text" class="form-control" id="userMail" name="userMail" readonly>
				</div>
				<div class="h4 mt-3">驗證碼</div>
				<div>
					<input type="text" class="form-control" id="verifyCode" name="verifyCode" placeholder="請輸入驗證碼">
				</div>
				<div class="text-center mt-5 mb-4">
					<button type="button" class="btn btn-success btn-lg" id="bindBtn" name="bindBtn">綁定</button>
				</div>
			</div>
			<input type="hidden" id="buAcc" name="buAcc" value="${buAcc}">
		</div>
	</div>
	<jsp:include page="/views/include/footer.jsp"></jsp:include>
</body>
</html>