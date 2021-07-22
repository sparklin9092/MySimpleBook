$(function() {
	
	initTransferDate();
	initAccountItemSelect();
	
	$('#outsideAcc').hide();
	
	$('#outSideAccCheck').on('click', showOutside);
	
	initData();

	$('#cancelBtn').on('click', cancelAct);
	$('#deleteBtn').on('click', deleteAct);
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

function initData() {
	
	var transferId = $('#transferId').val();
	
	$.ajax({
		url: '/transfer/one/' + transferId,
		method: 'POST',
		dataType: 'json',
		contentType: 'application/json',
		data: {},
		success: function(res) {
			
			if(res.status) {
				
				var transDate = res.oneDto.transDate;
				var amount = res.oneDto.amount;
				
				var outAccId = res.oneDto.outAccId;
				
				var outside = res.oneDto.outside;
				var inAccId = res.oneDto.inAccId;
				var outsideAccName = res.oneDto.outsideAccName;
				
				var createDateTime = res.oneDto.createDateTime;
				var remark = res.oneDto.remark;
				
				$('#transferDatePicker').val(moment(transDate).format('YYYY年MM月DD日'));
				$('#tOutAmnt').val(amount);
				
				$('#tOutAccItemSelect').val(outAccId).change();
				
				if(outside) {
					
					$('#outSideAccCheck').prop('checked', true);
					showOutside();
					$('#tOutsideAccName').val(outsideAccName);
					
				} else {
				
					$('#tInAccItemSelect').val(inAccId).change();
				}
				
				$('#createDateTime').val(moment.utc(createDateTime).format('YYYY年MM月DD日'));
				$('#remark').val(remark);
				
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
	
	location.href = '/transfer/records';
}
	
function deleteAct() {
	
	var data = {};
	data.transferId = $('#transferId').val();
	
	$.ajax({
		url: '/transfer/delete/act',
		method: 'POST',
		dataType: 'json',
		contentType: 'application/json',
		data: JSON.stringify(data),
		success: function(res) {
			
			if(res.status) {
				
				alert('刪除成功');
				location.href = '/transfer/records';
				
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
	
	var transferId = $('#transferId').val();
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
	data.transferId = transferId;
	data.transferDate = transferDate;
	data.amount = amount;
	data.tOutAccId = tOutAccId;
	data.tInAccId = tInAccId;
	data.outSideCheck = outSideCheck;
	data.tOutsideAccName = tOutsideAccName;
	data.remark = remark;
	
	$.ajax({
		url: '/transfer/modify/act',
		method: 'POST',
		dataType: 'json',
		contentType: 'application/json',
		data: JSON.stringify(data),
		success: function(res) {
			
			if(res.status) {
				
				alert('修改成功');
				location.href = '/transfer/records';
				
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