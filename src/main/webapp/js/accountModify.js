$(function() {

	initAccountTypeSelect();
	initLimitYearMonth();
	initData();
	
	$('#enableLimitDate').on('click', enableLimitDatefield);

	$('#cancelBtn').on('click', cancelAct);
	$('#deleteBtn').on('click', deleteAct);
	$('#confirmBtn').on('click', confirmAct);
});

function initAccountTypeSelect() {
	
	$.ajax({
		url: '/account/typeList',
		method: 'POST',
		dataType: 'json',
		contentType: 'application/json',
		data: {},
		success: function(res) {
			
			if(res.status) {
				
				var typeList = res.accountTypeListDto;
				var selectOptions = "";
				
				$.each(typeList, function(index, value){
					selectOptions += '<option value="' + value.typeId + '">' + value.typeName + '</option>';
				})
				
				$('#accountTypeSelect').html(selectOptions);
				
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
				
				var accountTypeId = res.accountOneDto.accountTypeId;
				var accountName = res.accountOneDto.accountName;
				var accountDefault = res.accountOneDto.accountDefault;
				var accountActive = res.accountOneDto.accountActive;
				var enableLimitDate = res.accountOneDto.enableLimitDate;
				var limitYear = res.accountOneDto.limitYear;
				var limitMonth = res.accountOneDto.limitMonth;
				var createUserName = res.accountOneDto.createUserName;
				var createDateTime = res.accountOneDto.createDateTime;
				
				$('#accountTypeSelect').val(accountTypeId.toString()).change();
				$('#accountName').val(accountName);
				
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
				
				$('#createUserName').val(createUserName);
				$('#createDateTime').val(moment.utc(createDateTime).format('YYYY年MM月DD日 hh:mm:ss'));
				
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
				
				alert(msg);
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
	var accountType = $('#accountTypeSelect').val();
	var accountName = $('#accountName').val();
	var accountDefault = $('input[name=accountDefault]:checked').val();
	var accountActive = $('input[name=accountActive]:checked').val();
	var enableLimitDate = $('#enableLimitDate').prop('checked');
	var limitYear = "";
	var limitMonth = "";
	
	if(enableLimitDate) {
		limitYear = $('#limitYear').val();
		limitMonth = $('#limitMonth').val();
	}
	
	var data = {};
	data.accountId = accountId;
	data.accountType = accountType;
	data.accountName = accountName;
	data.accountDefault = accountDefault;
	data.accountActive = accountActive;
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
				
				alert(msg);
			}
		},
		error: function(err) {
			console.log(err);
			alert('無法連接伺服器');
		}
	})
}