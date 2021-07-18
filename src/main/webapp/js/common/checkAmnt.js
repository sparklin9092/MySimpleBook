/**
 * 公用方法：驗證金額，只能是正數，不能是負數，<br>
 * 限制至少1位數，最多只能10位數，<br>
 * 如果有小數，也只能小數後兩位。<br>
 * 回傳值：true: 通過驗證、false: 驗證失敗
 */
function checkAmnt(amnt) {
	//驗證是否有值
	if (amnt) {
		//驗證是數字，而且是正數，且至少1位數，最多只能10位數，或包含小數後兩位
		/*
		var regex = /^[0-9]{1,10}(\.[0-9]{1,2})?$/;
		if(regex.test(amnt.toString())) {
			return true;
		}
		return false;
		*/
		var amnt = amnt.toString(); //轉型態為字串
		var amntBefore = ""; //小數點前的數字
		var amntAfter = "";  //小數點後的數字
		var pointExist = false;
		
		//檢查是否存在小數點
		if (amnt.indexOf('.') == -1) {
			//沒有小數點
			amntBefore = amnt;
		} else {
			//有小數點，切割字串，分為小數點前的數字和小數點後的數字
			pointExist = true;
			amntBefore = amnt.substring(0, amnt.indexOf('.'));
			amntAfter = amnt.substring(amnt.indexOf('.')+1, amnt.length);
		}
		
		//檢查是否為10位數
		if (amntBefore.length > 10) {
			alert('金額最多只能輸入 10 位數。');
			return false;
		}
		
		//檢查這10位數是不是都是數字，而且是正數
		var regex = /^[0-9]{1,10}$/;
		if(!regex.test(amntBefore)) {
			alert('金額只能輸入數字');
			return false;
		}
		
		//存在小數點，檢查小數是不是兩位數
		if(pointExist) {
			if(amntAfter.length == 0) {
				alert('請輸入小數點後的數字。');
				return false;
			}
			
			if(amntAfter.length > 2) {
				alert('小數點後最多只能輸入 2 位數。');
				return false;
			}
			
			//檢查這2位數是不是都是數字，而且是正數
			if(!regex.test(amntAfter)) {
				alert('小數點後只能輸入數字');
				return false;
			}
		}
		
		return true;
	} else {
		alert('請輸入金額。');
		return false;
	}
}