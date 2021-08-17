$(function() {

	initIncomeDate();
	initIncomeItemSelect();
	initAccountItemSelect();

	$('#cancelBtn').on('click', cancelAct);
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
		if (res.status) {
			var itemList = res.itemList;
			var selectOptions = "";
			$.each(itemList, function(index, value) {
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
		if (res.status) {
			var itemList = res.accountList;
			var selOpts = "";
			$.each(itemList, function(index, value) {
				selOpts += '<option value="' + value.accountId + '">' + value.accountName + '</option>';
			});
			$('#accountItemSelect').empty().html(selOpts);
		} else {
			errMsg(res.msg);
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

	if (!checkAmnt(amount)) return;

	var data = {};
	data.incomeDate = incomeDate;
	data.incomeItemId = incomeItemId;
	data.accountItemId = accountItemId;
	data.amount = amount;
	data.remark = remark;
	
	postSubmit('/income/create/act', data, function(res) {
		if (res.status) {
			showMsg('新增成功！');
			if (!checkGuestDataCount()) {
				initTodayDate();
				initIncomeItemSelect();
				initAccountItemSelect();
				$('#amount, #remark').val('');
			}
		} else {
			errMsg(res.msg);
		}
	});
}