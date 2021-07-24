$(function() {

	initData();

	$('#changeName').on('click', changeNameAct);
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
				var maskPwd = res.dto.maskPwd;
				var userEmail = res.dto.userEmail;
				var userPhone = res.dto.userPhone;
				var createDate = res.dto.createDate;
				
				$('#userName').val(userName);
				$('#userPwd').val(maskPwd);
				$('#userEmail').val(userEmail);
				$('#userPhone').val(userPhone);
				$('#createDate').val(createDate);
				
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

function changeNameAct() {
	
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
