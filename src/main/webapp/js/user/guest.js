$(function() {

	$('#phone, #email').hide();
	
	$('#accPwdTitle').on('click', accPwdShow);
	$('#phoneTitle').on('click', phoneShow);
	$('#emailTitle').on('click', emailShow);
	
	$('#checkAcc').on('click', checkUserAccAct);
	$('#userAcc').on('blur', checkUserAccAct);
	
	$('#bindAccPwd').on('click', bindAccPwdAct);
	$('#bindPhone').on('click', bindPhoneAct);
	$('#bindEmail').on('click', bindEmailAct);

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
	
	alert("功能開放使用，請使用「帳號與密碼」進行綁定。");
	
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
		url: '/user/info/check/account',
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
	
	var data = {};
	data.userAcc = $('#userAcc').val();
	data.userpwd = $('#userPwd').val();
	
	$.ajax({
		url: '/user/info/bind/accpwd',
		method: 'POST',
		dataType: 'json',
		contentType: 'application/json',
		data: JSON.stringify(data),
		success: function(res) {
			
			if(res.status) {
				
				alert("帳號綁定成功！請重新登入。");
				location.href = '/logout';
				
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

function bindPhoneAct() {
	
	alert("功能開放使用，請使用「帳號與密碼」進行綁定。");
	
	$('#accPwdTitle').trigger('click');
}

function bindEmailAct() {
	
	$('#bindEmail').addClass('btn-warning').removeClass('btn-success');
	$('#bindEmail').prop('disabled', false).text('確認中');
	
	setTimeout(function() {
	
		var data = {};
		data.userEmail = $('#userEmail').val();
		
		$.ajax({
			url: '/user/info/bind/email',
			method: 'POST',
			dataType: 'json',
			contentType: 'application/json',
			data: JSON.stringify(data),
			success: function(res) {
				
				if(res.status) {
					
					alert("帳號綁定成功！請重新登入。");
					location.href = '/logout';
					
				} else {
					
					$('#bindEmail').addClass('btn-success').removeClass('btn-warning');
					$('#bindEmail').prop('disabled', false).text('綁定');
					
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
