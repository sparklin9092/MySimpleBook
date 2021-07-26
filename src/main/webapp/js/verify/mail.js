$(function() {
	
	initBuAcc();

	$('#homeBtn').on('click', homeAct);
	$('#bindBtn').on('click', bindAct);

	$('#verifyCode').on('keypress', function(e) {
		var code = (e.keyCode ? e.keyCode : e.which);
		if (code == 13) $('#bindBtn').trigger('click');
	});
});

function initBuAcc() {
	
	var data = {};
	data.buAcc = $('#buAcc').val();
	
	$.ajax({
		url: '/verify/mail/buacc',
		method: 'POST',
		dataType: 'json',
		contentType: 'application/json',
		data: JSON.stringify(data),
		success: function(res) {
			if(res.status) {
				$('#userMail').val(res.userMail);
			} else {
				$('#userMail').val('系統查無資料，請重新綁定');
				$('#bindBtn').prop('disabled', true);
			}
		},
		error: function(err) {
			console.log(err);
			alert('無法連接伺服器');
		}
	});
}

function homeAct() {
	
	location.href = '/';
}

function bindAct() {
	
	var data = {};
	data.buAcc = $('#buAcc').val();
	data.verifyCode = $('#verifyCode').val();
	
	$.ajax({
		url: '/verify/mail/bindAct',
		method: 'POST',
		dataType: 'json',
		contentType: 'application/json',
		data: JSON.stringify(data),
		success: function(res) {
			if(res.status) {
				alert('綁定成功！已寄發臨時密碼到您的信箱，請到信箱索取臨時密碼進行登入！');
				location.href = '/';
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
