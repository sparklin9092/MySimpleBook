package net.spark9092.MySimpleBook.service;

import java.time.Duration;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.spark9092.MySimpleBook.common.CryptionCommon;
import net.spark9092.MySimpleBook.common.GeneratorCommon;
import net.spark9092.MySimpleBook.common.SendMailCommon;
import net.spark9092.MySimpleBook.dto.verify.MailBindMsgDto;
import net.spark9092.MySimpleBook.dto.verify.MailVerifyCodeLastTimeDto;
import net.spark9092.MySimpleBook.dto.verify.UserMailDto;
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
	 * 注意：訪客和一般使用者都是用這個方法去檢驗認證碼
	 * 驗證使用者的認證碼，通過之後，判斷認證的身份是什麼？
	 * 如果是訪客，就寄發臨時密碼給使用者去登入，然後把訪客身份改為一般使用者
	 * 如果是一般使用者，就檢驗認證碼，通過就把驗證碼改為「已使用」，讓登入機制可以去判斷這個Email是否可以用於登入
	 * @param bindMailPojo
	 * @return
	 */
	public MailBindMsgDto bindUserMailByPojo(BindMailPojo bindMailPojo) {

		boolean bindSign = false;
		MailBindMsgDto mailBindMsgDto = new MailBindMsgDto();

		String base64UserAccount = bindMailPojo.getBuAcc();
		String inputVerifyCode = bindMailPojo.getVerifyCode();

		//對 Base64 編碼的 User Account 進行解碼
		String userAccount = cryptionCommon.decoderBase64UserAccount(0, base64UserAccount);

		//透過 UserAccount 去查詢 User ID
		UserMailDto userMailDto = iUserInfoMapper.selectMailByAccount(userAccount);

		if(null == userMailDto) {

			//有出現錯誤，把標記改為false，後面邏輯都不用繼續了
			bindSign = false;

			mailBindMsgDto = new MailBindMsgDto();
			mailBindMsgDto.setStatus(false);
			mailBindMsgDto.setMsg("找不到您的電子信箱，請您重新綁定。");
			return mailBindMsgDto; //只有這一段要直接return，因為查不到資料，Dto 是 NULL 型態

		} else {
			//以上邏輯執行正確，把標記改為 true，讓接下來的邏輯可以繼續執行
			bindSign = true;
		}

		//這裡的 User ID 是透過上面的邏輯找到的
		int userId = userMailDto.getUserId();

		//透過 User ID 去找存在 DB 的 驗證碼索引、驗證碼、系統寄發時間、使用狀態
		UserMailVerifyDataDto userMailVerifyDataDto =
				iUserVerifyMapper.selectByUserId(userId);

		//根據標記，判斷這段邏輯是否要執行
		if(bindSign) {

			//先判斷有沒有認證碼
			if(null == userMailVerifyDataDto) {

				//有出現錯誤，把標記改為false，後面邏輯都不用繼續了
				bindSign = false;

				mailBindMsgDto.setStatus(false);
				mailBindMsgDto.setMsg("找不到您的認證碼，請您重新綁定。");
				
			} else {
				//以上邏輯執行正確，把標記改為 true，讓接下來的邏輯可以繼續執行
				bindSign = true;
			}
		}

		//根據標記，判斷這段邏輯是否要執行
		if(bindSign) {

			//再判斷認證碼過期了沒
			LocalDateTime now = LocalDateTime.now(); //驗證當下的時間
			Duration duration = Duration.between(
					userMailVerifyDataDto.getSystemSendDatetime(), now); // 與系統寄發時間之差

			logger.debug("duration.toMinutes(): " + duration.toMinutes());

			//如果超過 3 分鐘
			if(duration.toMinutes() >= 3) {

				//有出現錯誤，把標記改為false，後面邏輯都不用繼續了
				bindSign = false;

				mailBindMsgDto.setStatus(false);
				mailBindMsgDto.setMsg(
						"認證碼已過期，請您點擊「重寄認證碼」，稍後會收到新的認證碼，請盡快使用。");
				
			} else {
				//以上邏輯執行正確，把標記改為 true，讓接下來的邏輯可以繼續執行
				bindSign = true;
			}
		}

		//根據標記，判斷這段邏輯是否要執行
		if(bindSign) {
			
			//比對認證碼是否正確
			if(!inputVerifyCode.equals(userMailVerifyDataDto.getVerifyCode())) {

				//有出現錯誤，把標記改為false，後面邏輯都不用繼續了
				bindSign = false;

				mailBindMsgDto.setStatus(false);
				mailBindMsgDto.setMsg("您的認證碼輸入錯誤，請到信箱確認後，再重新輸入。");

			} else {
				//以上邏輯執行正確，把標記改為 true，讓接下來的邏輯可以繼續執行
				bindSign = true;
			}
		}

		//根據標記，判斷這段邏輯是否要執行
		if(bindSign) {
			
			//判斷認證碼使用過了沒
			if(userMailVerifyDataDto.isUsed()) {

				//有出現錯誤，把標記改為false，後面邏輯都不用繼續了
				bindSign = false;
				
				//這裡會根據身份不同，回傳不同的訊息
				String returnMsg = "";
				if(userMailDto.isGuest()) {
					
					//這是  「訪客」  綁定 Email 要回傳的訊息
					returnMsg = "認證碼已使用，請到您的信箱收取臨時密碼進行登入。";
					
				} else {
					
					//這是  「使用者」  綁定 Email 要回傳的訊息
					returnMsg = "認證碼已使用，您已經可以使用帳號或電子信箱(Email)進行登入。";
					
				}

				mailBindMsgDto.setStatus(false);
				mailBindMsgDto.setMsg(returnMsg);

			} else {
				//以上邏輯執行正確，把標記改為 true，讓接下來的邏輯可以繼續執行
				bindSign = true;
			}
		}

		//根據標記，判斷這段邏輯是否要執行
		if(bindSign) {
			
			//更新認證碼為「已使用」
			boolean updateStatus = iUserVerifyMapper.updateByUserId(
					userMailVerifyDataDto.getVerifyId(), userId);

			if(!updateStatus) {

				//有出現錯誤，把標記改為false，後面邏輯都不用繼續了
				bindSign = false;

				mailBindMsgDto.setStatus(false);
				mailBindMsgDto.setMsg("目前無法綁定電子信箱，已將您的問題提報，請您稍後再試。");
				
			} else {
				//以上邏輯執行正確，把標記改為 true，讓接下來的邏輯可以繼續執行
				bindSign = true;
			}
		}

		//以下這兩段邏輯寄發臨時密碼給訪客的邏輯，一般使用者不用處理
		if(userMailDto.isGuest()) {
			
			//產出亂數密碼，作為臨時密碼，等系統都更新好使用者資料之後，再寄發臨時密碼給使用者
			String randomPwd = generatorCommon.getUserPwd();

			//對密碼進行加密處理
			String enPwd = cryptionCommon.encryptionAESPwd(userId, randomPwd);

			if(bindSign) {

				//根據使用者ID，把加密後的密碼更新到資料庫，
				//並且也更新帳號為Email，讓使用者可以用Email+臨時密碼登入
				//同時修改訪客身份為一般使用者
				boolean updateStatus = iUserInfoMapper.updateMailToAccWithPwdById(
						userId, enPwd);

				if(updateStatus) {

					//以上邏輯執行正確，把標記改為 true，讓接下來的邏輯可以繼續執行
					bindSign = true;

				} else {

					bindSign = false; //有出現錯誤，把標記改為false，後面邏輯都不用繼續了

					mailBindMsgDto.setStatus(false);
					mailBindMsgDto.setMsg("目前無法為您產生臨時密碼，已將您的問題提報，請您稍後再試。");
				}
			}

			//寄發臨時密碼給使用者
			if(bindSign) {

				boolean sendStatus = sendCommon.sendRandomPwdMail(
						"訪客", userMailDto.getUserMail(), randomPwd);
				
				if(sendStatus) {

					//以上邏輯執行正確，把標記改為 true，讓接下來的邏輯可以繼續執行
					bindSign = true;
					
				} else {
	
					bindSign = false; //有出現錯誤，把標記改為false，後面邏輯都不用繼續了
	
					mailBindMsgDto.setStatus(false);
					mailBindMsgDto.setMsg("目前無法寄發臨時密碼給您，已將您的問題提報，請您稍後再試。");
				}
			}
		}

		//根據標記如果以上邏輯處理都正確，就把回傳的狀態改為true
		if(bindSign) {

			//這裡會根據身份不同，回傳不同的訊息
			String returnMsg = "";
			if(userMailDto.isGuest()) {
				
				//這是  「訪客」  綁定 Email 要回傳的訊息
				returnMsg = "綁定成功！已寄發臨時密碼到您的信箱，請到信箱索取臨時密碼進行登入！";
				
			} else {
				
				//這是  「使用者」  綁定 Email 要回傳的訊息
				returnMsg = "綁定成功！您已經可以使用帳號或電子信箱(Email)進行登入。";
				
			}

			mailBindMsgDto.setStatus(true);
			mailBindMsgDto.setMsg(returnMsg);
			mailBindMsgDto.setUserMail(userMailDto.getUserMail());
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

		String userAccount = cryptionCommon.decoderBase64UserAccount(0, base64UserAccount);

		UserMailDto userMailDto = iUserInfoMapper.selectMailByAccount(userAccount);

		if(null == userMailDto) {

			userMailMsgDto = new UserMailMsgDto();
			userMailMsgDto.setStatus(false);
			userMailMsgDto.setMsg("目前無法重寄認證碼到您的信箱，已將您的問題提報，請您稍候再試。");

		} else {

			int userId = userMailDto.getUserId();
			String userMail = userMailDto.getUserMail();
			String verifyCode = generatorCommon.getVerifyCode();
			
			//先查詢上一個認證碼是否已經超過3分鐘
			MailVerifyCodeLastTimeDto mailVerifyCodeLastTimeDto =
					iUserVerifyMapper.selectLastCodeTimeByUserId(userId);
			
			if(null == mailVerifyCodeLastTimeDto) {

				//認證碼insert失敗
				userMailMsgDto.setStatus(false);
				userMailMsgDto.setMsg("目前無法重寄認證碼到您的信箱，已將您的問題提報，請您稍候再試。");
				return userMailMsgDto;
				
			} else {
				
				LocalDateTime now = LocalDateTime.now();
				Duration duration = Duration.between(
						mailVerifyCodeLastTimeDto.getSystemSendTime(), now);
				
				int reSendSecInt = (int) duration.getSeconds();
				
				if(reSendSecInt > 0 && reSendSecInt <= 180) {
					
					userMailMsgDto.setStatus(false);
					userMailMsgDto.setMsg("您的上一個認證碼還未過期，請查看您的信箱。");
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
				return userMailMsgDto;
			}

			//使用發送公用類，呼叫發送電子信箱認證碼的方法
			boolean sendMailStatus = sendCommon.sendVerifyCodeMail(
					userAccount, "訪客", userMail, verifyCode);

			if(!sendMailStatus) {

				//Email發送失敗
				userMailMsgDto.setStatus(false);
				userMailMsgDto.setMsg("目前無法重寄認證碼到您的信箱，已將您的問題提報，請您稍候再試。");
				return userMailMsgDto;
			}
			
			userMailMsgDto.setStatus(true);
			userMailMsgDto.setMsg("");
		}
		
		return userMailMsgDto;
	}

}
