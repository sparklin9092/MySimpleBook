package net.spark9092.MySimpleBook.common;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import org.apache.commons.lang3.StringUtils;

/**
 * 各種Get(取得)的共用類
 * @author spark.9092
 */
@Component
public class GetCommon {

	private static final Logger logger = LoggerFactory.getLogger(GetCommon.class);

	/**
	 * <b>查請求主機IP地址，如果通過代理進來，則透過防火牆查真實IP地址</b>
	 * @param request
	 * @return 真實IP地址
	 * @throws IOException
	 */
	public String getIpAddress(HttpServletRequest request) throws IOException {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
				ip = request.getHeader("Proxy-Client-IP");
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
				ip = request.getHeader("WL-Proxy-Client-IP");
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
				ip = request.getHeader("HTTP_CLIENT_IP");
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
				ip = request.getHeader("HTTP_X_FORWARDED_FOR");
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
				ip = request.getRemoteAddr();
		} else if (ip.length() > 15) {
			String[] ips = ip.split(",");
			for (String ip2 : ips) {
				String strIp = ip2;
				if (!("unknown".equalsIgnoreCase(strIp))) {
					ip = strIp;
					break;
				}
			}
		}
		return ip;
	}
	
	/**
	 * <b>格式化日期，輸出為「yyyy年MM月dd日」</b>
	 * @param dataTime (java.time.LocalDateTime)
	 * @return 格式化後的日期
	 */
	public String getFormatDate(LocalDateTime dataTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日");
		try {
			return dataTime.format(formatter);
		} catch (Exception e) {
			logger.error("格式化日期發生錯誤！！！");
			return "";
		}
	}
	
	/**
	 * <b>傳入金額字串，去除為0的小數; 如果有小數，則保留顯示</b>
	 * @param amnt
	 * @return 去除小數為0的金額字串
	 */
	public String getNoZeroAmnt(String amnt) {
		amnt = (amnt.indexOf(".00") == -1) ? amnt : amnt.replace(".00", "");
		amnt = (StringUtils.isBlank(amnt)) ? "0" : amnt;
		return amnt;
	}
}
