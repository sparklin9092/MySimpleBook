$(function() {

	$('#loginBtn').on('click', loginAct);
	$('#guestBtn').on('click', guestAct);
	
	if ($.cookie('checkGuest')) $.removeCookie('checkGuest');
	
	checkUserExist();
	rememberAccVal();
	checkLoginStatus();
	checkDesktop();

	$('#userAcc, #userPwd').on('keypress', function(e) {
		var code = (e.keyCode ? e.keyCode : e.which);
		if (code == 13) $('#loginBtn').trigger('click');
	});
});

function loginAct() {
	
	rememberAccAct();
	
	var data = {};
	data.userAcc = $('#userAcc').val();
	data.userPwd = $('#userPwd').val();
	
	postSubmit('/login', data, function(res) {
		if(res.status) {
			location.href = '/main';
			$.cookie('loginStatus', true);
		} else {
			errMsg(res.msg);
			$('#userPwd').val('');
		}
	});
}

function guestAct() {
	
	postSubmit('/login/guest', {}, function(res) {
		if(res.status) {
			location.href = '/main';
			$.cookie('loginStatus', true);
		} else {
			showMsg(res.msg);
		}
	});
}

function checkUserExist() {
	
	var user = $('#user').val();
	if(user) $('#userAcc').val(user);
}

function rememberAccAct() {
	
	var userAcc = $('#userAcc').val();
	$.cookie('rememberAcc', userAcc);
}

function rememberAccVal() {
	
	if($.cookie('rememberAcc')) {
		var cookieAcc = $.cookie('rememberAcc');
		$('#userAcc').val(cookieAcc);
	}
}

function checkLoginStatus() {
	
	if($.cookie('loginStatus')) {
		postSubmit('/login/check', {}, function(res) {
			if(res.status) {
				location.href = '/main';
			}
		});
	}
}

function checkDesktop() {
	if(!isMobile()) {
		$('#socialCard a').prop('target', '_blank');
	} else {
		$('#socialCard a').removeProp('target');
	}
}
