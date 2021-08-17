/**
 * 共用方法：檢查訪客目前以建立的資料數量
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

/**
 * 共用方法：檢查是否為行動裝置
 */
function isMobile() {
    var isMobile = false;
	var mobiles = new Array
    (
        "midp", "j2me", "avant", "docomo", "novarra", "palmos", "palmsource",
        "240x320", "opwv", "chtml", "pda", "windows ce", "mmp/",
        "blackberry", "mib/", "symbian", "wireless", "nokia", "hand", "mobi",
        "phone", "cdm", "up.b", "audio", "sie-", "sec-", "samsung", "htc",
        "mot-", "mitsu", "sagem", "sony", "alcatel", "lg", "eric", "vx",
        "NEC", "philips", "mmm", "xx", "panasonic", "sharp", "wap", "sch",
        "rover", "pocket", "benq", "java", "pt", "pg", "vox", "amoi",
        "bird", "compal", "kg", "voda", "sany", "kdd", "dbt", "sendo",
        "sgh", "gradi", "jb", "dddi", "moto", "iphone", "android",
        "iPod", "incognito", "webmate", "dream", "cupcake", "webos",
        "s8000", "bada", "googlebot-mobile"
    )
    var ua = navigator.userAgent.toLowerCase();
    for (var i = 0; i < mobiles.length; i++) {
        if (ua.indexOf(mobiles[i]) > 0) {
            isMobile = true;
            break;
        }
    }
	return isMobile
}
