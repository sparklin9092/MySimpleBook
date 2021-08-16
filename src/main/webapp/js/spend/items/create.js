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
	
	postSubmit('/spend/items/create/act', data, function(res) {
		if(res.status) {
			showMsg('新增成功');
			if(!checkGuestDataCount()) location.href = '/spend/items';
		} else {
			errMsg(res.msg);
		}
	});
}