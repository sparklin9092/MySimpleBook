$(function() {
	
	$('#userInfo').on('click', userInfoView);
	
	$('#logout').on('click', logoutAct);
});

function logoutAct() {
	
	location.href = '/logout';
}

function userInfoView() {
	
	location.href = '/user/info';
}