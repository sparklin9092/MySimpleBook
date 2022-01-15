var specifyDateModalActSign = false;

$(function() {
	initDetailDataTable();
	initSpecifyDateModal();
	
	$('#cancelBtn').on('click', cancelAct);
	$('button[name=changeDateRangeBtn]').on('click', changeDataRange);
	$('#specifyDateModalBtn').on('click', specifyDateModalAct);
});

function initDetailDataTable() {
	$.each($('button[name=changeDateRangeBtn]'), function(key, val) {
		if($(val).hasClass('btn-info')) {
			$(val).removeClass('btn-info').addClass('btn-outline-info');
		}
	});
	$('#todayBtn').removeClass('btn-outline-info').addClass('btn-info');
	
	var accountId = $('#accountId').val();
	var startDate = moment().format('YYYY-MM-DD');
	var endDate = moment().format('YYYY-MM-DD');
	
	var data = {};
	data.startDate = startDate;
	data.endDate = endDate;
	
	if($.fn.DataTable.isDataTable('#table')) {
		$('#table').dataTable().fnDestroy();
		$('#table').empty();
	}
	
	setTimeout(function() {
		postSubmit('/account/detail/list/' + accountId, data, function(res) {
			if(res.status)
				renderTable(res);
			else
				errMsg(res.msg);
		});
	}, 200);
}

function initSpecifyDateModal() {
	$('#specifyDateModal').on('show.bs.modal', function(e) {
		specifyDateModalActSign = false;
	});
	$('#specifyDateModal').on('hide.bs.modal', function(e) {
		if(!specifyDateModalActSign)
			initDetailDataTable();
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

function cancelAct() {
	history.back();
}

function spendModifyView(id) {
	location.href = '/spend/modify/' + id;
}

function incomeModifyView(id) {
	location.href = '/income/modify/' + id;
}

function transferModifyView(id) {
	location.href = '/transfer/modify/' + id;
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

function reloadTable(startDate, endDate) {
	var accountId = $('#accountId').val();
	var data = {};
	data.startDate = startDate;
	data.endDate = endDate;
	
	if($.fn.DataTable.isDataTable('#table')) {
		$('#table').dataTable().fnDestroy();
		$('#table').empty();
	}
	
	setTimeout(function() {
		postSubmit('/account/detail/list/' + accountId, data, function(res) {
			if(res.status)
				renderTable(res);
			else
				errMsg(res.msg);
		});
	}, 200);
}

function renderTable(res) {
	$('#accountName').val(res.accName);
	$('#accountTypeName').val(res.typeName);
	$('#accountAmnt').val(res.accAmnt);
	$('#table').DataTable({
		dom: '<"float-start"f>tp',
		data: res.details,
		columns: [
			{title: 'id'},
			{title: '功能'},
			{title: '日期'},
			{title: '項目'},
			{title: '金額'},
			{title: '備註'},
			{title: 'type'}
		],
		order: [[2, 'desc']],
		columnDefs: [
			{
				targets: 0,
				visible: false,
				searchable: false
			},
			{
				targets: 1,
				orderable: false,
				render: function(data, type, row) {
					var clickAct = '';
					switch (row[6]) {
						case 's':
							clickAct = '"spendModifyView('+row[0]+')"';
							break;
						case 'i':
							clickAct = '"incomeModifyView('+row[0]+')"';
							break;
						case 't':
							clickAct = '"transferModifyView('+row[0]+')"';
							break;
					}
					if(clickAct == '')
						return '';
					else
						return '<button type="button" class="btn btn-outline-warning ms-2" onclick=' + clickAct + '>修改</button>';
				}
			},
			{
				targets: 6,
				visible: false,
				searchable: false
			}
		],
		rowCallback: function(row, data, index){
			var color = "#000000";
			switch (data[6]) {
				case 's':
					color = '#dc3545';
					break;
				case 'i':
					color = '#198754';
					break;
				case 't':
					color = '#0dcaf0';
					break;
			}
			$(row).find('td:eq(3)').css('color', color);
		},
		pageLength: 20,
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
}

function specifyDateModalAct() {
	var specifyDate = $('#specifyDate').val();
	reloadTable(specifyDate, specifyDate);
	specifyDateModalActSign = true;
	$('#specifyDateModal').modal('hide');
}
