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
	
	$.ajax({
		url: '/account/types/create/act',
		method: 'POST',
		dataType: 'json',
		contentType: 'application/json',
		data: JSON.stringify(data),
		success: function(res) {
			
			if(res.status) {
				
				showMsg('新增成功');
				
				if(!checkGuestDataCount()) {
					
					location.href = '/account/types';
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