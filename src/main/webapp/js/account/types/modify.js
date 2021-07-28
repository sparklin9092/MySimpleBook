$(function() {

	initData();
	$('#cancelBtn').on('click', cancelAct);
	$('#deleteBtn').on('click', deleteAct);
	$('#confirmBtn').on('click', confirmAct);
});

function initData() {
	
	var typeId = $('#typeId').val();
	
	$.ajax({
		url: '/account/types/one/' + typeId,
		method: 'POST',
		dataType: 'json',
		contentType: 'application/json',
		data: {},
		success: function(res) {
			
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
				
				alert(res.msg);
			}
		},
		error: function(err) {
			console.log(err);
			alert('無法連接伺服器');
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
	
		$.ajax({
			url: '/account/types/delete/act',
			method: 'POST',
			dataType: 'json',
			contentType: 'application/json',
			data: JSON.stringify(data),
			success: function(res) {
				
				if(res.status) {
					
					alert('刪除成功');
					location.href = '/account/types';
					
				} else {
					
					alert(res.msg);
				}
			},
			error: function(err) {
				console.log(err);
				alert('無法連接伺服器');
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
	
	$.ajax({
		url: '/account/types/modify/act',
		method: 'POST',
		dataType: 'json',
		contentType: 'application/json',
		data: JSON.stringify(data),
		success: function(res) {
			
			if(res.status) {
				
				alert('修改成功');
				location.href = '/account/types';
				
			} else {
				
				alert(res.msg);
			}
		},
		error: function(err) {
			console.log(err);
			alert('無法連接伺服器');
		}
	});
}