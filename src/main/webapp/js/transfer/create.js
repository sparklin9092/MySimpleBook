$(function() {
	
	initTransferDate();
	initAccountItemSelect();
	
	$('#outsideAcc').hide();
	
	$('#outSideAccCheck').on('click', showOutside);
	
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

function showOutside() {
	
	var status = $('#outSideAccCheck').prop('checked');
	
	if(status) {
		
		$('#insideAcc').hide();
		$('#outsideAcc').show();
		
	} else {
		
		$('#insideAcc').show();
		$('#outsideAcc').hide();
		
	}
}

function cancelAct() {
	
	location.href = '/main';
}

function confirmAct() {
	
	var transferDate = $('#transferDate').val();
	var amount = $('#tOutAmnt').val();
	var tOutAccId = $('#tOutAccItemSelect').val();
	var tInAccId = $('#tInAccItemSelect').val();
	var outSideCheck = $('#outSideAccCheck').prop('checked');
	var tOutsideAccName = $('#tOutsideAccName').val();
	var remark = $('#remark').val();
	
	if(!outSideCheck && tInAccId == tOutAccId) {
		alert("無法在相同帳戶之間進行轉帳。");
		return;
	}
	
	if(!checkAmnt(amount)) return;
	
	var data = {};
	data.transferDate = transferDate;
	data.amount = amount;
	data.tOutAccId = tOutAccId;
	data.tInAccId = tInAccId;
	data.outSideCheck = outSideCheck;
	data.tOutsideAccName = tOutsideAccName;
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