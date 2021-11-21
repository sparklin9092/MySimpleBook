package net.spark9092.MySimpleBook.service;

import java.text.DecimalFormat;

public abstract class BaseService {

	/**
	 * <b>金額格式化，千分位符號 & 小數點後兩位</b>
	 * <p>例如：123456 格式化為 123,456.00</p>
	 */
	protected DecimalFormat decimalFormat = new DecimalFormat("#,###.00");
}
