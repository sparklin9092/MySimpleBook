/**
 * 公用方法：初始化年份下拉選單<br>
 * elementId(String) : 下拉選單的ID（不用帶「#」符號）<br>
 * yearNow  (int)    : 目前年份，如果沒有輸入，就是當前年度<br>
 * rangeYear(int)    : 正負範圍。（ex:目前2021年，正負50年，範圍是1971~2071）
 */
function initYearSelect(elementId, yearNow, rangeYear) {
	
	var yOptStr = "";
	var yNum, yNumMin, yNumMax;
	yNumMin = parseInt(moment().add(-rangeYear, 'year').format('YYYY'));
	yNumMax = parseInt(moment().add(rangeYear, 'year').format('YYYY'));
	
	if(!yearNow) {
		yearNow = parseInt(moment().format('YYYY'));
	}
	
	for (yNum=yNumMin; yNum<=yNumMax; yNum++) {
		if(yNum == yearNow) {
			yOptStr += '<option value="' + yNum + '" selected>' + yNum + '年</option>'
		} else {
			yOptStr += '<option value="' + yNum + '">' + yNum + '年</option>'
		}
	}
	
	$('#' + elementId).html(yOptStr);
}

/**
 * 公用方法：初始化年份下拉選單<br>
 * elementId(String) : 下拉選單的ID（不用帶「#」符號）<br>
 * yearNow  (int)    : 目前年份，如果沒有輸入，就是當前年度
 */
function initMonthSelect(elementId, monthNow) {
	
	var mOptStr = "";
	var mNum;
	
	if(!monthNow) {
		monthNow = parseInt(moment().format('YYYY'));
	}
	
	for(mNum=1; mNum<=12; mNum++) {
		var mNumStr = mNum.toString();
		if(mNum>=1 && mNum<=9) {
			mNumStr = '0'+mNumStr;
		}
		if(mNum == monthNow) {
			mOptStr += '<option value="' + mNumStr + '" selected>' + mNum + '月</option>'
		} else {
			mOptStr += '<option value="' + mNumStr + '">' + mNum + '月</option>'
		}
	}
	
	$('#' + elementId).html(mOptStr);
}
