$(function() {

	$('#loginBtn').on('click', loginAct);

	$('#userName, #userPwd').on('keypress', function(e) {
		var code = (e.keyCode ? e.keyCode : e.which);
		if (code == 13) $('#loginBtn').trigger('click');
	});
});

function loginAct() {
	
	var data = {};
	data.userName = $('#userName').val();
	data.userPwd = $('#userPwd').val();
	
	console.log(data);
	
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
				loginDialog(res.msg);
				$('#userPwd').val('');
			}
		},
		error: function(err) {
			loginDialog('無法連接伺服器');
			console.log(err);
		}
	});
}

function loginDialog(msg) {
	var loginDialog = new bootstrap.Modal($('#loginMsgDialog'));
	$('#loginMsg').text(msg);
	loginDialog.show();

	$('#loginMsgDialog').on('hidden.bs.modal', function() {
		$('#loginMsg').text('');
	});
}
