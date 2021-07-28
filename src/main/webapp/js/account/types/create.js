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
				
				alert('新增成功');
				
				if(!checkGuestDataCount()) {
					
					location.href = '/account/types';
				}
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