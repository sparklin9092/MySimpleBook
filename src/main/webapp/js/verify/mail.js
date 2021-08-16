$(function() {
	
	initBuAcc();

	$('#homeBtn').on('click', homeAct);
	$('#bindBtn').on('click', bindAct);
	$('#reSendBtn').on('click', reSendAct);

	$('#verifyCode').on('keypress', function(e) {
		var code = (e.keyCode ? e.keyCode : e.which);
		if (code == 13) $('#bindBtn').trigger('click');
	});
});

function initBuAcc() {
	
	$('#reSendBtn').prop('disabled', true);
	$('#reSendBtn').text('重寄認證碼(180)');
	
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
				
				if(res.used) {
					
					showMsg("您的信箱已經綁定完成！請使用信箱進行登入。");
					location.href = '/';
					
				} else {

					$('#userMail').val(res.userMail);

					var reSendSec = res.reSendSec;

					if (reSendSec == 0) {

						$('#reSendBtn').prop('disabled', false);
						$('#reSendBtn').text('重寄認證碼');

					} else {

						$('#reSendBtn').prop('disabled', true);
						$('#reSendBtn').text('重寄認證碼(' + reSendSec + ')');

						var reSendMailInteval = setInterval(function() {

							reSendSec -= 1;
							$('#reSendBtn').text('重寄認證碼(' + reSendSec + ')');

							if (reSendSec <= 0) {

								$('#reSendBtn').prop('disabled', false);
								$('#reSendBtn').text('重寄認證碼');

								clearInterval(reSendMailInteval);
							}
						}, 1000);
					}
				}
			} else {
				
				$('#userMail').val('系統查無資料，請重新綁定');
				$('#bindBtn').prop('disabled', true);
			}
		},
		error: function(err) {
			sysMsg('無法連接伺服器');
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
				showMsg(res.msg);
				location.href = '/';
			} else {
				errMsg(res.msg);
			}
		},
		error: function(err) {
			sysMsg('無法連接伺服器');
		}
	});
}

function reSendAct() {
	
	$('#reSendBtn').prop('disabled', true);
	$('#reSendBtn').text('重寄認證碼(180)');
	
	var data = {};
	data.buAcc = $('#buAcc').val();
	
	$.ajax({
		url: '/verify/mail/resend',
		method: 'POST',
		dataType: 'json',
		contentType: 'application/json',
		data: JSON.stringify(data),
		success: function(res) {
			
			if(res.status) {
				
				showMsg('已經重新寄發認證碼到您的信箱！請到信箱索取認證碼進行綁定！');
				
				initBuAcc();
			} else {
				errMsg(res.msg);
			}
		},
		error: function(err) {
			sysMsg('無法連接伺服器');
		}
	});
}
