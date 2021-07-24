/**
 * 檢查訪客目前以建立的資料數量
 * 如果超過 5、10、25、50 筆資料以上
 * 就提醒使用者前往綁定帳號
 */
function checkGuestDataCount() {
	
	var alertGuestDataCountList = [50, 25, 10, 5];
	
	$.ajax({
		url: '/user/info/guest/datacount',
		method: 'POST',
		dataType: 'json',
		contentType: 'application/json',
		data: {},
		success: function(res) {
			
			console.log(res);
			
			var guestDataCount = res;
			
			$.each(alertGuestDataCountList, function(key, alertCount) {
			
				console.log("alertCount: " + alertCount);
				console.log("guestDataCount: " + guestDataCount);
				
				if(guestDataCount == alertCount) {
					
					var goToBindAcc = confirm('推薦前往綁定帳號！避免目前的資料，在您離開後，被系統刪除。 ^_^');
					
					console.log("goToBindAcc: " + goToBindAcc);
					
					if(goToBindAcc) {
				
						location.href = '/user/info';
						
						return true;
					}
				}
			});
		},
		error: function(err) {
					
			return false;
		}
	});
	
	//返回 false 讓訪客繼續使用
	return false;
}
