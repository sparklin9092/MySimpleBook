$(function() {

	initData();
	
	$('#checkAcc').on('click', checkUserAccAct);
	$('#userAcc').on('blur', checkUserAccAct);

	$('#changePwd').on('click', changePwdView);
	$('#bindEmail').on('click', bindEmailAct);
	$('#bindPhone').on('click', bindPhoneAct);
	$('#deleteBtn').on('click', deleteAct);
	$('#confirmBtn').on('click', confirmAct);
});

function initData() {
	
	$.ajax({
		url: '/user/info',
		method: 'POST',
		dataType: 'json',
		contentType: 'application/json',
		data: {},
		success: function(res) {
			
			if(res.status) {
				
				var userName = res.dto.userName;
				var userAcc = res.dto.userAcc;
				var maskPwd = res.dto.maskPwd;
				var userEmail = res.dto.userEmail;
				var userPhone = res.dto.userPhone;
				var createDate = res.dto.createDate;
				
				$('#userName').val(userName);
				$('#userAcc').val(userAcc);
				$('#userPwd').val(maskPwd);
				$('#userEmail').val(userEmail);
				$('#userPhone').val(userPhone);
				$('#createDate').val(createDate);
				
				$('#checkAcc').prop('disabled', true);
				
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

function changePwdView() {
	
}

function bindEmailAct() {
	
}

function bindPhoneAct() {
	
}

function deleteAct() {
	
}

function confirmAct() {
	
}
