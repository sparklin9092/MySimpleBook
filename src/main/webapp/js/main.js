$(function() {

	$('#incomeBtn').on('click', incomeView);
	$('#spendBtn').on('click', spendView);
	$('#transferBtn').on('click', transCreateView);
	$('#moreTransRecords').on('click', transRecView);
	$('#moreAccRecords').on('click', accountView);
	
	initRichCodeList();
	
	queryTransferRecord();
	queryIncomeRecord();
	querySpendRecord();
	queryAccoundRecord();
});

function transCreateView() {
	
	location.href = '/transfer';
}

function incomeView() {
	
	location.href = '/income';
}

function spendView() {
	
	location.href = '/spend';
}

function transRecView() {
	
	location.href = '/transfer/records';
}

function accountView() {
	
	location.href = '/account';
}

function initRichCodeList() {
	
	var defaultRichCode = '時間就是金錢！';
	var richCodeItems = "";
	richCodeItems += '<div class="carousel-item p-2 active">' + defaultRichCode + '</div>';
	
	$.ajax({
		url: '/main/richCode/list',
		method: 'POST',
		dataType: 'json',
		contentType: 'application/json',
		data: {},
		success: function(res) {
			
			if(res.status) {
				
				richCodeItems = '';
				
				$.each(res.listDtos, function(key, val) {
					
					var activeSign = '';
					if(key == 1) activeSign = ' active';
					else activeSign = '';
					
					richCodeItems += '<div class="carousel-item p-2 ' + activeSign + '">' + val.richCode + '</div>';
				});
				
				$('#richCodeBox').empty().html(richCodeItems);
			}
		},
		error: function(err) {
			console.log(err);
		}
	});
				
	$('#richCodeBox').empty().html(richCodeItems);
}

function queryTransferRecord() {
	
	$.ajax({
		url: '/main/transfer/list',
		method: 'POST',
		dataType: 'json',
		contentType: 'application/json',
		data: {},
		success: function(res) {
			
			var records = "";
			
			if(res.status) {
				
				$.each(res.listDtos, function(key, val) {
					records += '<div class="row">';
					records += '<div class="col">' + val.accOutName + '</div>';
					records += '<div class="col">' + val.accoInName + '</div>';
					records += '<div class="col text-info">' + val.transAmnt + '</div>';
					records += '</div>';
					
					if(key+1 == 5) {
				
						records += '<div class="row">';
						records += '<div class="col">(這裡只顯示最近的5個轉帳紀錄喔～)</div>';
						records += '</div>';
						
					}
				});
				
			} else {
				
				records += '<div class="row">';
				records += '<div class="col">' + res.msg + '</div>';
				records += '</div>';
			}
				
			$('#transRecords').empty().html(records);
		},
		error: function(err) {
			console.log(err);
			alert('無法連接伺服器');
		}
	});
}

function queryIncomeRecord() {
	
	$.ajax({
		url: '/main/income/list',
		method: 'POST',
		dataType: 'json',
		contentType: 'application/json',
		data: {},
		success: function(res) {
			
			var records = "";
			
			if(res.status) {
				
				$.each(res.listDtos, function(key, val) {
					records += '<div class="row">';
					records += '<div class="col">' + val.itemName + '</div>';
					records += '<div class="col text-success">' + val.amnt + '</div>';
					records += '</div>';
					
					if(key+1 == 5) {
				
						records += '<div class="row">';
						records += '<div class="col">(這裡只顯示最新的5個收入紀錄喔～)</div>';
						records += '</div>';
						
					}
				});
				
			} else {
				
				records += '<div class="row">';
				records += '<div class="col">' + res.msg + '</div>';
				records += '</div>';
			}
				
			$('#incomeRecords').empty().html(records);
		},
		error: function(err) {
			console.log(err);
			alert('無法連接伺服器');
		}
	});
}

function querySpendRecord() {
	
	$.ajax({
		url: '/main/spend/list',
		method: 'POST',
		dataType: 'json',
		contentType: 'application/json',
		data: {},
		success: function(res) {
			
			var records = "";
			
			if(res.status) {
				
				$.each(res.listDtos, function(key, val) {
					records += '<div class="row">';
					records += '<div class="col">' + val.itemName + '</div>';
					records += '<div class="col text-danger">' + val.amnt + '</div>';
					records += '</div>';
					
					if(key+1 == 5) {
				
						records += '<div class="row">';
						records += '<div class="col">(這裡只顯示最新的5個支出紀錄喔～)</div>';
						records += '</div>';
						
					}
				});
				
			} else {
				
				records += '<div class="row">';
				records += '<div class="col">' + res.msg + '</div>';
				records += '</div>';
			}
				
			$('#spendRecords').empty().html(records);
		},
		error: function(err) {
			console.log(err);
			alert('無法連接伺服器');
		}
	});
}

function queryAccoundRecord() {
	
	$.ajax({
		url: '/main/account/list',
		method: 'POST',
		dataType: 'json',
		contentType: 'application/json',
		data: {},
		success: function(res) {
			
			var records = "";
			
			if(res.status) {
				
				$.each(res.listDtos, function(key, val) {
					records += '<div class="row">';
					records += '<div class="col">' + val.accName + '</div>';
					records += '<div class="col text-primary">' + val.amnt + '</div>';
					records += '</div>';
					
					if(key+1 == 5) {
				
						records += '<div class="row">';
						records += '<div class="col">(這裡只顯示餘額最高的5個帳戶喔～)</div>';
						records += '</div>';
						
					}
				});
				
			} else {
				
				records += '<div class="row">';
				records += '<div class="col">' + res.msg + '</div>';
				records += '</div>';
			}
				
			$('#accRecords').empty().html(records);
		},
		error: function(err) {
			console.log(err);
			alert('無法連接伺服器');
		}
	});
}


