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
				
				var accountTypeName = res.accountOneDto.accountTypeName;
				var accountName = res.accountOneDto.accountName;
				var initAmnt = res.accountOneDto.initAmnt;
				var accountAmnt = res.accountOneDto.accountAmnt;
				var accountDefault = res.accountOneDto.accountDefault;
				var accountActive = res.accountOneDto.accountActive;
				var enableLimitDate = res.accountOneDto.enableLimitDate;
				var limitYear = res.accountOneDto.limitYear;
				var limitMonth = res.accountOneDto.limitMonth;
				var createDateTime = res.accountOneDto.createDateTime;
				
				$('#accountTypeName').val(accountTypeName);
				$('#accountName').val(accountName);
				$('#initAmnt').val(initAmnt);
				$('#accountAmnt').val(accountAmnt);
				
				if(accountDefault) {
					$('#defaultTrue').prop('checked', true);
				} else {
					$('#defaultFalse').prop('checked', true);
				}
				
				if(accountActive) {
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
				
				$('#createDateTime').val(moment.utc(createDateTime).format('YYYY年MM月DD日'));
				
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
	
	var data = {};
	data.accountId = $('#accountId').val();
	
	$.ajax({
		url: '/account/delete/act',
		method: 'POST',
		dataType: 'json',
		contentType: 'application/json',
		data: JSON.stringify(data),
		success: function(res) {
			
			console.log(res);
			
			if(res.status) {
				
				alert('刪除成功');
				location.href = '/account';
				
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
	
	var accountId = $('#accountId').val();
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
				
				alert('修改成功');
				location.href = '/account';
				
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