$(function() {

	$('#cancelBtn').on('click', cancelAct);
	$('#confirmBtn').on('click', confirmAct);
});

function cancelAct() {
	
	location.href = '/user/info';
}

function confirmAct() {
	
	var oldPwd = $('#oldPwd').val();
	var newPwd = $('#newPwd').val();
	
	var data = {};
	data.oldPwd = oldPwd;
	data.newPwd = newPwd;
	
	postSubmit('/user/info/pwd/change', data, function(res) {
		if(res.status) {
			showMsg("密碼修改成功！請重新登入。");
			location.href = '/logout';
		} else {
			errMsg(res.msg);
		}
	});
}
