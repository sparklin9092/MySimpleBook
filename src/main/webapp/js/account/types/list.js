$(function() {

	$('#createBtn').on('click', createView);

	initAccountTypeItemDataTable();
});

function createView() {
	
	location.href = '/account/types/create';
}

function initAccountTypeItemDataTable() {
	
	$.ajax({
		url: '/account/types/list',
		method: 'POST',
		dataType: 'json',
		contentType: 'application/json',
		data: {},
		success: function(res) {
			
			$('#accountTypeItemTable').DataTable({
				dom: '<"float-start"f>tp',
				data: res,
				columns: [
					{title: 'id',},
					{title: '名稱', width: '55%'},
					{title: '狀態', width: '20%'},
					{title: '功能', width: '25%'}
				],
				order: [[0, 'desc']],
				columnDefs: [
					{
						targets: 0,
						visible: false,
						searchable: false
					},
					{
						targets: 3,
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
		},
		error: function(err) {
			sysMsg('無法連接伺服器');
		}
	});
}

function modifyView(itemId) {
	
	location.href = '/account/types/modify/' + itemId;
}