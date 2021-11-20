$(function() {

	initData();
	$('#cancelBtn').on('click', cancelAct);
	$('#deleteBtn').on('click', deleteAct);
	$('#confirmBtn').on('click', confirmAct);
});

function initData() {
	
	var itemId = $('#itemId').val();
	
	postSubmit('/spend/items/one/' + itemId, {}, function(res) {
		if(res.status) {
			var itemName = res.itemName;
			var itemActive = res.itemActive;
			var itemDefault = res.itemDefault;
			var createDateTime = res.createDateTime;
			
			$('#itemSpendName').val(itemName);
			
			if(itemActive) {
				$('#activeTrue').prop('checked', true);
			} else {
				$('#activeFalse').prop('checked', true);
			}
			
			if(itemDefault) {
				$('#defaultTrue').prop('checked', true);
			} else {
				$('#defaultFalse').prop('checked', true);
			}
			
			$('#createDateTime').val(createDateTime);
		} else {
			errMsg(res.msg);
		}
	});
}

function cancelAct() {
	history.back();
}
	
function deleteAct() {
	
	var deleteConfirm = confirm('確定要刪除嗎？');
	
	if(deleteConfirm) {
	
		var data = {};
		data.itemId = $('#itemId').val();
		
		postSubmit('/spend/items/delete/act', data, function(res) {
			if(res.status) {
				showMsg('刪除成功');
				location.href = '/spend/items';
			} else {
				errMsg(res.msg);
			}
		});
	}
}

function confirmAct() {
	
	var data = {};
	data.itemId = $('#itemId').val();
	data.itemName = $('#itemSpendName').val();
	data.itemActive = $('input[name=itemSpendActive]:checked').val();
	data.itemDefault = $('input[name=itemSpendDefault]:checked').val();
	
	postSubmit('/spend/items/modify/act', data, function(res) {
		if(res.status) {
			showMsg('修改成功');
			initData();
		} else {
			errMsg(res.msg);
		}
	});
}