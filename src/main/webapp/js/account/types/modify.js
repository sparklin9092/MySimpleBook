$(function() {

	initData();
	$('#cancelBtn').on('click', cancelAct);
	$('#deleteBtn').on('click', deleteAct);
	$('#confirmBtn').on('click', confirmAct);
});

function initData() {
	
	var typeId = $('#typeId').val();
	
	postSubmit('/account/types/one/' + typeId, {}, function(res) {
		if(res.status) {
			var typeName = res.typeName;
			var typeActive = res.typeActive;
			var typeDefault = res.typeDefault;
			var createDateTime = res.createDateTime;
			
			$('#accountTypeName').val(typeName);
			
			if(typeActive) {
				$('#activeTrue').prop('checked', true);
			} else {
				$('#activeFalse').prop('checked', true);
			}
			
			if(typeDefault) {
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
	
	location.href = '/account/types';
}
	
function deleteAct() {
	
	var deleteConfirm = confirm('確定要刪除嗎？');
	
	if(deleteConfirm) {
	
		var data = {};
		data.typeId = $('#typeId').val();
	
		postSubmit('/account/types/delete/act', data, function(res) {
			if(res.status) {
				showMsg('刪除成功');
				location.href = '/account/types';
			} else {
				errMsg(res.msg);
			}
		});
	}
}

function confirmAct() {
	
	var data = {};
	data.typeId = $('#typeId').val();
	data.typeName = $('#accountTypeName').val();
	data.typeActive = $('input[name=accountTypeActive]:checked').val();
	data.typeDefault = $('input[name=accountTypeDefault]:checked').val();
	
	postSubmit('/account/types/modify/act', data, function(res) {
		if(res.status) {
			showMsg('修改成功');
			initData();
		} else {
			errMsg(res.msg);
		}
	});
}