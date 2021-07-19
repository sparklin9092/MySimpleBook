$(function() {
	
	initIncomeDate();
	initIncomeItemSelect();
	initAccountItemSelect();

	$('#cancelBtn').on('click', cancelAct);
	$('#confirmBtn').on('click', confirmAct);
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

function cancelAct() {
	
	location.href = '/main';
}

function confirmAct() {
	
	var incomeDate = $('#incomeDate').val();
	var incomeItemId = $('#incomeItemSelect').val();
	var accountItemId = $('#accountItemSelect').val();
	var amount = $('#amount').val();
	var remark = $('#remark').val();
	
	if(!checkAmnt(amount)) return;
	
	var data = {};
	data.incomeDate = incomeDate;
	data.incomeItemId = incomeItemId;
	data.accountItemId = accountItemId;
	data.amount = amount;
	data.remark = remark;
	
	$.ajax({
		url: '/income/create/act',
		method: 'POST',
		dataType: 'json',
		contentType: 'application/json',
		data: JSON.stringify(data),
		success: function(res) {
			
			if(res.status) {
				
				var next = confirm('新增成功，要繼續新增下一筆收入嗎？');

				if(next) {

					initTodayDate();
					initIncomeItemSelect();
					initAccountItemSelect();
					$('#amount, #remark').val('');
					
				} else {
	
					location.href = '/main';
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