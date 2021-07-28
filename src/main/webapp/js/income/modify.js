$(function() {
	
	initIncomeDate();
	initIncomeItemSelect();
	initAccountItemSelect();
	
	initData();

	$('#cancelBtn').on('click', cancelAct);
	$('#deleteBtn').on('click', deleteAct);
	$('#confirmBtn').on('click', confirmAct);

	$('#amount').focus();

	$('#amount').on('keypress', function(e) {
		var code = (e.keyCode ? e.keyCode : e.which);
		if (code == 13) $('#confirmBtn').trigger('click');
	});
});

function initTodayDate() {
	
	$('#incomeDatePicker').val(moment().format('YYYY年MM月DD日'));
	$('#incomeDate').val(moment().format('YYYY-MM-DD'));
}

function initIncomeDate() {
	
	$('#incomeDatePicker').datepicker({
		dateFormat: 'yy年mm月dd日',
		showWeek: true,
		altField: '#incomeDate',
		altFormat: 'yy-mm-dd'
	});
	
	initTodayDate();
}

function initIncomeItemSelect() {
	
	$.ajax({
		url: '/income/itemList',
		method: 'POST',
		dataType: 'json',
		contentType: 'application/json',
		data: {},
		success: function(res) {
			
			if(res.status) {
				
				var itemList = res.itemList;
				var selectOptions = "";
				
				$.each(itemList, function(index, value){
					selectOptions += '<option value="' + value.itemId + '">' + value.itemName + '</option>';
				})
				
				$('#incomeItemSelect').empty().html(selectOptions);
				
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

function initAccountItemSelect() {
	
	$.ajax({
		url: '/income/accountList',
		method: 'POST',
		dataType: 'json',
		contentType: 'application/json',
		data: {},
		success: function(res) {
			
			if(res.status) {
				
				var itemList = res.accountList;
				var selOpts = "";
				
				$.each(itemList, function(index, value){
					selOpts += '<option value="' + value.accountId + '">' + value.accountName + '</option>';
				})
				
				$('#accountItemSelect').empty().html(selOpts);
				
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

function initData() {
	
	var incomeId = $('#incomeId').val();
	
	$.ajax({
		url: '/income/one/' + incomeId,
		method: 'POST',
		dataType: 'json',
		contentType: 'application/json',
		data: {},
		success: function(res) {
			
			if(res.status) {
				
				var incomeDate = res.oneDto.incomeDate;
				var amount = res.oneDto.amount;
				
				var incomeItemId = res.oneDto.incomeItemId;
				var accountId = res.oneDto.accountId;
				
				var remark = res.oneDto.remark;
				
				$('#incomeDatePicker').val(moment(incomeDate).format('YYYY年MM月DD日'));
				$('#amount').val(amount);
				
				$('#incomeItemSelect').val(incomeItemId).change();
				$('#accountItemSelect').val(accountId).change();
				
				$('#remark').val(remark);
				
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
	
	location.href = '/income/records';
}
	
function deleteAct() {
	
	var deleteConfirm = confirm('確定要刪除嗎？');
	
	if(deleteConfirm) {
	
		var data = {};
		data.incomeId = $('#incomeId').val();
		
		$.ajax({
			url: '/income/delete/act',
			method: 'POST',
			dataType: 'json',
			contentType: 'application/json',
			data: JSON.stringify(data),
			success: function(res) {
				
				if(res.status) {
					
					alert('刪除成功');
					location.href = '/income/records';
					
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
	
	var incomeId = $('#incomeId').val();
	var incomeDate = $('#incomeDate').val();
	var incomeItemId = $('#incomeItemSelect').val();
	var accountId = $('#accountItemSelect').val();
	var amount = $('#amount').val();
	var remark = $('#remark').val();
	
	if(!checkAmnt(amount)) return;
	
	var data = {};
	data.incomeId = incomeId;
	data.incomeDate = incomeDate;
	data.incomeItemId = incomeItemId;
	data.accountId = accountId;
	data.amount = amount;
	data.remark = remark;
	
	$.ajax({
		url: '/income/modify/act',
		method: 'POST',
		dataType: 'json',
		contentType: 'application/json',
		data: JSON.stringify(data),
		success: function(res) {
			
			if(res.status) {
				
				alert('修改成功');
				location.href = '/income/records';
				
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