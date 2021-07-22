$(function() {

	$('#cancelBtn').on('click', cancelAct);
	$('#confirmBtn').on('click', confirmAct);
});

function cancelAct() {
	
	location.href = '/account/types';
}

function confirmAct() {
	
	var itemName = $('#itemAccountTypeName').val();
	var itemDefault = $('input[name=itemAccountTypeDefault]:checked').val();
	
	var data = {};
	data.itemName = itemName;
	data.itemDefault = itemDefault;
	
	$.ajax({
		url: '/account/types/create/act',
		method: 'POST',
		dataType: 'json',
		contentType: 'application/json',
		data: JSON.stringify(data),
		success: function(res) {
			
			if(res.status) {
				
				alert('新增成功');
				location.href = '/account/types';
				
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