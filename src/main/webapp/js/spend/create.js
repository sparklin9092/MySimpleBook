$(function() {
	
	initSpendDate();
	initSpendItemSelect();
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
	
	postSubmit('/spend/itemList', {}, function(res) {
		if(res.status) {
			var itemList = res.itemList;
			var selectOptions = "";
			$.each(itemList, function(index, value){
				selectOptions += '<option value="' + value.itemId + '">' + value.itemName + '</option>';
			});
			$('#spendItemSelect').empty().html(selectOptions);
		} else {
			errMsg(res.msg);
		}
	});
}

function initAccountItemSelect() {
	
	postSubmit('/spend/accountList', {}, function(res) {
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

function cancelAct() {
	
	location.href = '/main';
}

function confirmAct() {
	
	var spendDate = $('#spendDate').val();
	var spendItemId = $('#spendItemSelect').val();
	var accountItemId = $('#accountItemSelect').val();
	var amount = $('#amount').val();
	var remark = $('#remark').val();
	
	if(!checkAmnt(amount)) return;
	
	var data = {};
	data.spendDate = spendDate;
	data.spendItemId = spendItemId;
	data.accountItemId = accountItemId;
	data.amount = amount;
	data.remark = remark;
	
	postSubmit('/spend/create/act', data, function(res) {
		if(res.status) {
			showMsg('新增成功！');
			if(!checkGuestDataCount()) {
				initTodayDate();
				initSpendItemSelect();
				initAccountItemSelect();
				$('#amount, #remark').val('');
			}
		} else {
			errMsg(res.msg);
		}
	});
}