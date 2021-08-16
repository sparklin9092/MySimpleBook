$(function() {

	$('#cancelBtn').on('click', cancelAct);
	$('#confirmBtn').on('click', confirmAct);
});

function cancelAct() {
	
	location.href = '/spend/items';
}

function confirmAct() {
	
	var itemName = $('#itemSpendName').val();
	var itemDefault = $('input[name=itemSpendDefault]:checked').val();
	
	var data = {};
	data.itemName = itemName;
	data.itemDefault = itemDefault;
	
	$.ajax({
		url: '/spend/items/create/act',
		method: 'POST',
		dataType: 'json',
		contentType: 'application/json',
		data: JSON.stringify(data),
		success: function(res) {
			
			if(res.status) {
				
				showMsg('新增成功');
				
				if(!checkGuestDataCount()) {
					
					location.href = '/spend/items';
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