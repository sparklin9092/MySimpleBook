$(function() {

	$.ajax({
		url: '/userCheck',
		method: 'GET',
		dataType: 'json',
		contentType: 'application/json',
		data: {},
		success: function(res) {
			
			console.log(res);
			
			if(!res.status) {
				
				alert("使用者未登入");
			}
		},
		error: function(err) {
			console.log(err);
			alert('無法連接伺服器');
		}
	});
});