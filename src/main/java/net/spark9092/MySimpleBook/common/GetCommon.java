package net.spark9092.MySimpleBook.common;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class GetCommon {

	private static final Logger logger = LoggerFactory.getLogger(GetCommon.class);

	public String getIpAddress(HttpServletRequest request) throws IOException {
		// 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址
		String ip = request.getHeader("X-Forwarded-For");

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_CLIENT_IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_X_FORWARDED_FOR");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
			}
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
	 * 格式化日期，輸出為「yyyy年MM月dd日」
	 * @param dataTime (java.time.LocalDateTime)
	 * @return
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
}
