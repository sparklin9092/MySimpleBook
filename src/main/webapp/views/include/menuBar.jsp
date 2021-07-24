<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="net.spark9092.MySimpleBook.entity.UserInfoEntity" %>
<%@ page import="net.spark9092.MySimpleBook.enums.SessinNameEnum" %>
<%
UserInfoEntity user = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

String title = "我的致富寶典";

if(null != user) {
	if(!user.getUserName().equals("")) {
		title = user.getUserName() + "的致富寶典";
	}
}
%>

<nav class="navbar navbar-expand-lg navbar-dark menu-color mb-3">
	<div class="container-xl py-3">
		<a class="navbar-brand menu-title" href="/main">
			<span id="menuUserTitle" name="menuUserTitle"><%=title%></span>
		</a>
		<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#menuList" aria-controls="menuList" aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="menuList">
			<ul class="navbar-nav me-auto mb-2 mb-lg-0">
				<li class="nav-item dropdown px-3">
					<a class="nav-link menu-item dropdown-toggle" href="#" id="spendMenage" role="button" data-bs-toggle="dropdown" aria-expanded="false">支出</a>
					<ul class="dropdown-menu" aria-labelledby="spendMenage">
						<li>
							<a class="dropdown-item menu-sub-item" href="/spend">新增支出</a>
						</li>
						<li>
							<a class="dropdown-item menu-sub-item" href="/spend/records">支出紀錄</a>
						</li>
						<li>
							<a class="dropdown-item menu-sub-item" href="/spend/items">支出項目管理</a>
						</li>
					</ul>
				</li>
				<li class="nav-item dropdown px-3">
					<a class="nav-link menu-item dropdown-toggle" href="#" id="incomeMenage" role="button" data-bs-toggle="dropdown" aria-expanded="false">收入</a>
					<ul class="dropdown-menu" aria-labelledby="incomeMenage">
						<li>
							<a class="dropdown-item menu-sub-item" href="/income">新增收入</a>
						</li>
						<li>
							<a class="dropdown-item menu-sub-item" href="/income/records">收入紀錄</a>
						</li>
						<li>
							<a class="dropdown-item menu-sub-item" href="/income/items">收入項目管理</a>
						</li>
					</ul>
				</li>
				<li class="nav-item dropdown px-3">
					<a class="nav-link menu-item dropdown-toggle" href="#" id="transferMenage" role="button" data-bs-toggle="dropdown" aria-expanded="false">轉帳</a>
					<ul class="dropdown-menu" aria-labelledby="transferMenage">
						<li>
							<a class="dropdown-item menu-sub-item" href="/transfer">新增轉帳</a>
						</li>
						<li>
							<a class="dropdown-item menu-sub-item" href="/transfer/records">轉帳紀錄</a>
						</li>
					</ul>
				</li>
				<li class="nav-item dropdown px-3">
					<a class="nav-link menu-item dropdown-toggle" href="#" id="accountMenage" role="button" data-bs-toggle="dropdown" aria-expanded="false">帳戶</a>
					<ul class="dropdown-menu" aria-labelledby="accountMenage">
						<li>
							<a class="dropdown-item menu-sub-item" href="/account">帳戶管理</a>
						</li>
						<li>
							<a class="dropdown-item menu-sub-item" href="/account/types">帳戶類型管理</a>
						</li>
					</ul>
				</li>
			</ul>
			<button type="botton" class="btn btn-primary btn-lg me-4" id="userInfo" name="userInfo">使用者</button>
			<button type="botton" class="btn btn-danger btn-lg" id="logout" name="logout">登出</button>
		</div>
	</div>
</nav>