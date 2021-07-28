$(function() {

	initData();
	$('#cancelBtn').on('click', cancelAct);
	$('#deleteBtn').on('click', deleteAct);
	$('#confirmBtn').on('click', confirmAct);
});

function initData() {
	
	var itemId = $('#itemId').val();
	
	$.ajax({
		url: '/income/items/one/' + itemId,
		method: 'POST',
		dataType: 'json',
		contentType: 'application/json',
		data: {},
		success: function(res) {
			
			console.log(res);
			
			if(res.status) {
				
				var itemName = res.itemName;
				var itemActive = res.itemActive;
				var itemDefault = res.itemDefault;
				var createDateTime = res.createDateTime;
				
				$('#itemIncomeName').val(itemName);
				
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
	
	location.href = '/income/items';
}
	
function deleteAct() {
	
	var deleteConfirm = confirm('確定要刪除嗎？');
	
	if(deleteConfirm) {
	
		var data = {};
		data.itemId = $('#itemId').val();
		
		$.ajax({
			url: '/income/items/delete/act',
			method: 'POST',
			dataType: 'json',
			contentType: 'application/json',
			data: JSON.stringify(data),
			success: function(res) {
				
				if(res.status) {
					
					alert('刪除成功');
					location.href = '/income/items';
					
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
	data.itemId = $('#itemId').val();
	data.itemName = $('#itemIncomeName').val();
	data.itemActive = $('input[name=itemIncomeActive]:checked').val();
	data.itemDefault = $('input[name=itemIncomeDefault]:checked').val();
	
	$.ajax({
		url: '/income/items/modify/act',
		method: 'POST',
		dataType: 'json',
		contentType: 'application/json',
		data: JSON.stringify(data),
		success: function(res) {
			
			if(res.status) {
				
				alert('修改成功');
				location.href = '/income/items';
				
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