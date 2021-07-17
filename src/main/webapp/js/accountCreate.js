$(function() {

	initAccountTypeSelect();
	initLimitYearMonth();
	
	$('#enableLimitDate').on('click', enableLimitDatefield);

	$('#cancelBtn').on('click', cancelAct);
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
	var accountDefault = $('input[name=accountDefault]:checked').val();
	var enableLimitDate = $('#enableLimitDate').prop('checked');
	var limitYear = "";
	var limitMonth = "";
	
	if(enableLimitDate) {
		limitYear = $('#limitYear').val();
		limitMonth = $('#limitMonth').val();
	}
	
	var data = {};
	data.accountType = accountType;
	data.accountName = accountName;
	data.accountDefault = accountDefault;
	data.enableLimitDate = enableLimitDate;
	data.limitYear = limitYear;
	data.limitMonth = limitMonth;
	
	$.ajax({
		url: '/account/create/act',
		method: 'POST',
		dataType: 'json',
		contentType: 'application/json',
		data: JSON.stringify(data),
		success: function(res) {
			
			console.log(res);
			
			if(res.status) {
				
				alert('新增成功');
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