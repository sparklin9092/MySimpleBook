var specifyDateModalActSign = false;

$(function() {
	initTodayRecords();
	initSpecifyDateModal();
	
	$('button[name=changeDateRangeBtn]').on('click', changeDataRange);
	$('#specifyDateModalBtn').on('click', specifyDateModalAct);
});

function initTodayRecords() {
	$.each($('button[name=changeDateRangeBtn]'), function(key, val) {
		if($(val).hasClass('btn-info')) {
			$(val).removeClass('btn-info').addClass('btn-outline-info');
		}
	});
	$('#todayBtn').removeClass('btn-outline-info').addClass('btn-info');
	
	var todayDate = moment().format('YYYY-MM-DD');
	var startDate = todayDate;
	var endDate = todayDate;
	
	reloadTable(startDate, endDate);
}

function initSpecifyDateModal() {
	$('#specifyDateModal').on('show.bs.modal', function(e) {
		specifyDateModalActSign = false;
	});
	$('#specifyDateModal').on('hide.bs.modal', function(e) {
		if(!specifyDateModalActSign)
			initTodayRecords();
	});
	initSpecifyDatePicker();
}

function initSpecifyDatePicker() {
	$('#datePicker').datepicker({
		dateFormat: 'yy年mm月dd日',
		showWeek: true,
		altField: '#specifyDate',
		altFormat: 'yy-mm-dd'
	});
	
	$('#datePicker').val(moment().format('YYYY年MM月DD日'));
	$('#specifyDate').val(moment().format('YYYY-MM-DD'));
}

function reloadTable(startDate, endDate) {
	var data = {};
	data.startDate = startDate;
	data.endDate = endDate;
	
	if($.fn.DataTable.isDataTable('#table')) {
		$('#table').dataTable().fnDestroy();
		$('#table').empty();
	}
	
	setTimeout(function() {
		postSubmit('/income/records', data, function(res) {
			var data = [];
			if(res.status) data = res.list
			
			$('#table').DataTable({
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
						orderable: false,
						render: function(data, type, row) {
							return '<button type="button" class="btn btn-outline-warning" onclick="modifyView('+row[0]+')">修改</button>';
						}
					}
				],
				pageLength: 10,
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
	}, 200);
}

function modifyView(transId) {
	location.href = '/income/modify/' + transId;
}

function changeDataRange() {
	var thisBtnId = $(this).prop('id');
	var startDate = moment().format('YYYY-MM-DD');
	var endDate = moment().format('YYYY-MM-DD');
	
	$.each($('button[name=changeDateRangeBtn]'), function(key, val) {
		if($(val).prop('id') == thisBtnId) {
			if($(val).hasClass('btn-outline-info')) {
				$(val).removeClass('btn-outline-info').addClass('btn-info');
				var dateRange = $(this).data('range');
				
				if(dateRange == 1) {
					reloadTable(startDate, endDate);
				} else if(dateRange == 2) {
					startDate = moment().subtract(1, 'day').format('YYYY-MM-DD');
					endDate = moment().subtract(1, 'day').format('YYYY-MM-DD');
					reloadTable(startDate, endDate);
				} else if(dateRange == 30) {
					startDate = moment().startOf('month').format('YYYY-MM-DD');
					endDate = moment().endOf('month').format('YYYY-MM-DD');
					reloadTable(startDate, endDate);
				} else if(dateRange == 90) {
					startDate = moment().subtract(2, 'months').startOf('month').format('YYYY-MM-DD');
					endDate = moment().endOf('month').format('YYYY-MM-DD');
					reloadTable(startDate, endDate);
				} else if(dateRange == 180) {
					startDate = moment().subtract(5, 'months').startOf('month').format('YYYY-MM-DD');
					endDate = moment().endOf('month').format('YYYY-MM-DD');
					reloadTable(startDate, endDate);
				} else if(dateRange == -1) {
					//catch this condition, and don't do anything
				} else {
					reloadTable(startDate, endDate);
				}
			}
		} else {
			if($(val).hasClass('btn-info')) {
				$(val).removeClass('btn-info').addClass('btn-outline-info');
			}
		}
	});
}

function specifyDateModalAct() {
	var specifyDate = $('#specifyDate').val();
	reloadTable(specifyDate, specifyDate);
	specifyDateModalActSign = true;
	$('#specifyDateModal').modal('hide');
}
