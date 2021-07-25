$(function() {

	$('#phone, #email').hide();
	
	$('#accPwdTitle').on('click', accPwdShow);
	$('#phoneTitle').on('click', phoneShow);
	$('#emailTitle').on('click', emailShow);
	
	$('#changeAcc').on('click', checkUserAccAct);
	
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
	
	alert("功能開放使用，請使用「帳號與密碼」進行綁定。");
	
	$('#accPwdTitle').trigger('click');
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
				
				$('#changeAcc').removeClass('btn-warning').addClass('btn-success');
				$('#changeAcc').prop('disabled', true).text('通過');
				
			} else {
				
				$('#changeAcc').removeClass('btn-success').addClass('btn-warning');
				$('#changeAcc').prop('disabled', false).text('檢查');
				
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
				
				$('#changeAcc').removeClass('btn-success').addClass('btn-warning');
				$('#changeAcc').prop('disabled', false).text('檢查');
				
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
	
	alert("功能開放使用，請使用「帳號與密碼」進行綁定。");
	
	$('#accPwdTitle').trigger('click');
	
}
