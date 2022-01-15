<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/views/include/header.jsp"></jsp:include>
	<link rel="stylesheet" href="/css/index.css">
</head>
<body>
	<div class="container-fluid py-3 top-box">
		<div class="container-xl">
			<div class="row">
				<div class="col-xs-12 col-sm-12 col-md-7 col-lg-7">
					<h1>致富寶典 RichNote</h1>
				</div>
				<div class="col-xs-12 col-sm-12 col-md-2 col-lg-2 mb-3">
					<input type="text" class="form-control" id="userAcc" name="userAcc" placeholder="帳號、電子信箱">
				</div>
				<div class="col-xs-12 col-sm-12 col-md-2 col-lg-2 mb-3">
					<input type="password" class="form-control" id="userPwd" name="userPwd" placeholder="請輸入密碼">
				</div>
				<div class="col-xs-12 col-sm-12 col-md-1 col-lg-1 text-center">
					<button type="button" class="btn btn-success" id="loginBtn" name="loginBtn">登入</button>
				</div>
			</div>
		</div>
	</div>
	<div class="container-fluid py-3">
		<div class="container-xl">
			<div class="row">
				<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 text-center">
					<h3>訪客帳號，快速註冊</h3>
					<h3>免費試用中</h3>
				</div>
				<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 d-grid gap-2">
					<button type="button" class="btn btn-primary btn-lg" id="guestBtn" name="guestBtn">訪客試用</button>
				</div>
			</div>
		</div>
	</div>
	<!--
	<div class="container-fluid my-4" id="socialCard">
		<div class="container-xl">
			<div class="row social-bg py-3 mx-2 rounded">
				<div class="col text-center">
					<h3>社群平台</h3>
				</div>
			</div>
			<div class="row">
				<div class="col-xs-12 col-sm-12 col-md-3 col-lg-3 text-center mt-3">
					<a href="https://page.line.me/richnote">
						<div class="card card-width mx-auto">
							<div class="card-img-min-height">
								<img src="https://resources.richnote.net/index/line_card.jpg" 
									class="card-img-top" alt="致富寶典的 LINE 官方帳號">
							</div>
							<div class="card-body">
								<i class="fab fa-line fa-3x text-primary mb-2"></i>
								<h5 class="card-title">LINE 官方帳號</h5>
								<button type="button" class="btn btn-primary mt-2">
									加入 LINE 好友
								</button>
							</div>
						</div>
					</a>
				</div>
				<div class="col-xs-12 col-sm-12 col-md-3 col-lg-3 text-center mt-3">
					<a href="https://www.facebook.com/RichNote.net">
						<div class="card card-width mx-auto">
							<div class="card-img-min-height">
								<img src="https://resources.richnote.net/index/fb_card.jpg" 
									class="card-img-top" alt="致富寶典的 FaceBook 粉絲專頁">
							</div>
							<div class="card-body">
								<i class="fab fa-facebook fa-3x text-primary mb-2"></i>
								<h5 class="card-title">FaceBook 粉絲專頁</h5>
								<button type="button" class="btn btn-primary mt-2">
									追蹤 FaceBook
								</button>
							</div>
						</div>
					</a>
				</div>
				<div class="col-xs-12 col-sm-12 col-md-3 col-lg-3 text-center mt-3">
					<a href="https://www.instagram.com/richnote_net">
						<div class="card card-width mx-auto">
							<div class="card-img-min-height">
								<img src="https://resources.richnote.net/index/ig_card.jpg" 
									class="card-img-top" alt="致富寶典的 Instagram 粉絲專頁">
							</div>
							<div class="card-body">
								<i class="fab fa-instagram fa-3x text-primary mb-2"></i>
								<h5 class="card-title">Instagram 官方帳號</h5>
								<button type="button" class="btn btn-primary mt-2">
									追蹤 Instagram
								</button>
							</div>
						</div>
					</a>
				</div>
				<div class="col-xs-12 col-sm-12 col-md-3 col-lg-3 text-center mt-3">
					<a href="https://www.youtube.com/channel/UCRY2uJHJwFgCagB_xJy7K8A">
						<div class="card card-width mx-auto">
							<div class="card-img-min-height">
								<img src="https://resources.richnote.net/index/yt_card.jpg" 
									class="card-img-top" alt="致富寶典的 YouTube 頻道">
							</div>
							<div class="card-body">
								<i class="fab fa-youtube fa-3x text-primary mb-2"></i>
								<h5 class="card-title">YouTube 頻道</h5>
								<button type="button" class="btn btn-primary mt-2">
									訂閱 YouTube 頻道
								</button>
							</div>
						</div>
					</a>
				</div>
			</div>
		</div>
	</div>
	-->
	<input type="hidden" id="user" name="user" value="${user}">
	<jsp:include page="/views/include/footer.jsp"></jsp:include>
	<script type="text/javascript" src="/js/index.js"></script>
</body>
</html>