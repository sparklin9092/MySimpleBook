$(function() {

	initData();
	$('#cancelBtn').on('click', cancelAct);
	$('#deleteBtn').on('click', deleteAct);
	$('#confirmBtn').on('click', confirmAct);
});

function initData() {
	
	var itemId = $('#itemId').val();
	
	$.ajax({
		url: '/itemSpend/one/' + itemId,
		method: 'POST',
		dataType: 'json',
		contentType: 'application/json',
		data: {},
		success: function(res) {
			
			console.log(res);
			
			if(res.status) {
				
				var itemName = res.itemSpendOneDto.itemName;
				var itemActive = res.itemSpendOneDto.itemActive;
				var itemDefault = res.itemSpendOneDto.itemDefault;
				var createUserName = res.itemSpendOneDto.createUserName;
				var createDateTime = res.itemSpendOneDto.createDateTime;
				
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
				
				$('#createUserName').val(createUserName);
				$('#createDateTime').val(moment.utc(createDateTime).format('YYYY年MM月DD日 hh:mm:ss'));
				
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
	
	location.href = '/itemSpend';
}
	
function deleteAct() {
	
	var data = {};
	data.itemId = $('#itemId').val();
	
	$.ajax({
		url: '/itemSpend/delete/act',
		method: 'POST',
		dataType: 'json',
		contentType: 'application/json',
		data: JSON.stringify(data),
		success: function(res) {
			
			console.log(res);
			
			if(res.status) {
				
				alert('刪除成功');
				location.href = '/itemSpend';
				
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
	data.itemName = $('#itemSpendName').val();
	data.itemActive = $('input[name=itemSpendActive]:checked').val();
	data.itemDefault = $('input[name=itemSpendDefault]:checked').val();
	
	$.ajax({
		url: '/itemSpend/modify/act',
		method: 'POST',
		dataType: 'json',
		contentType: 'application/json',
		data: JSON.stringify(data),
		success: function(res) {
			
			console.log(res);
			
			if(res.status) {
				
				alert('修改成功');
				location.href = '/itemSpend';
				
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