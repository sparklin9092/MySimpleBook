$(function() {
	
	initSpendDate();
	getSpendItemList();
});

/**
 * 初始化支出的日期欄位
 */
function initSpendDate() {
	
	$('#spendDatePicker').datepicker({
		dateFormat: 'yy年mm月dd日'
	});
	
	$('#spendDatePicker').val(moment().format('YYYY年MM月DD日'));
}

/**
 * 取得支出項目的下拉選單
 */
function getSpendItemList() {
	
	$.ajax({
		url: '/spend/itemList',
		method: 'POST',
		dataType: 'json',
		contentType: 'application/json',
		data: {},
		success: function(res) {
			
			if(res.status) {
				
				var itemList = res.spendItemListDto;
				var selectOptions = "";
				
				$.each(itemList, function(index, value){
					selectOptions += '<option value="' + value.itemId + '">' + value.itemName + '</option>';
				})
				
				$('#spendItemSelect').html(selectOptions);
				
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