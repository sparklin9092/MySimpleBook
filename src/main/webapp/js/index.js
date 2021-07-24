$(function() {

	$('#loginBtn').on('click', loginAct);
	
	$('#guestBtn').on('click', guestAct);

	$('#userName, #userPwd').on('keypress', function(e) {
		var code = (e.keyCode ? e.keyCode : e.which);
		if (code == 13) $('#loginBtn').trigger('click');
	});
});

function loginAct() {
	
	var data = {};
	data.userName = $('#userName').val();
	data.userPwd = $('#userPwd').val();
	
	$.ajax({
		url: '/login',
		method: 'POST',
		dataType: 'json',
		contentType: 'application/json',
		data: JSON.stringify(data),
		success: function(res) {
			if(res.status) {
				location.href = 'main';
			} else {
				alert(res.msg);
				$('#userPwd').val('');
			}
		},
		error: function(err) {
			console.log(err);
			alert('無法連接伺服器');
		}
	});
}

function guestAct() {
	
	$.ajax({
		url: '/login/guest',
		method: 'POST',
		dataType: 'json',
		contentType: 'application/json',
		data: {},
		success: function(res) {
			if(res.status) {
				location.href = 'main';
			} else {
				alert(res.msg);
			}
		},
		error: function(err) {
			console.log(err);
			alert('無法連接伺服器');
		}
	});
}
