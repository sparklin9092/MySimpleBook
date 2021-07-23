$(function() {

	initTodayRecords();
	
	$('button[name=changeDateRangeBtn]').on('click', changeDataRange);
});

function initTodayRecords() {
	
	var todayDate = moment().format('YYYY-MM-DD');
	
	var startDate = todayDate;
	var endDate = todayDate;
	
	initIncomeDataTable(startDate, endDate);
}

function initIncomeDataTable(startDate, endDate) {
	
	var data = {};
	data.startDate = startDate;
	data.endDate = endDate;
	
	if($.fn.DataTable.isDataTable('#incomeRecTable')) {
		$('#incomeRecTable').dataTable().fnDestroy();
		$('#incomeRecTable').empty();
	}
	
	$.ajax({
		url: '/income/records',
		method: 'POST',
		dataType: 'json',
		contentType: 'application/json',
		data: JSON.stringify(data),
		success: function(res) {
			
			var data = [];
			
			if(res.status) {
				
				data = res.list
			}
			
			$('#incomeRecTable').DataTable({
				dom: '<"float-start"f>tp',
				data: data,
				columns: [
					{title: 'id'},
					{title: '時間'},
					{title: '項目'},
					{title: '金額'},
					{title: '功能'}
				],
				order: [[1, 'desc']],
				columnDefs: [
					{
						targets: 0,
						visible: false,
						searchable: false
					},
					{
						targets: 4,
						render: function(data, type, row) {
							return '<button type="button" class="btn btn-outline-warning" onclick="modifyView('+row[0]+')">修改</button>';
						}
					}
				],
				pageLength: 5,
				language: {
					search: '搜尋：',
					zeroRecords: '找不到資料',
					paginate: {
						first: '第一頁',
						last: '最後一頁',
						next: '下一頁',
						previous: '上一頁'
					}
				}
			});
		},
		error: function(err) {
			console.log(err);
			alert('無法連接伺服器');
		}
	});
}

function modifyView(transId) {
	
	location.href = '/income/modify/' + transId;
}

function changeDataRange() {
	
	var thisBtnId = $(this).prop('id');
	
	$.each($('button[name=changeDateRangeBtn]'), function(key, val) {
		
		if($(val).prop('id') == thisBtnId) {
			
			if($(val).hasClass('btn-outline-info')) {
				
				$(val).removeClass('btn-outline-info').addClass('btn-info');
				
				var dateRange = $(this).data('range');
				
				if(dateRange == 1) {
					
					initTodayRecords();
					
				} else if(dateRange == 7) {
					
					var startDate = moment().day(1).format('YYYY-MM-DD');
					var endDate = moment().day(7).format('YYYY-MM-DD');
					
					initIncomeDataTable(startDate, endDate);
					
				} else if(dateRange == 30) {
					
					var startDate = moment().startOf('month').format('YYYY-MM-DD');
					var endDate = moment().endOf('month').format('YYYY-MM-DD');
					
					initIncomeDataTable(startDate, endDate);
					
				} else if(dateRange == 90) {
					
					var startDate = moment().subtract(2, 'months').startOf('month').format('YYYY-MM-DD');
					var endDate = moment().endOf('month').format('YYYY-MM-DD');
					
					initIncomeDataTable(startDate, endDate);
					
				} else {
					
					initTodayRecords();
				}
			}
		} else {
			
			if($(val).hasClass('btn-info')) {
				
				$(val).removeClass('btn-info').addClass('btn-outline-info');
			}
		}
	});
}