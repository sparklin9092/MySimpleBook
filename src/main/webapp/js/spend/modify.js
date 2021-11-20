$(function() {
	
	initSpendDate();
	initSpendItemSelect();
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

function initData() {
	
	var spendId = $('#spendId').val();
	
	postSubmit('/spend/one/' + spendId, {}, function(res) {
		if(res.status) {
			var spendDate = res.oneDto.spendDate;
			var amount = res.oneDto.amount;
			var spendItemId = res.oneDto.spendItemId;
			var accountId = res.oneDto.accountId;
			var remark = res.oneDto.remark;
			
			$('#spendDatePicker').val(moment(spendDate).format('YYYY年MM月DD日'));
			$('#spendDate').val(spendDate);
			$('#amount').val(amount);
			$('#remark').val(remark);
			
			$('#spendItemSelect').val(spendItemId).change();
			$('#accountItemSelect').val(accountId).change();
		} else {
			errMsg(res.msg);
		}
	});
}

function cancelAct() {
	history.back();
}
	
function deleteAct() {
	
	var deleteConfirm = confirm('確定要刪除嗎？');
	if(deleteConfirm) {
		var data = {};
		data.spendId = $('#spendId').val();
		postSubmit('/spend/delete/act', data, function(res) {
			if(res.status) {
				showMsg('刪除成功');
				location.href = '/spend/records';
			} else {
				errMsg(res.msg);
			}
		});
	}
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
	
	postSubmit('/spend/modify/act', data, function(res) {
		if(res.status) {
			showMsg('修改成功');
			initData();
		} else {
			errMsg(res.msg);
		}
	});
}