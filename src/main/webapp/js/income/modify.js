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
	
	postSubmit('/income/itemList', {}, function(res) {
		if(res.status) {
			var itemList = res.itemList;
			var selectOptions = "";
			$.each(itemList, function(index, value){
				selectOptions += '<option value="' + value.itemId + '">' + value.itemName + '</option>';
			});
			$('#incomeItemSelect').empty().html(selectOptions);
		} else {
			errMsg(res.msg);
		}
	});
}

function initAccountItemSelect() {
	
	postSubmit('/income/accountList', {}, function(res) {
		if(res.status) {
			var itemList = res.accountList;
			var selOpts = "";
			$.each(itemList, function(index, value){
				selOpts += '<option value="' + value.accountId + '">' + value.accountName + '</option>';
			});
			$('#accountItemSelect').empty().html(selOpts);
		} else {
			errMsg(res.msg);
		}
	});
}

function initData() {
	
	var incomeId = $('#incomeId').val();
	postSubmit('/income/one/' + incomeId, {}, function(res) {
		if(res.status) {
			var incomeDate = res.oneDto.incomeDate;
			var amount = res.oneDto.amount;
			var incomeItemId = res.oneDto.incomeItemId;
			var accountId = res.oneDto.accountId;
			var remark = res.oneDto.remark;
			
			$('#incomeDatePicker').val(moment(incomeDate).format('YYYY年MM月DD日'));
			$('#amount').val(amount);
			$('#remark').val(remark);
			
			$('#incomeItemSelect').val(incomeItemId).change();
			$('#accountItemSelect').val(accountId).change();
		} else {
			errMsg(res.msg);
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
		postSubmit('/income/delete/act', data, function(res) {
			if(res.status) {
				showMsg('刪除成功');
				location.href = '/income/records';
			} else {
				errMsg(res.msg);
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
	
	postSubmit('/income/modify/act', data, function(res) {
		if(res.status) {
			showMsg('修改成功');
			initData();
		} else {
			errMsg(res.msg);
		}
	});
}