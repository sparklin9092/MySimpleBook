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
			
			if(res.status) {
				
				showMsg('新增成功');
				
				if(!checkGuestDataCount()) {
					
					location.href = '/income/items';
				}
			} else {
				
				errMsg(res.msg);
			}
		},
		error: function(err) {
			sysMsg('無法連接伺服器');
		}
	})
}