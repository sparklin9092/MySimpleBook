/**
 * 檢查訪客目前以建立的資料數量
 * 如果超過 5、10、25、50 筆資料以上
 * 就提醒使用者前往綁定帳號
 */
function checkGuestDataCount() {
	var alertGuestDataCountList = [50, 25, 10, 5];
	postSubmitCustErr('/guest/datacount', {}, function(res) {
		var guestDataCount = res;
		$.each(alertGuestDataCountList, function(key, alertCount) {
			if(guestDataCount == alertCount) {
				var goToBindAcc = confirm('推薦前往綁定帳號！避免目前的資料，在您離開後被系統刪除。');
				if(goToBindAcc) {
					location.href = '/user/info';
					return true;
				}
			}
		});
	}, function(err) {
		return false;
	});
	
	//返回 false 讓訪客繼續使用
	return false;
}

/**
 * 共用方法：檢查傳入的是否為function
 */
function isFunction(funcToCheck) {
	return funcToCheck && {}.toString.call(funcToCheck) === '[object Function]';
}
