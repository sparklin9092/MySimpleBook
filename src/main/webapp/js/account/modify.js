$(function() {

	initLimitYearMonth();
	initData();
	
	$('#enableLimitDate').on('click', enableLimitDatefield);

	$('#cancelBtn').on('click', cancelAct);
	$('#deleteBtn').on('click', deleteAct);
	$('#confirmBtn').on('click', confirmAct);
});

function initLimitYearMonth() {
	
	var yearNow = parseInt(moment().add(5, 'year').format('YYYY'));
	initYearSelect('limitYear', yearNow, 50);
	
	var monthNow = parseInt(moment().format('MM'));
	initMonthSelect('limitMonth', monthNow);
}

function initData() {
	
	var accountId = $('#accountId').val();
	
	$.ajax({
		url: '/account/one/' + accountId,
		method: 'POST',
		dataType: 'json',
		contentType: 'application/json',
		data: {},
		success: function(res) {
			
			if(res.status) {
				
				var accTypeName = res.accTypeName;
				var accName = res.accName;
				var initAmnt = res.initAmnt;
				var accAmnt = res.accAmnt;
				var accDefault = res.accDefault;
				var accActive = res.accActive;
				var enableLimitDate = res.enableLimitDate;
				var limitYear = res.limitYear;
				var limitMonth = res.limitMonth;
				var createDateTime = res.createDateTime;
				
				$('#accountTypeName').val(accTypeName);
				$('#accountName').val(accName);
				$('#initAmnt').val(initAmnt);
				$('#accountAmnt').val(accAmnt);
				
				if(accDefault) {
					$('#defaultTrue').prop('checked', true);
				} else {
					$('#defaultFalse').prop('checked', true);
				}
				
				if(accActive) {
					$('#activeTrue').prop('checked', true);
				} else {
					$('#activeFalse').prop('checked', true);
				}
				
				if(enableLimitDate) {
					$('#enableLimitDate').prop('checked', true);
					enableLimitDatefield();
					$('#limitYear').val(limitYear).change();
					$('#limitMonth').val(limitMonth).change();
				}
				
				$('#createDateTime').val(createDateTime);
				
			} else {
				
				errMsg(res.msg);
			}
		},
		error: function(err) {
			sysMsg('無法連接伺服器');
		}
	});
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
	
function deleteAct() {
	
	var deleteConfirm = confirm('確定要刪除嗎？');
	
	if(deleteConfirm) {
	
		var data = {};
		data.accountId = $('#accountId').val();
		
		$.ajax({
			url: '/account/delete/act',
			method: 'POST',
			dataType: 'json',
			contentType: 'application/json',
			data: JSON.stringify(data),
			success: function(res) {
				
				if(res.status) {
					
					showMsg('刪除成功');
					location.href = '/account';
					
				} else {
					
					errMsg(res.msg);
				}
			},
			error: function(err) {
				sysMsg('無法連接伺服器');
			}
		});
	}
}

function confirmAct() {
	
	var accountId = $('#accountId').val();
	var accountName = $('#accountName').val();
	var accountActive = $('input[name=accountActive]:checked').val();
	var accountDefault = $('input[name=accountDefault]:checked').val();
	var enableLimitDate = $('#enableLimitDate').prop('checked');
	var limitYear = "";
	var limitMonth = "";
	
	if(enableLimitDate) {
		limitYear = $('#limitYear').val();
		limitMonth = $('#limitMonth').val();
	}
	
	var data = {};
	data.accountId = accountId;
	data.accountName = accountName;
	data.accountActive = accountActive;
	data.accountDefault = accountDefault;
	data.enableLimitDate = enableLimitDate;
	data.limitYear = limitYear;
	data.limitMonth = limitMonth;
	
	$.ajax({
		url: '/account/modify/act',
		method: 'POST',
		dataType: 'json',
		contentType: 'application/json',
		data: JSON.stringify(data),
		success: function(res) {
			
			if(res.status) {
				
				showMsg('修改成功');
				initData();
				
			} else {
				
				errMsg(res.msg);
			}
		},
		error: function(err) {
			sysMsg('無法連接伺服器');
		}
	})
}