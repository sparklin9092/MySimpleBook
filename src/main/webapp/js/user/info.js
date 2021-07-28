$(function() {

	initData();
	
	$('#changePwd').on('click', changePwdView);
	$('#bindEmail').on('click', bindEmailAct);
	$('#deleteBtn').on('click', deleteAct);
	$('#confirmBtn').on('click', confirmAct);
});

var emailStatus = 2;

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
				emailStatus = res.dto.emailStatus;
				var createDate = res.dto.createDate;
				
				$('#userName').val(userName);
				$('#userAcc').val(userAcc);
				$('#userPwd').val(maskPwd);
				$('#userEmail').val(userEmail);
				$('#createDate').val(createDate);
				
				if(emailStatus == 1) {
					
					$('#bindEmail').addClass('btn-success').removeClass('btn-warning');
					$('#bindEmail').prop('disabled', true).text('已寄發');
					
				} else if(emailStatus == 2) {
					
					$('#bindEmail').addClass('btn-success').removeClass('btn-warning');
					$('#bindEmail').prop('disabled', false).text('綁定');
					
				} else if(emailStatus == 3) {
					
					$('#bindEmail').addClass('btn-success').removeClass('btn-warning');
					$('#bindEmail').prop('disabled', true).text('已認證');
					
				} else {
					
					$('#bindEmail').addClass('btn-success').removeClass('btn-warning');
					$('#bindEmail').prop('disabled', false).text('綁定');
					
				}
				
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
	
	$('#bindEmail').addClass('btn-warning').removeClass('btn-success');
	$('#bindEmail').prop('disabled', true).text('確認中');
	
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
					
					alert("已經寄發認證碼到您的信箱，輸入認證碼確認後，綁定就完成了！");
				
					initData();
					
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
	
	var modifyConfirm = true;
	if(emailStatus == 3) {
		
		modifyConfirm = confirm('您的電子信箱(Email)已認證，修改為不同的電子信箱(Email)，'
						+'會需要您重新綁定，請問是否要繼續修改嗎？')
	}
	
	if(modifyConfirm) {
	
		var data = {};
		data.userName = $('#userName').val();
		data.userEmail = $('#userEmail').val();
		
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
}
