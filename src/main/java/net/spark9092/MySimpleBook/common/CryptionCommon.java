package net.spark9092.MySimpleBook.common;

import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CryptionCommon {

	private static final Logger logger = LoggerFactory.getLogger(CryptionCommon.class);
	
	private Base64.Encoder encoder = Base64.getEncoder();
	private Base64.Decoder decoder = Base64.getDecoder();

	/**
	 * 使用 AES 對使用者密碼進行加密編碼
	 * @param userId
	 * @param userPwd
	 * @return
	 */
	public String encryptionAESPwd(int userId, String userPwd) {
		
		//TODO 為使用者密碼進行加密處理
		
		return userPwd;
	}

	/**
	 * 使用 AES 對使用者密碼進行解密編碼
	 * @param userId
	 * @param userPwd
	 * @return
	 */
	public String decryptionAESPwd(int userId, String userPwd) {
		
		//TODO 為使用者密碼進行解密處理
		
		return userPwd;
	}
	
	/**
	 * 使用 Base64 對使用者帳號進行編碼
	 * @param userId
	 * @param userAccount
	 * @return
	 */
	public String encoderBase64UserAccount(int userId, String userAccount) {
		
		try {
			byte[] userAccountByte = userAccount.getBytes("UTF-8");
		
			return encoder.encodeToString(userAccountByte);
			
		} catch(Exception ex) {
			
			logger.error(String.format(
					"CryptionCommon(加解密公用類)給使用者ID: %d 進行 Base64 編碼時，"
					+ "發生錯誤！傳入的 userAccount 是: %s", userId, userAccount)
			);
			
			return "";
		}
	}
	
	/**
	 * 使用 Base64 對使用者帳號進行解碼
	 * @param userId
	 * @param userAccount
	 * @return
	 */
	public String decoderBase64UserAccount(int userId, String base64UserAccount) {
		
		try {
			
			return new String(decoder.decode(base64UserAccount), "UTF-8");
			
		} catch(Exception ex) {
			
			logger.error(String.format(
					"CryptionCommon(加解密公用類)給使用者ID: %d 進行 Base64 編碼時，"
					+ "發生錯誤！傳入的 base64UserAccount 是: %s", userId, base64UserAccount)
			);
			
			return "";
		}
	}
}
