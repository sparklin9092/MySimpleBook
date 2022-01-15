$(function() {
	
	checkGuest();

	$('#transferBtn').on('click', transCreateView);
	$('#incomeBtn').on('click', incomeCreateView);
	$('#spendBtn').on('click', spendCreateView);
	
	$('#moreTransRecords').on('click', transRecView);
	$('#moreIncomeRecords').on('click', incomeRecView);
	$('#moreSpendRecords').on('click', spendRecView);
	$('#moreAccRecords').on('click', accountView);
	
	//initRichCodeList();
	
	queryTransferRecord();
	queryIncomeRecord();
	querySpendRecord();
	queryAccoundRecord();
});

function checkGuest() {

	if (!$.cookie('checkGuest')) {
		$.cookie('checkGuest', true);
		postSubmit('/main/check/guest', {}, function(res) {
			if(res.status) {
				var goToBindAcc = confirm('推薦前往綁定帳號！避免目前的資料，在您離開後被系統刪除。');
				if (goToBindAcc) {
					location.href = '/user/info';
				}
			}
		});
	}
}

function transCreateView() {
	
	location.href = '/transfer';
}

function incomeCreateView() {
	
	location.href = '/income';
}

function spendCreateView() {
	
	location.href = '/spend';
}

function transRecView() {
	
	location.href = '/transfer/records';
}

function incomeRecView() {
	
	location.href = '/income/records';
}

function spendRecView() {
	
	location.href = '/spend/records';
}

function accountView() {
	
	location.href = '/account';
}

function initRichCodeList() {
	
	var defaultRichCode = '時間就是金錢！';
	var richCodeItems = "";
	richCodeItems += '<div class="carousel-item p-2 active">' + defaultRichCode + '</div>';
	
	setTimeout(function() {
		postSubmit('/main/richCode/list', {}, function(res) {
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
		});
	}, 300);
	
	$('#richCodeBox').empty().html(richCodeItems);
}

function queryTransferRecord() {
	
	setTimeout(function() {
		postSubmit('/main/transfer/list', {}, function(res) {
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
		});
	}, 300);
}

function queryIncomeRecord() {
	
	setTimeout(function() {
		postSubmit('/main/income/list', {}, function(res) {
			var records = "";
			if(res.status) {
				$.each(res.listDtos, function(key, val) {
					records += '<div class="row">';
					records += '<div class="col-8">' + val.itemName + '</div>';
					records += '<div class="col-4 text-success">' + val.amnt + '</div>';
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
		});
	}, 300);
}

function querySpendRecord() {
	
	setTimeout(function() {
		postSubmit('/main/spend/list', {}, function(res) {
			var records = "";
			if(res.status) {
				$.each(res.listDtos, function(key, val) {
					records += '<div class="row">';
					records += '<div class="col-8">' + val.itemName + '</div>';
					records += '<div class="col-4 text-danger">' + val.amnt + '</div>';
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
		});
	}, 300);
}

function queryAccoundRecord() {
	
	setTimeout(function() {
		postSubmit('/main/account/list', {}, function(res) {
			var records = "";
			if(res.status) {
				$.each(res.listDtos, function(key, val) {
					records += '<div class="row">';
					records += '<div class="col-8">' + val.accName + '</div>';
					records += '<div class="col-4 text-primary">' + val.amnt + '</div>';
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
		});
	}, 500);
}


