package net.spark9092.MySimpleBook.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.spark9092.MySimpleBook.common.CryptionCommon;
import net.spark9092.MySimpleBook.common.GeneratorCommon;
import net.spark9092.MySimpleBook.common.SendMailCommon;
import net.spark9092.MySimpleBook.dto.user.MailDto;
import net.spark9092.MySimpleBook.dto.verify.MailBindMsgDto;
import net.spark9092.MySimpleBook.dto.verify.MailVerifyCodeLastTimeDto;
import net.spark9092.MySimpleBook.dto.verify.UserMailMsgDto;
import net.spark9092.MySimpleBook.dto.verify.UserMailVerifyDataDto;
import net.spark9092.MySimpleBook.enums.VerifyTypeEnum;
import net.spark9092.MySimpleBook.mapper.IUserInfoMapper;
import net.spark9092.MySimpleBook.mapper.IUserVerifyMapper;
import net.spark9092.MySimpleBook.pojo.verify.BindMailPojo;

@Service
public class VerifyService {

	private static final Logger logger = LoggerFactory.getLogger(VerifyService.class);

	@Autowired
	private IUserInfoMapper iUserInfoMapper;

	@Autowired
	private IUserVerifyMapper iUserVerifyMapper;

	@Autowired
	private GeneratorCommon generatorCommon;

	@Autowired
	private CryptionCommon cryptionCommon;

	@Autowired
	private SendMailCommon sendCommon;

	/**
	 * 驗證使用者的認證碼，通過之後，寄發臨時密碼給使用者去登入
	 * 如果認證碼通過，就把訪客身份改為一般使用者
	 * @param bindMailPojo
	 * @return
	 */
	public MailBindMsgDto bindUserMailByPojo(BindMailPojo bindMailPojo) {

		boolean bindSign = false;
		String userMail = "";
		MailBindMsgDto mailBindMsgDto = new MailBindMsgDto();
		Base64.Decoder decoder = Base64.getDecoder();

		String base64UserAccount = bindMailPojo.getBuAcc();
		String inputVerifyCode = bindMailPojo.getVerifyCode();

		try { //使用 try..catch... 是因為 Base64.Decoder
			String userAccount = new String(decoder.decode(base64UserAccount), "UTF-8");

			mailBindMsgDto = iUserInfoMapper.selectUserIdByAccount(userAccount);

			if(null == mailBindMsgDto) {

				bindSign = false; //有出現錯誤，把標記改為false，後面邏輯都不用繼續了
				
				mailBindMsgDto = new MailBindMsgDto();
				mailBindMsgDto.setStatus(false);
				mailBindMsgDto.setMsg("找不到您的電子信箱，請您重新綁定。");
				return mailBindMsgDto; //只有這一段邏輯要直接return，因為查不到資料後，Dto會是null型態
				
			} else {
				bindSign = true;
			}
			
			int userId = mailBindMsgDto.getUserId();

			UserMailVerifyDataDto userMailVerifyDataDto =
					iUserVerifyMapper.selectByUserId(userId);

			//先判斷有沒有認證碼
			if(bindSign) {
				if(null == userMailVerifyDataDto) {

					bindSign = false; //有出現錯誤，把標記改為false，後面邏輯都不用繼續了

					mailBindMsgDto.setStatus(false);
					mailBindMsgDto.setMsg("找不到您的認證碼，請您重新綁定。");
					
				} else {
					bindSign = true;
				}
			}

			//再判斷認證碼過期了沒
			if(bindSign) {
				LocalDateTime now = LocalDateTime.now();
				Duration duration = Duration.between(
						userMailVerifyDataDto.getSystemSendDatetime(), now);
				
				logger.debug("duration.toMinutes(): " + duration.toMinutes());
	
				if(duration.toMinutes() >= 3) {

					bindSign = false; //有出現錯誤，把標記改為false，後面邏輯都不用繼續了
	
					mailBindMsgDto.setStatus(false);
					mailBindMsgDto.setMsg(
							"認證碼已過期，請您點擊「重寄認證碼」，稍後會收到新的認證碼，請盡快使用。");
					
				} else {
					bindSign = true;
				}
			}

			//比對認證碼是否正確
			if(bindSign) {
				if(!inputVerifyCode.equals(userMailVerifyDataDto.getVerifyCode())) {

					bindSign = false; //有出現錯誤，把標記改為false，後面邏輯都不用繼續了
	
					mailBindMsgDto.setStatus(false);
					mailBindMsgDto.setMsg("認證碼輸入錯誤，請您重新輸入。");
					
				} else {
					bindSign = true;
				}
			}

			//判斷認證碼使用過了沒
			if(bindSign) {
				if(userMailVerifyDataDto.isUsed()) {

					bindSign = false; //有出現錯誤，把標記改為false，後面邏輯都不用繼續了
	
					mailBindMsgDto.setStatus(false);
					mailBindMsgDto.setMsg("認證碼已使用，請到您的信箱收取臨時密碼進行登入。");
					
				} else {
					bindSign = true;
				}
			}

			//更新認證碼為「已使用」
			if(bindSign) {
				boolean updateStatus = iUserVerifyMapper.updateByUserId(
						userMailVerifyDataDto.getVerifyId(), 
						userId);
	
				if(!updateStatus) {
	
					bindSign = false; //有出現錯誤，把標記改為false，後面邏輯都不用繼續了
	
					mailBindMsgDto.setStatus(false);
					mailBindMsgDto.setMsg("目前無法綁定電子信箱，已將您的問題提報，請您稍後再試。");
					
				} else {
					bindSign = true;
				}
			}
			
			//產出亂數密碼，作為臨時密碼，等系統都更新好使用者資料之後，再寄發臨時密碼給使用者
			String randomPwd = generatorCommon.getUserPwd();
			
			//對密碼進行加密處理
			String enPwd = cryptionCommon.encryptionPwd(randomPwd);

			if(bindSign) {
				
				//根據使用者ID，把加密後的密碼更新到資料庫，並且也更新帳號為Email，讓使用者可以用Email+臨時密碼登入
				//同時修改訪客身份為一般使用者
				boolean updateStatus = iUserInfoMapper.updateMailToAccWithPwdById(
						userId, enPwd);
				
				if(updateStatus) {
					
					bindSign = true;
					
				} else {
	
					bindSign = false; //有出現錯誤，把標記改為false，後面邏輯都不用繼續了
	
					mailBindMsgDto.setStatus(false);
					mailBindMsgDto.setMsg("目前無法為您產生臨時密碼，已將您的問題提報，請您稍後再試。");
				}
			}

			//寄發臨時密碼給使用者
			if(bindSign) {
				
				//根據使用者ID，查詢要寄發臨時密碼的Email
				MailDto userMailDto = iUserInfoMapper.selectMailByUserId(userId);
				
				if(null == userMailDto) {
	
					bindSign = false; //有出現錯誤，把標記改為false，後面邏輯都不用繼續了
					
				} else {
					
					userMail = userMailDto.getUserMail();
					
					boolean sendStatus = sendCommon.sendRandomPwdMail(
							"訪客", userMailDto.getUserMail(), randomPwd);
					
					if(sendStatus) {
						
						bindSign = true;
						
					} else {
		
						bindSign = false; //有出現錯誤，把標記改為false，後面邏輯都不用繼續了
		
						mailBindMsgDto.setStatus(false);
						mailBindMsgDto.setMsg("目前無法寄發臨時密碼給您，已將您的問題提報，請您稍後再試。");
					}
				}
			}
			
		} catch (Exception e) {
			
			logger.error("使用Base64對使用者帳號解碼時，發生問題！！！");
			
			mailBindMsgDto.setStatus(false);
			mailBindMsgDto.setMsg("目前無法寄發臨時密碼給您，已將您的問題提報，請您稍後再試。");
		}

		mailBindMsgDto.setUserId(0); //最後把使用者ID歸零，讓前端不知道使用者ID是多少
		
		//如果以上邏輯處理都正確，就把回傳的狀態改為true
		if(bindSign) {
			
			mailBindMsgDto.setStatus(true);
			mailBindMsgDto.setMsg("");
			mailBindMsgDto.setUserMail(userMail);
		}

		return mailBindMsgDto;
	}

	/**
	 * 重寄認證碼給使用者
	 * @param base64UserAccount
	 * @return
	 */
	public UserMailMsgDto resSendVerifyCodeMailByAccount(String base64UserAccount) {

		UserMailMsgDto userMailMsgDto = new UserMailMsgDto();
		Base64.Decoder decoder = Base64.getDecoder();

		try {

			String userAccount = new String(decoder.decode(base64UserAccount), "UTF-8");

			userMailMsgDto = iUserInfoMapper.selectMailByAccount(userAccount);
			
			if(null == userMailMsgDto) {
				
				userMailMsgDto = new UserMailMsgDto();
				userMailMsgDto.setStatus(false);
				userMailMsgDto.setMsg("目前無法重寄認證碼到您的信箱，已將您的問題提報，請您稍候再試。");
				userMailMsgDto.setReSendSec("");
				userMailMsgDto.setUserMail("");
				userMailMsgDto.setUserId(0);
				
			} else {
				
				int userId = userMailMsgDto.getUserId();
				String userMail = userMailMsgDto.getUserMail();
				String verifyCode = generatorCommon.getVerifyCode();
				
				//先查詢上一個認證碼是否已經超過3分鐘
				MailVerifyCodeLastTimeDto mailVerifyCodeLastTimeDto = 
						iUserVerifyMapper.selectLastCodeTimeByUserId(userId);
				
				if(null == mailVerifyCodeLastTimeDto) {

					//認證碼insert失敗
					userMailMsgDto.setStatus(false);
					userMailMsgDto.setMsg("目前無法重寄認證碼到您的信箱，已將您的問題提報，請您稍候再試。");
					userMailMsgDto.setReSendSec("");
					userMailMsgDto.setUserMail("");
					userMailMsgDto.setUserId(0);
					return userMailMsgDto;
					
				} else {
					
					LocalDateTime now = LocalDateTime.now();
					Duration duration = Duration.between(
							mailVerifyCodeLastTimeDto.getSystemSendTime(), now);
					
					int reSendSecInt = (int) duration.getSeconds();
					
					if(reSendSecInt > 0 && reSendSecInt <= 180) {
						
						userMailMsgDto.setStatus(false);
						userMailMsgDto.setMsg("您的上一個認證碼還未過期，請查看您的信箱。");
						userMailMsgDto.setReSendSec("");
						userMailMsgDto.setUserMail("");
						userMailMsgDto.setUserId(0);
						return userMailMsgDto;
						
					}
				}
				
				//存入user_verify，給使用者來驗證
				boolean insertStatus = iUserVerifyMapper.insertMailVerifyCodeByUserId(
						userId, VerifyTypeEnum.EMAIL.getType(), verifyCode);

				if(!insertStatus) {

					//認證碼insert失敗
					userMailMsgDto.setStatus(false);
					userMailMsgDto.setMsg("目前無法重寄認證碼到您的信箱，已將您的問題提報，請您稍候再試。");
					userMailMsgDto.setReSendSec("");
					userMailMsgDto.setUserMail("");
					userMailMsgDto.setUserId(0);
					return userMailMsgDto;
				}

				//使用發送公用類，呼叫發送電子信箱認證碼的方法
				boolean sendMailStatus = sendCommon.sendVerifyCodeMail(
						userAccount, "訪客", userMail, verifyCode);

				if(!sendMailStatus) {

					//Email發送失敗
					userMailMsgDto.setStatus(false);
					userMailMsgDto.setMsg("目前無法重寄認證碼到您的信箱，已將您的問題提報，請您稍候再試。");
					userMailMsgDto.setReSendSec("");
					userMailMsgDto.setUserMail("");
					userMailMsgDto.setUserId(0);
					return userMailMsgDto;
				}
				
				userMailMsgDto.setStatus(true);
				userMailMsgDto.setMsg("");
				userMailMsgDto.setReSendSec("");
				userMailMsgDto.setUserMail("");
				userMailMsgDto.setUserId(0);
			}
		} catch (Exception e) {
			
			logger.error("使用Base64對使用者帳號解碼時，發生問題！！！");
			
			userMailMsgDto = new UserMailMsgDto();
			userMailMsgDto.setStatus(false);
			userMailMsgDto.setMsg("目前無法重寄認證碼到您的信箱，已將您的問題提報，請您稍候再試。");
			userMailMsgDto.setReSendSec("");
			userMailMsgDto.setUserMail("");
			userMailMsgDto.setUserId(0);
		}
		
		return userMailMsgDto;
	}

}
