package net.spark9092.MySimpleBook.common;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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

	public boolean isMobile(String userAgent) {

		boolean isMobile = false;

		//根據瀏覽器的UserAgent判斷是否為手機裝置
		List<String> mobiles = Arrays.asList
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
	            );

	    String ua = userAgent.toLowerCase();
	    for (String mobile : mobiles) {
	        if (ua.indexOf(mobile) > 0) {
	            isMobile = true;
	            break;
	        }
	    }

		return isMobile;
	}

	public boolean isIntranet(String ipAddress) {

		logger.debug("isIntranet IP: " + ipAddress);

		boolean isIntranet = false;

		//根據瀏覽器的UserAgent判斷是否為手機裝置
		List<String> whiteList = Arrays.asList
	            (
	                "192.168", "127.0.0.1", "0:0:0:0:0:0:0:1"
	            );

	    String ip = ipAddress.toLowerCase();
	    for (String whiteIp : whiteList) {
	        if (ip.indexOf(whiteIp) >= 0) {
	        	isIntranet = true;
	            break;
	        }
	    }

		return isIntranet;

	}
}
