$(function() {
	
	$('#logout').on('click', logoutAct);
});

function logoutAct() {
	
	location.href = 'logout';
}