$(function() {

	$('#createBtn').on('click', createView);

	initAccountDataTable();
});

function createView() {
	
	location.href = '/account/create';
}

function initAccountDataTable() {
	
	postSubmit('/account/list', {}, function(res) {
		$('#accountTable').DataTable({
			dom: '<"float-start"f>tp',
			data: res,
			columns: [
				{title: 'id'},
				{title: '名稱'},
				{title: '額度'},
				{title: '功能'}
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
	});
}

function modifyView(itemId) {
	
	location.href = '/account/modify/' + itemId;
}