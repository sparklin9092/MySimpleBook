$(function() {
	
	initTransferDate();
	initAccountItemSelect();

	$('#cancelBtn').on('click', cancelAct);
	$('#confirmBtn').on('click', confirmAct);
});

function initTodayDate() {
	
	$('#transferDatePicker').val(moment().format('YYYY年MM月DD日'));
	$('#transferDate').val(moment().format('YYYY-MM-DD'));
}

function initTransferDate() {
	
	$('#transferDatePicker').datepicker({
		dateFormat: 'yy年mm月dd日',
		showWeek: true,
		altField: '#transferDate',
		altFormat: 'yy-mm-dd'
	});
	
	initTodayDate();
}

function initAccountItemSelect() {
	
	$.ajax({
		url: '/transfer/accountList',
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
				
				$('#tInAccItemSelect, #tOutAccItemSelect').empty().html(selOpts);
				
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
	
	var transferDate = $('#transferDate').val();
	var tOutAccId = $('#tOutAccItemSelect').val();
	var tOutAmnt = $('#tOutAmnt').val();
	var tInAccId = $('#tInAccItemSelect').val();
	var remark = $('#remark').val();
	
	if(tInAccId == tOutAccId) {
		alert("無法在相同帳戶之間進行轉帳。");
		return;
	}
	
	if(!checkAmnt(tOutAmnt)) return;
	
	var data = {};
	data.transferDate = transferDate;
	data.tOutAccId = tOutAccId;
	data.tOutAmnt = tOutAmnt;
	data.tInAccId = tInAccId;
	data.remark = remark;
	
	$.ajax({
		url: '/transfer/create/act',
		method: 'POST',
		dataType: 'json',
		contentType: 'application/json',
		data: JSON.stringify(data),
		success: function(res) {
			
			if(res.status) {
				
				var next = confirm('新增成功，要繼續新增下一筆轉帳嗎？');

				if(next) {

					initTodayDate();
					initAccountItemSelect();
					$('#tOutAmnt, #remark').val('');
					
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