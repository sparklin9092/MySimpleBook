$(function() {

	initData();
	
	$('#userEmail').on('blur', checkSameEmail);
	
	$('#changePwd').on('click', changePwdView);
	$('#bindEmail').on('click', bindEmailAct);
	$('#deleteBtn').on('click', deleteAct);
	$('#confirmBtn').on('click', confirmAct);
});

var userEmail = "";
var emailStatus = 2;

function initData() {
	
	postSubmit('/user/info', {}, function(res) {
		if(res.status) {
			var userName = res.dto.userName;
			var userAcc = res.dto.userAcc;
			var maskPwd = res.dto.maskPwd;
			userEmail = res.dto.userEmail;
			emailStatus = res.dto.emailStatus;
			var createDate = res.dto.createDate;
			var userBtnTxt = userName.substring(0,1);
			
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
			$('#userInfo').text(userBtnTxt);
		} else {
			errMsg(res.msg);
		}
	});
}

function checkSameEmail() {
	
	if(emailStatus == 3 && $('#userEmail').val() == userEmail) {
					
		$('#bindEmail').addClass('btn-success').removeClass('btn-warning');
		$('#bindEmail').prop('disabled', true).text('已認證');
		
	} else if(emailStatus == 3 && $('#userEmail').val() != userEmail) {
					
		$('#bindEmail').addClass('btn-success').removeClass('btn-warning');
		$('#bindEmail').prop('disabled', false).text('重新綁定');
		
	}
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
		
		postSubmit('/user/info/bind/email', data, function(res) {
			if(res.status) {
				showMsg("已經寄發認證碼到您的信箱，輸入認證碼確認後，綁定就完成了！");
				initData();
			} else {
				$('#bindEmail').addClass('btn-success').removeClass('btn-warning');
				$('#bindEmail').prop('disabled', false).text('綁定');
				errMsg(res.msg);
			}
		});
	}, 1000);
}

function deleteAct() {
	
	var deleteConfirm = confirm('警告：您確定要刪除帳號嗎？系統將會徹底刪除您的所有資料，而且無法復原！！！');
	if(deleteConfirm) {
		postSubmit('/user/info/delete', {}, function(res) {
			if(res.status) {
				showMsg("您的帳號資料已全數刪除！謝謝您使用「致富寶典」！");
				location.href = '/logout';
			} else {
				errMsg(res.msg);
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
		
		postSubmit('/user/info/modify', data, function(res) {
			if(res.status) {
				showMsg("基本資料修改成功！");
				initData();
			} else {
				errMsg(res.msg);
			}
		});
	}
}
