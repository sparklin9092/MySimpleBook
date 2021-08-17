$(function() {

	initTodayRecords();
	
	$('button[name=changeDateRangeBtn]').on('click', changeDataRange);
});

function initTodayRecords() {
	
	var todayDate = moment().format('YYYY-MM-DD');
	
	var startDate = todayDate;
	var endDate = todayDate;
	
	initTransferDataTable(startDate, endDate);
}

function initTransferDataTable(startDate, endDate) {
	
	var data = {};
	data.startDate = startDate;
	data.endDate = endDate;
	
	setTimeout(function() {
		if($.fn.DataTable.isDataTable('#transRecTable')) {
			$('#transRecTable').dataTable().fnDestroy();
			$('#transRecTable').empty();
		}
	}, 100);
	
	setTimeout(function() {
		
		postSubmit('/transfer/records', data, function(res) {
			var data = [];
			if(res.status) data = res.list
			
			$('#transRecTable').DataTable({
				dom: '<"float-start"f>tp',
				data: data,
				columns: [
					{title: 'id'},
					{title: '時間'},
					{title: '轉出'},
					{title: '轉入'},
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
						targets: 5,
						orderable: false,
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
		});
	}, 100);
}

function modifyView(transId) {
	
	location.href = '/transfer/modify/' + transId;
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
					
					setTimeout(function() {
						initTransferDataTable(startDate, endDate);
					}, 100);
					
				} else if(dateRange == 30) {
					
					var startDate = moment().startOf('month').format('YYYY-MM-DD');
					var endDate = moment().endOf('month').format('YYYY-MM-DD');
					
					setTimeout(function() {
						initTransferDataTable(startDate, endDate);
					}, 300);
					
				} else if(dateRange == 90) {
					
					var startDate = moment().subtract(2, 'months').startOf('month').format('YYYY-MM-DD');
					var endDate = moment().endOf('month').format('YYYY-MM-DD');
					
					setTimeout(function() {
						initTransferDataTable(startDate, endDate);
					}, 500);
					
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