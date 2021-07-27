$(function() {

	initData();
	
	$('#changePwd').on('click', changePwdView);
	$('#bindEmail').on('click', bindEmailAct);
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
				
				$('#menuUserTitle').text(userName + '的致富寶典');
				
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

function changePwdView() {
	
	location.href = '/user/info/pwd';
}

function bindEmailAct() {
	
}

function deleteAct() {
	
	var deleteConfirm = confirm('警告：您確定要刪除帳號嗎？系統將會徹底刪除您的所有資料，而且無法復原！！！');
	
	if(deleteConfirm) {
	
		$.ajax({
			url: '/user/info/delete',
			method: 'POST',
			dataType: 'json',
			contentType: 'application/json',
			data: {},
			success: function(res) {
				
				if(res.status) {
					
					alert("您的帳號資料已全數刪除！謝謝您使用「致富寶典」！");
					location.href = '/logout';
					
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
}

function confirmAct() {
	
	var data = {};
	data.userName = $('#userName').val();
	data.userEmail = $('#userEmail').val();
	data.userPhone = $('#userPhone').val();
	
	$.ajax({
		url: '/user/info/modify',
		method: 'POST',
		dataType: 'json',
		contentType: 'application/json',
		data: JSON.stringify(data),
		success: function(res) {
			
			if(res.status) {
				
				alert("基本資料修改成功！");
				
				initData();
				
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
