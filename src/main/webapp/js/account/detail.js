$(function() {
	
	initAccountDataTable();
	
	$('#cancelBtn').on('click', cancelAct);
});

function initAccountDataTable() {
	var accountId = $('#accountId').val();;
	postSubmit('/account/detail/list/' + accountId, {}, function(res) {
		if(res.status) {
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
		} else {
			errMsg(res.msg);
		}
	});
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
