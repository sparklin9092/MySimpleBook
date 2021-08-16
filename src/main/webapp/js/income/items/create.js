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
	
	postSubmit('/income/items/create/act', data, function(res) {
		if(res.status) {
			showMsg('新增成功');
			if(!checkGuestDataCount()) location.href = '/income/items';
		} else {
			errMsg(res.msg);
		}
	});
}