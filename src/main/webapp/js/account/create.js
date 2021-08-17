$(function() {

	initAccountTypeSelect();
	initLimitYearMonth();
	
	$('#enableLimitDate').on('click', enableLimitDatefield);

	$('#cancelBtn').on('click', cancelAct);
	$('#confirmBtn').on('click', confirmAct);

	$('#initAmnt').focus();

	$('#initAmnt').on('keypress', function(e) {
		var code = (e.keyCode ? e.keyCode : e.which);
		if (code == 13) $('#confirmBtn').trigger('click');
	});
});

function initAccountTypeSelect() {
	
	postSubmit('/account/typeList', {}, function(res) {
		if(res.status) {
			var typeList = res.accountTypeListDto;
			var selectOptions = "";
			$.each(typeList, function(index, value){
				selectOptions += '<option value="' + value.typeId + '">' + value.typeName + '</option>';
			})
			$('#accountTypeSelect').html(selectOptions);
		} else {
			errMsg(res.msg);
		}
	});
}

function initLimitYearMonth() {
	
	var yearNow = parseInt(moment().add(5, 'year').format('YYYY'));
	initYearSelect('limitYear', yearNow, 50);
	
	var monthNow = parseInt(moment().format('MM'));
	initMonthSelect('limitMonth', monthNow);
}

function enableLimitDatefield() {
	
	var enableChecked = $('#enableLimitDate').prop('checked');
	
	if(enableChecked) {
		$('#limitYear, #limitMonth').prop('disabled', false);
	} else {
		$('#limitYear, #limitMonth').prop('disabled', true);
	}
}

function cancelAct() {
	
	location.href = '/account';
}

function confirmAct() {
	
	var accountType = $('#accountTypeSelect').val();
	var accountName = $('#accountName').val();
	var initAmnt = $('#initAmnt').val();
	var accountDefault = $('input[name=accountDefault]:checked').val();
	var enableLimitDate = $('#enableLimitDate').prop('checked');
	var limitYear = "";
	var limitMonth = "";
	
	if(!checkAmnt(initAmnt)) return;
	
	if(enableLimitDate) {
		limitYear = $('#limitYear').val();
		limitMonth = $('#limitMonth').val();
	}
	
	var data = {};
	data.accountType = accountType;
	data.accountName = accountName;
	data.initAmnt = initAmnt;
	data.accountDefault = accountDefault;
	data.enableLimitDate = enableLimitDate;
	data.limitYear = limitYear;
	data.limitMonth = limitMonth;
	
	postSubmit('/account/create/act', data, function(res) {
		if(res.status) {
			showMsg('新增成功');
			if(!checkGuestDataCount()) location.href = '/account';
		} else {
			errMsg(res.msg);
		}
	});
}