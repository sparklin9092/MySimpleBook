$(function() {

	$('#cancelBtn').on('click', cancelAct);
	$('#confirmBtn').on('click', confirmAct);
});

function cancelAct() {
	
	location.href = '/income/items';
}

function confirmAct() {
	
	var itemName = $('#itemIncomeName').val();
	var itemDefault = $('input[name=itemIncomeDefault]:checked').val();
	
	var data = {};
	data.itemName = itemName;
	data.itemDefault = itemDefault;
	
	$.ajax({
		url: '/income/items/create/act',
		method: 'POST',
		dataType: 'json',
		contentType: 'application/json',
		data: JSON.stringify(data),
		success: function(res) {
			
			console.log(res);
			
			if(res.status) {
				
				alert('新增成功');
				location.href = '/income/items';
				
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