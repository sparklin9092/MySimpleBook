$(function() {

	$('#transferBtn').on('click', transferView);
	$('#incomeBtn').on('click', incomeView);
	$('#spendBtn').on('click', spendView);
	
	initRichCodeList();
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