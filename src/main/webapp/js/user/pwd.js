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
	
	$.ajax({
		url: '/user/info/pwd/change',
		method: 'POST',
		dataType: 'json',
		contentType: 'application/json',
		data: JSON.stringify(data),
		success: function(res) {
			
			if(res.status) {
				
				alert("密碼修改成功");
				location.href = '/user/info';
				
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
