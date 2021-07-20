$(function() {

	$('#transferBtn').on('click', transferView);
	$('#incomeBtn').on('click', incomeView);
	$('#spendBtn').on('click', spendView);
	
	initRichCodeList();
	
	queryTransferRecord();
	queryIncomeRecord();
	querySpendRecord();
	queryAccoundRecord();
});

function transferView() {
	
	location.href = '/transfer';
}

function incomeView() {
	
	location.href = '/income';
}

function spendView() {
	
	location.href = '/spend';
}

function initRichCodeList() {
	
	var defaultRichCode = '時間就是金錢！';
	var richCodeItems = "";
	richCodeItems += '<div class="carousel-item p-2 active">' + defaultRichCode + '</div>';
	
	$.ajax({
		url: '/main/richCodeList',
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
			console.log(res);
			
			if(res.status) {
			
				var transRecords = "";
				
				$.each(res.listDtos, function(key, val) {
					transRecords += '<div class="row">';
					transRecords += '<div class="col">' + val.accOutName + '</div>';
					transRecords += '<div class="col">' + val.accoInName + '</div>';
					transRecords += '<div class="col text-info">' + val.transAmnt + '</div>';
					transRecords += '</div>';
				});
				
				$('#transRecords').empty().html(transRecords);
				
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

function queryIncomeRecord() {
	
}

function querySpendRecord() {
	
}

function queryAccoundRecord() {
	
}