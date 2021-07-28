$(function() {

	$('#phone, #email').hide();
	
	$('#accPwdTitle').on('click', accPwdShow);
	$('#phoneTitle').on('click', phoneShow);
	$('#emailTitle').on('click', emailShow);
	
	$('#checkAcc').on('click', checkUserAccAct);
	$('#userAcc').on('blur', checkUserAccAct);
	
	$('#bindBtn').on('click', bindAct);

	$('#cancelBtn').on('click', cancelAct);
});

function cancelAct() {
	
	location.href = '/main';
}

function accPwdShow() {
	
	$('#accPwd').show();
	$('#phone, #email').hide();
}

function phoneShow() {
	
	$('#phone').show();
	$('#accPwd, #email').hide();
	
	alert("功能未開放使用，請使用「帳號與密碼」或「電子信箱(Email)」進行綁定。");
	
	$('#accPwdTitle').trigger('click');
}

function emailShow() {
	
	$('#email').show();
	$('#accPwd, #phone').hide();
}

function checkUserAccAct() {
	
	var data = {};
	data.userAcc = $('#userAcc').val();
	
	$.ajax({
		url: '/guest/check/account',
		method: 'POST',
		dataType: 'json',
		contentType: 'application/json',
		data: JSON.stringify(data),
		success: function(res) {
			
			if(res.status) {
				
				$('#checkAcc').removeClass('btn-warning').addClass('btn-success');
				$('#checkAcc').prop('disabled', true).text('通過');
				
			} else {
				
				$('#checkAcc').removeClass('btn-success').addClass('btn-warning');
				$('#checkAcc').prop('disabled', false).text('檢查');
				
				alert(res.msg);
			}
		},
		error: function(err) {
			console.log(err);
			alert('無法連接伺服器');
		}
	});
}

function bindAccPwdAct() {
	
	$('#bindBtn').addClass('btn-warning').removeClass('btn-success');
	$('#bindBtn').prop('disabled', false).text('確認中');
	
	setTimeout(function() {
	
		var data = {};
		data.userAcc = $('#userAcc').val();
		data.userpwd = $('#userPwd').val();
		
		$.ajax({
			url: '/guest/bind/accpwd',
			method: 'POST',
			dataType: 'json',
			contentType: 'application/json',
			data: JSON.stringify(data),
			success: function(res) {
				
				if(res.status) {
					
					$('#bindBtn').addClass('btn-success').removeClass('btn-warning');
					$('#bindBtn').prop('disabled', true).text('已綁定');
					
					alert("帳號綁定成功！請重新登入。");
					location.href = '/';
					
				} else {
					
					$('#bindBtn').addClass('btn-success').removeClass('btn-warning');
					$('#bindBtn').prop('disabled', false).text('綁定');
					
					$('#checkAcc').removeClass('btn-success').addClass('btn-warning');
					$('#checkAcc').prop('disabled', false).text('檢查');
					
					alert(res.msg);
				}
			},
			error: function(err) {
				console.log(err);
				alert('無法連接伺服器');
			}
		});
	}, 1000)
}

function bindPhoneAct() {
	
	alert("功能開放使用，請使用「帳號與密碼」進行綁定。");
	
	$('#accPwdTitle').trigger('click');
}

function bindEmailAct() {
	
	$('#bindBtn').addClass('btn-warning').removeClass('btn-success');
	$('#bindBtn').prop('disabled', false).text('確認中');
	
	setTimeout(function() {
	
		var data = {};
		data.userEmail = $('#userEmail').val();
		
		$.ajax({
			url: '/guest/bind/email',
			method: 'POST',
			dataType: 'json',
			contentType: 'application/json',
			data: JSON.stringify(data),
			success: function(res) {
				
				if(res.status) {
					
					$('#bindBtn').addClass('btn-success').removeClass('btn-warning');
					$('#bindBtn').prop('disabled', true).text('已寄發');
					
					alert("已寄發認證碼到您的信箱！請到您的信箱確認郵件。");
					location.href = '/logout';
					
				} else {
					
					$('#bindBtn').addClass('btn-success').removeClass('btn-warning');
					$('#bindBtn').prop('disabled', false).text('綁定');
					
					alert(res.msg);
				}
			},
			error: function(err) {
				console.log(err);
				alert('無法連接伺服器');
			}
		});
	}, 1000);
}

function bindAct() {
	
	var bindtype = $('input[name=bindType]:checked').data('bindtype');
	
	if(bindtype == '1') bindAccPwdAct();
	else if(bindtype == '2') bindPhoneAct();
	else if(bindtype == '3') bindEmailAct();
	else alert('請選擇您要綁定的方式。');
	
}
