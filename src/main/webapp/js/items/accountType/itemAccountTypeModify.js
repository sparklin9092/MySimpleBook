$(function() {

	initData();
	$('#cancelBtn').on('click', cancelAct);
	$('#deleteBtn').on('click', deleteAct);
	$('#confirmBtn').on('click', confirmAct);
});

function initData() {
	
	var itemId = $('#itemId').val();
	
	$.ajax({
		url: '/itemAccountType/one/' + itemId,
		method: 'POST',
		dataType: 'json',
		contentType: 'application/json',
		data: {},
		success: function(res) {
			
			console.log(res);
			
			if(res.status) {
				
				var itemName = res.itemAccountTypeOneDto.itemName;
				var itemActive = res.itemAccountTypeOneDto.itemActive;
				var itemDefault = res.itemAccountTypeOneDto.itemDefault;
				var createDateTime = res.itemAccountTypeOneDto.createDateTime;
				
				$('#itemAccountTypeName').val(itemName);
				
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
				
				$('#createDateTime').val(moment.utc(createDateTime).format('YYYY年MM月DD日'));
				
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
	
	location.href = '/itemAccountType';
}
	
function deleteAct() {
	
	var data = {};
	data.itemId = $('#itemId').val();
	
	$.ajax({
		url: '/itemAccountType/delete/act',
		method: 'POST',
		dataType: 'json',
		contentType: 'application/json',
		data: JSON.stringify(data),
		success: function(res) {
			
			console.log(res);
			
			if(res.status) {
				
				alert('刪除成功');
				location.href = '/itemAccountType';
				
			} else {
				
				alert(msg);
			}
		},
		error: function(err) {
			console.log(err);
			alert('無法連接伺服器');
		}
	});
}

function confirmAct() {
	
	var data = {};
	data.itemId = $('#itemId').val();
	data.itemName = $('#itemAccountTypeName').val();
	data.itemActive = $('input[name=itemAccountTypeActive]:checked').val();
	data.itemDefault = $('input[name=itemAccountTypeDefault]:checked').val();
	
	$.ajax({
		url: '/itemAccountType/modify/act',
		method: 'POST',
		dataType: 'json',
		contentType: 'application/json',
		data: JSON.stringify(data),
		success: function(res) {
			
			console.log(res);
			
			if(res.status) {
				
				alert('修改成功');
				location.href = '/itemAccountType';
				
			} else {
				
				alert(msg);
			}
		},
		error: function(err) {
			console.log(err);
			alert('無法連接伺服器');
		}
	});
}