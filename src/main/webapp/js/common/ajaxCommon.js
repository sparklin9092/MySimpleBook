/**
 * 共用方法：以 Post 的方式，將 data 送到指定的 url，並 callback afterFunc & errFunc
 * 如果沒有輸入 url，則會顯示系統訊息：「未輸入 Post URL！！！」
 * 如果沒有輸入 afterFunc，則會顯示系統訊息：「未輸入 Post After Function！！！」
 * 如果沒有輸入 errFunc，則會顯示系統訊息：「未輸入 Post Error Function！！！」
 */
function postSubmitCustErr(url, data, afterFunc, errFunc) {
	
	var submitSign = false;
	var submitData = "";
	
	if(url) {
		submitSign = true;
	} else {
		submitSign = false;
		sysMsg('未輸入 Post URL！！！');
	}
	
	if(isFunction(afterFunc)) {
		submitSign = true;
	} else {
		submitSign = false;
		sysMsg('未輸入 Post After Function！！！');
	}
	
	if(isFunction(errFunc)) {
		submitSign = true;
	} else {
		submitSign = false;
		sysMsg('未輸入 Post Error Function！！！');
	}
	
	if(submitSign) {
		if(data) {
			submitData = JSON.stringify(data);
		}
		$.ajax({
			url: url,
			method: 'POST',
			dataType: 'json',
			contentType: 'application/json',
			data: submitData,
			success: afterFunc,
			error: errFunc
		});
	}
}

/**
 * 共用方法：重新包裝 postSubmitCustErr() 方法，統一管理 error function
 */
function postSubmit(url, data, afterFunc) {
	postSubmitCustErr(url, data, afterFunc, function(err) {
		sysMsg('無法連接伺服器', err);
	});
}
