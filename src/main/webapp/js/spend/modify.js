$(function() {
	
	initSpendDate();
	initSpendItemSelect();
	initAccountItemSelect();
	
	initData();

	$('#cancelBtn').on('click', cancelAct);
	$('#deleteBtn').on('click', deleteAct);
	$('#confirmBtn').on('click', confirmAct);
});

function initTodayDate() {
	
	$('#spendDatePicker').val(moment().format('YYYY年MM月DD日'));
	$('#spendDate').val(moment().format('YYYY-MM-DD'));
}

function initSpendDate() {
	
	$('#spendDatePicker').datepicker({
		dateFormat: 'yy年mm月dd日',
		showWeek: true,
		altField: '#spendDate',
		altFormat: 'yy-mm-dd'
	});
	
	initTodayDate();
}

function initSpendItemSelect() {
	
	$.ajax({
		url: '/spend/itemList',
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
				
				$('#spendItemSelect').empty().html(selectOptions);
				
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
		url: '/spend/accountList',
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
	
	var spendId = $('#spendId').val();
	
	$.ajax({
		url: '/spend/one/' + spendId,
		method: 'POST',
		dataType: 'json',
		contentType: 'application/json',
		data: {},
		success: function(res) {
			
			console.log(res);
			
			if(res.status) {
				
				var spendDate = res.oneDto.spendDate;
				var amount = res.oneDto.amount;
				
				var spendItemId = res.oneDto.spendItemId;
				var accountId = res.oneDto.accountId;
				
				var createDateTime = res.oneDto.createDateTime;
				var remark = res.oneDto.remark;
				
				$('#spendDatePicker').val(moment(spendDate).format('YYYY年MM月DD日'));
				$('#amount').val(amount);
				
				$('#spendItemSelect').val(spendItemId).change();
				$('#accountItemSelect').val(accountId).change();
				
				$('#createDateTime').val(moment.utc(createDateTime).format('YYYY年MM月DD日'));
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
	
	location.href = '/spend/records';
}
	
function deleteAct() {
	
	var data = {};
	data.spendId = $('#spendId').val();
	
	$.ajax({
		url: '/spend/delete/act',
		method: 'POST',
		dataType: 'json',
		contentType: 'application/json',
		data: JSON.stringify(data),
		success: function(res) {
			
			if(res.status) {
				
				alert('刪除成功');
				location.href = '/spend/records';
				
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

function confirmAct() {
	
	var spendId = $('#spendId').val();
	var spendDate = $('#spendDate').val();
	var spendItemId = $('#spendItemSelect').val();
	var accountId = $('#accountItemSelect').val();
	var amount = $('#amount').val();
	var remark = $('#remark').val();
	
	if(!checkAmnt(amount)) return;
	
	var data = {};
	data.spendId = spendId;
	data.spendDate = spendDate;
	data.spendItemId = spendItemId;
	data.accountId = accountId;
	data.amount = amount;
	data.remark = remark;
	
	$.ajax({
		url: '/spend/modify/act',
		method: 'POST',
		dataType: 'json',
		contentType: 'application/json',
		data: JSON.stringify(data),
		success: function(res) {
			
			if(res.status) {
				
				alert('修改成功');
				location.href = '/spend/records';
				
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