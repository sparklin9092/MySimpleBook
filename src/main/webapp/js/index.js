$(function() {

	$('#loginBtn').on('click', loginAct);
	
	$('#guestBtn').on('click', guestAct);

	$('#userAcc, #userPwd').on('keypress', function(e) {
		var code = (e.keyCode ? e.keyCode : e.which);
		if (code == 13) $('#loginBtn').trigger('click');
	});
	
	if ($.cookie('checkGuest')) {
		
		$.removeCookie('checkGuest');
	}
	
	checkUserMailExist();
	rememberAccVal();
	
	$('#rememberAcc').on('click', rememberAccAct);
});

function loginAct() {
	
	rememberAccAct();
	
	var data = {};
	data.userAcc = $('#userAcc').val();
	data.userPwd = $('#userPwd').val();
	
	$.ajax({
		url: '/login',
		method: 'POST',
		dataType: 'json',
		contentType: 'application/json',
		data: JSON.stringify(data),
		success: function(res) {
			if(res.status) {
				location.href = '/main';
			} else {
				alert(res.msg);
				$('#userPwd').val('');
			}
		},
		error: function(err) {
			console.log(err);
			alert('無法連接伺服器');
		}
	});
}

function guestAct() {
	
	$.ajax({
		url: '/login/guest',
		method: 'POST',
		dataType: 'json',
		contentType: 'application/json',
		data: {},
		success: function(res) {
			if(res.status) {
				location.href = '/main';
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

function checkUserMailExist() {
	
	var userMail = $('#userMail').val();
	
	if(userMail) {
		
		$('#userAcc').val(userMail);
	}
}

function rememberAccAct() {
	
	var isCheck = $('#rememberAcc').prop('checked');
	
	if(isCheck) {

		var userAcc = $('#userAcc').val();

		$.cookie('rememberAcc', userAcc);
		
	} else {
		
		$.removeCookie('rememberAcc');
	}
}

function rememberAccVal() {
	
	if($.cookie('rememberAcc')) {
		
		var cookieAcc = $.cookie('rememberAcc');
		
		$('#userAcc').val(cookieAcc);
	}
}
