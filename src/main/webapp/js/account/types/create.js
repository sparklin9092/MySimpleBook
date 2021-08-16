$(function() {

	$('#cancelBtn').on('click', cancelAct);
	$('#confirmBtn').on('click', confirmAct);
});

function cancelAct() {
	
	location.href = '/account/types';
}

function confirmAct() {
	
	var typeName = $('#accountTypeName').val();
	var typeDefault = $('input[name=accountTypeDefault]:checked').val();
	
	var data = {};
	data.typeName = typeName;
	data.typeDefault = typeDefault;
	
	postSubmit('/account/types/create/act', data, function(res) {
		if(res.status) {
			showMsg('新增成功');
			if(!checkGuestDataCount()) {
				location.href = '/account/types';
			}
		} else {
			errMsg(res.msg);
		}
	});
}