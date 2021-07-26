package net.spark9092.MySimpleBook.common;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.spark9092.MySimpleBook.dto.whiteList.WhiteListValueDto;
import net.spark9092.MySimpleBook.enums.WhiteListTypeEnum;
import net.spark9092.MySimpleBook.mapper.ISystemWhiteListMapper;

@Component
public class CheckCommon {

	private static final Logger logger = LoggerFactory.getLogger(GetCommon.class);
	
	@Autowired
	private ISystemWhiteListMapper iSystemWhiteListMapper;

	/**
	 * 驗證金額
	 * @param amount
	 * @return
	 */
	public boolean checkAmnt(BigDecimal amount) {
		
		String regex = "^[0-9]{1,10}(\\.[0-9]{1,2})?$";
		
		return amount.toString().matches(regex);
	}
	
	/**
	 * 驗證Email(電子信箱)
	 * @param userMail
	 * @return
	 */
	public boolean checkMail(String userMail) {

		String regex = "\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}";
		
		return userMail.matches(regex);
	}
	
	/**
	 * 驗證手機號碼
	 * @param userPhone
	 * @return
	 */
	public boolean checkPhone(String userPhone) {

		String regex = "[0-9]{10}";
		
		return userPhone.matches(regex);
	}

	/**
	 * 檢查是否為手機裝置、平板裝置等移動設備
	 * @param userAgent
	 * @return
	 */
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

	/**
	 * 檢查是否為白名單 IP
	 * @param ipAddress
	 * @return
	 */
	public boolean isWhiteIp(String ipAddress) {

		logger.debug("Input IP: " + ipAddress);

		boolean isWhiteIp = false;
		
		List<WhiteListValueDto> dtoList = 
				iSystemWhiteListMapper.selectListByType(WhiteListTypeEnum.WHITE_IP.getType());

	    String inputIp = ipAddress.toLowerCase();
	    for (WhiteListValueDto dto : dtoList) {
	    	
	    	String whiteIp = dto.getValue();
	    	
	        if (inputIp.indexOf(whiteIp) >= 0) {
	        	isWhiteIp = true;
	            break;
	        }
	    }

		return isWhiteIp;

	}
	
	/**
	 * 檢查使用者帳號是否為系統相關帳號
	 * @param userAccount
	 * @return
	 */
	public boolean isSystemAccount(String userAccount) {
		
		List<String> systemAccountList = Arrays.asList(
				"guest", "admin", "spark", "manage", "system", "supervisor");
		
		boolean isSystemAccount = false;
		
		for (String systemAcc : systemAccountList) {
	        if (userAccount.indexOf(systemAcc) >= 0) {
	        	isSystemAccount = true;
	            break;
	        }
	    }
		
		return isSystemAccount;
	}
}
