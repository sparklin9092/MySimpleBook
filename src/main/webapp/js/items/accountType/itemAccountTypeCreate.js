$(function() {

	$('#cancelBtn').on('click', cancelAct);
	$('#confirmBtn').on('click', confirmAct);
});

function cancelAct() {
	
	location.href = '/itemAccountType';
}

function confirmAct() {
	
	var itemName = $('#itemAccountTypeName').val();
	var itemDefault = $('input[name=itemAccountTypeDefault]:checked').val();
	
	var data = {};
	data.itemName = itemName;
	data.itemDefault = itemDefault;
	
	$.ajax({
		url: '/itemAccountType/create/act',
		method: 'POST',
		dataType: 'json',
		contentType: 'application/json',
		data: JSON.stringify(data),
		success: function(res) {
			
			console.log(res);
			
			if(res.status) {
				
				alert('新增成功');
				location.href = '/itemAccountType';
				
			} else {
				
				alert(res.msg);
			}
		},
		error: function(err) {
			console.log(err);
			alert('無法連接伺服器');
		}
	})
}