$(function() {

	$('#createBtn').on('click', createView);

	initSpendItemDataTable();
});

function createView() {
	
	location.href = '/itemSpend/create';
}

function initSpendItemDataTable() {
	
	$.ajax({
		url: '/itemSpend/list',
		method: 'POST',
		dataType: 'json',
		contentType: 'application/json',
		data: {},
		success: function(res) {
			
			$('#spendItemTable').DataTable({
				data: res,
				columns: [
					{title: 'id'},
					{title: '名稱'},
					{title: '預設'},
					{title: '狀態'},
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
						targets: 4,
						render: function(data, type, row) {
							return '<button type="button" class="btn btn-outline-warning" onclick="modifyView('+row[0]+')">修改</button>';
						}
					}
				],
				language: {
					lengthMenu: '每頁顯示 _MENU_ 筆',
					search: '搜尋：',
					info: '目前顯示 _PAGE_ 頁，總共 _PAGES_ 頁',
					zeroRecords: '找不到資料',
					infoEmpty: '沒有資料',
					infoFiltered: '(已過濾總共 _MAX_ 筆資料)',
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

function modifyView(itemId) {
	
	location.href = '/itemSpend/modify/' + itemId;
}