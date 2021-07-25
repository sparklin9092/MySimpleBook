$(function() {
	
	initBuAcc();

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
			}
		},
		error: function(err) {
			console.log(err);
			alert('無法連接伺服器');
		}
	});
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
				alert('綁定成功，可以用電子信箱登入了！');
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
