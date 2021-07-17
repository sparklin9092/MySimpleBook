<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<nav class="navbar navbar-expand-lg navbar-dark menu-color mb-3">
	<div class="container-xl py-3">
		<a class="navbar-brand menu-title" href="/main">
			<span id="menuUserTitle" name="menuUserTitle">我的記帳本</span>
		</a>
		<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#menuList" aria-controls="menuList" aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="menuList">
			<ul class="navbar-nav me-auto mb-2 mb-lg-0">
				<li class="nav-item">
					<a class="nav-link menu-item" href="/spend">新增支出</a>
				</li>
				<li class="nav-item">
					<a class="nav-link menu-item" href="/income">新增收入</a>
				</li>
				<li class="nav-item">
					<a class="nav-link menu-item" href="/account">帳戶管理</a>
				</li>
				<li class="nav-item dropdown">
					<a class="nav-link menu-item dropdown-toggle" href="#" id="itemMenage" role="button" data-bs-toggle="dropdown" aria-expanded="false">項目管理</a>
					<ul class="dropdown-menu" aria-labelledby="itemMenage">
						<li>
							<a class="dropdown-item menu-sub-item" href="/itemSpend">支出項目管理</a>
						</li>
						<li>
							<a class="dropdown-item menu-sub-item" href="/itemIncome">收入項目管理</a>
						</li>
					</ul>
				</li>
			</ul>
			<button type="botton" class="btn btn-danger btn-lg" id="logout" name="logout">登出</button>
		</div>
	</div>
</nav>