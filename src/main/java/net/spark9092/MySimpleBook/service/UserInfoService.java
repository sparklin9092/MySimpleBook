package net.spark9092.MySimpleBook.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import net.spark9092.MySimpleBook.common.CheckCommon;
import net.spark9092.MySimpleBook.common.CryptionCommon;
import net.spark9092.MySimpleBook.common.GeneratorCommon;
import net.spark9092.MySimpleBook.common.SendCommon;
import net.spark9092.MySimpleBook.dto.user.LoginResultDto;
import net.spark9092.MySimpleBook.dto.user.UserAccCheckMsgDto;
import net.spark9092.MySimpleBook.dto.user.UserBindAccPwdMsgDto;
import net.spark9092.MySimpleBook.dto.user.UserBindMailMsgDto;
import net.spark9092.MySimpleBook.dto.user.UserDeleteMsgDto;
import net.spark9092.MySimpleBook.dto.user.UserInfoDto;
import net.spark9092.MySimpleBook.dto.user.UserInfoModifyMsgDto;
import net.spark9092.MySimpleBook.dto.user.UserInfoMsgDto;
import net.spark9092.MySimpleBook.dto.user.UserPwdChangeMsgDto;
import net.spark9092.MySimpleBook.dto.user.UserMailDto;
import net.spark9092.MySimpleBook.dto.verify.MailBindMsgDto;
import net.spark9092.MySimpleBook.dto.verify.MailVerifyCodeLastTimeDto;
import net.spark9092.MySimpleBook.dto.verify.UserMailMsgDto;
import net.spark9092.MySimpleBook.dto.verify.UserMailVerifyDataDto;
import net.spark9092.MySimpleBook.entity.UserInfoEntity;
import net.spark9092.MySimpleBook.enums.SeqsNameEnum;
import net.spark9092.MySimpleBook.enums.SystemEnum;
import net.spark9092.MySimpleBook.enums.VerifyTypeEnum;
import net.spark9092.MySimpleBook.mapper.IAccountMapper;
import net.spark9092.MySimpleBook.mapper.IAccountTypesMapper;
import net.spark9092.MySimpleBook.mapper.IGuestMapper;
import net.spark9092.MySimpleBook.mapper.IIncomeItemsMapper;
import net.spark9092.MySimpleBook.mapper.IIncomeMapper;
import net.spark9092.MySimpleBook.mapper.ISpendItemsMapper;
import net.spark9092.MySimpleBook.mapper.ISpendMapper;
import net.spark9092.MySimpleBook.mapper.ISystemSeqsMapper;
import net.spark9092.MySimpleBook.mapper.ITransferMapper;
import net.spark9092.MySimpleBook.mapper.IUserInfoMapper;
import net.spark9092.MySimpleBook.mapper.IUserVerifyMapper;
import net.spark9092.MySimpleBook.pojo.user.ChangePwdPojo;
import net.spark9092.MySimpleBook.pojo.user.LoginPojo;
import net.spark9092.MySimpleBook.pojo.user.ModifyPojo;
import net.spark9092.MySimpleBook.pojo.user.UserAccCheckPojo;
import net.spark9092.MySimpleBook.pojo.user.UserBindAccPwdPojo;
import net.spark9092.MySimpleBook.pojo.user.UserBindMailPojo;
import net.spark9092.MySimpleBook.pojo.verify.BindMailPojo;

@Service
public class UserInfoService {

	private static final Logger logger = LoggerFactory.getLogger(UserInfoService.class);

	@Autowired
	private IUserInfoMapper iUserInfoMapper;

	@Autowired
	private IAccountMapper iAccountMapper;

	@Autowired
	private IAccountTypesMapper iAccountTypesMapper;

	@Autowired
	private IIncomeMapper iIncomeMapper;

	@Autowired
	private IIncomeItemsMapper iIncomeItemsMapper;

	@Autowired
	private ISpendMapper iSpendMapper;

	@Autowired
	private ISpendItemsMapper iSpendItemsMapper;

	@Autowired
	private ITransferMapper iTransferMapper;

	@Autowired
	private ISystemSeqsMapper iSystemSeqsMapper;

	@Autowired
	private IGuestMapper iGuestMapper;

	@Autowired
	private IUserVerifyMapper iUserVerifyMapper;

	@Autowired
	private GeneratorCommon generatorCommon;

	@Autowired
	private CheckCommon checkCommon;

	@Autowired
	private CryptionCommon cryptionCommon;

	@Autowired
	private SendCommon sendCommon;

	/**
	 * 使用者登入
	 * @param userLoginPojo
	 * @return
	 */
	public LoginResultDto userLogin(LoginPojo userLoginPojo) {

		LoginResultDto loginResultDto = new LoginResultDto();
		UserInfoEntity userInfoEntity = new UserInfoEntity();

		String userAcc = userLoginPojo.getUserAcc();
		userInfoEntity = iUserInfoMapper.selectUserInfoByAccount(userAcc);

		if(userInfoEntity == null) {

			loginResultDto.setStatus(false);
			loginResultDto.setMsg("帳號或密碼錯誤");

		} else {

			String inputPwd = userLoginPojo.getUserPwd();
			String dataPwd = userInfoEntity.getUserPwd();

			boolean isActive = userInfoEntity.isActive();
			boolean isDelete = userInfoEntity.isDelete();

			if(!isActive) {

				loginResultDto.setStatus(false);
				loginResultDto.setMsg("帳號已停用。");

			} else if (isDelete) {

				loginResultDto.setStatus(false);
				loginResultDto.setMsg("帳號或密碼錯誤。");

			} else if(!inputPwd.equals(dataPwd)) {

				loginResultDto.setStatus(false);
				loginResultDto.setMsg("帳號或密碼錯誤。");

			} else {

				loginResultDto.setStatus(true);
				loginResultDto.setUserInfoEntity(userInfoEntity);

				try {
					iUserInfoMapper.updateLastLoginTimeById(LocalDateTime.now(), userInfoEntity.getId());
				} catch (Exception e) {
					//更新最後登入時間，如果發生錯誤也沒關係
				}
			}
		}

		return loginResultDto;
	}

	/**
	 * 訪客登入
	 * @param ipAddress
	 * @param guestDevice
	 * @return
	 */
	public LoginResultDto guestLogin(String ipAddress, String guestDevice) {

		LoginResultDto loginResultDto = new LoginResultDto();

		//計算這個 IP 已經建立多少次訪客了
		int guestLoginTimes = iGuestMapper.getLoginTimes(ipAddress);

		//超過 5 次就不能再用訪客登入了
		if(guestLoginTimes == 5 && !checkCommon.isWhiteIp(ipAddress)) {

			loginResultDto.setStatus(false);
			loginResultDto.setMsg("今日訪客數量已達系統上限。");
			return loginResultDto;
		}

		//對訪客取號碼牌(訪客序號)
		int guestSeq = iSystemSeqsMapper.getSeq(SeqsNameEnum.GUEST.getName());

		logger.info("目前是第 " + guestSeq + " 位訪客使用致富寶典系統！");

		try {

			//訪客資料寫不了就算了，不要讓使用者對系統的初次感覺不好
			iGuestMapper.createByValues(guestSeq, ipAddress, guestDevice);

		} catch (Exception e) {}

		String userName = "訪客";
		String UserPwd = generatorCommon.getUserPwd();

		boolean createStatus = iUserInfoMapper.createGuest(userName, UserPwd, SystemEnum.SYSTEM_USER_ID.getId(), guestSeq);

		//訪客帳號建立成功之後，就走一般的登入流程
		if(createStatus) {

			UserInfoEntity userInfoEntity = iUserInfoMapper.selectGuestBySeq(guestSeq);

			if(userInfoEntity == null) {

				loginResultDto.setStatus(false);
				loginResultDto.setMsg("訪客數量已達系統上限");

			} else {

				loginResultDto.setStatus(true);
				loginResultDto.setUserInfoEntity(userInfoEntity);

				//更新最後登入時間，如果發生錯誤也沒關係
				try {
					iUserInfoMapper.updateLastLoginTimeById(LocalDateTime.now(), userInfoEntity.getId());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {

			loginResultDto.setStatus(false);
			loginResultDto.setMsg("訪客數量已達系統上限");

		}

		return loginResultDto;
	}

	/**
	 * 取得訪客目前的資料數量，用於提醒訪客要綁定帳號，包含：
	 *
	 * 轉帳(transfer)、
	 * 收入(income)、收入項目(income items)、
	 * 支出(spend)、支出項目(spend items)、
	 * 帳戶(account)、帳戶類別(account types)，
	 *
	 * 收入項目、支出項目 系統各預設 1 筆，計算的時候要扣掉，
	 * 帳戶 系統是預設 2 筆，計算的時候，也要扣掉
	 *
	 * @param userId
	 * @return Guest data count 訪客資料數量
	 */
	public int getGuestDataCount(int userId) {

		int dataCount = 0;

		dataCount = iUserInfoMapper.selectGuestDataCount(userId);

		return dataCount;
	}

	/**
	 * 檢查使用者帳號 (user_info.user_account) 是否已存在
	 * @param userAccCheckPojo
	 * @return
	 */
	public UserAccCheckMsgDto checkUserAccByPojo(UserAccCheckPojo userAccCheckPojo) {

		UserAccCheckMsgDto userAccCheckMsgDto = new UserAccCheckMsgDto();

		String userAcc = userAccCheckPojo.getUserAcc();

		if(null == userAcc || userAcc.equals("") || userAcc.isEmpty()) {

			userAccCheckMsgDto.setStatus(false);
			userAccCheckMsgDto.setMsg("請輸入帳號。");

		} else if(checkCommon.isSystemAccount(userAcc)) { //檢查是否為系統相關帳號

			userAccCheckMsgDto.setStatus(false);
			userAccCheckMsgDto.setMsg(String.format("%s 帳號已使用。", userAcc));

		} else {

			int userAccCount = iUserInfoMapper.selectExistUserCountByAcc(userAcc, userAccCheckPojo.getUserId());

			if(userAccCount == 0) {

				userAccCheckMsgDto.setStatus(true);
				userAccCheckMsgDto.setMsg("");

			} else {

				userAccCheckMsgDto.setStatus(false);
				userAccCheckMsgDto.setMsg(String.format("%s 帳號已使用。", userAcc));

			}
		}

		return userAccCheckMsgDto;
	}

	/**
	 * 根據使用者輸入的帳號密碼，綁定訪客帳號，並修改訪客身份為使用者
	 * @param userBindAccPwdPojo
	 * @return
	 */
	public UserBindAccPwdMsgDto bindUserByAccPwdPojo(UserBindAccPwdPojo userBindAccPwdPojo) {

		UserBindAccPwdMsgDto userBindAccPwdMsgDto = new UserBindAccPwdMsgDto();

		String userAcc = userBindAccPwdPojo.getUserAcc();

		//可能會有時間差，更新使用者資料前，再次檢查使用者帳號是否重複
		UserAccCheckPojo userAccCheckPojo = new UserAccCheckPojo();
		userAccCheckPojo.setUserAcc(userAcc);

		UserAccCheckMsgDto userAccCheckMsgDto = this.checkUserAccByPojo(userAccCheckPojo);

		if(userAccCheckMsgDto.isStatus()) {

			String enPwd = cryptionCommon.encryptionPwd(userBindAccPwdPojo.getUserpwd());

			boolean bindAccPwdStatus = false;

			try {
				//確定帳號沒有重複，可以更新到資料庫了
				bindAccPwdStatus = iUserInfoMapper.updateAccPwdByUserId(
						userBindAccPwdPojo.getUserId(), userAcc,
						enPwd);
			} catch (Exception ex) {
				//可能會因為時間差，導致更新失敗，因為使用者帳號 (user_info.user_account) 只能是唯一值
			}

			if(bindAccPwdStatus) {

				userBindAccPwdMsgDto.setStatus(true);
				userBindAccPwdMsgDto.setMsg("");

			} else {

				userBindAccPwdMsgDto.setStatus(false);
				userBindAccPwdMsgDto.setMsg(String.format("太可惜了！這個 %s 帳號被其他人搶先使用了，再換一個試試看。", userAcc));

			}
		} else {

			//帳號有重複，回傳訊息給使用者
			userBindAccPwdMsgDto.setStatus(false);
			userBindAccPwdMsgDto.setMsg(String.format("太可惜了！這個 %s 帳號被其他人搶先使用了，再換一個試試看。", userAcc));

		}

		return userBindAccPwdMsgDto;
	}

	/**
	 * 根據使用者輸入的Email，來綁定訪客帳號，並修改訪客身份為使用者
	 * 然後再寄發臨時密碼給使用者，讓使用者可以使用Email+臨時密碼進行登入
	 * @param userBindAccPwdPojo
	 * @return
	 */
	public UserBindMailMsgDto bindUserByMailPojo(UserBindMailPojo userBindMailPojo) {

		UserBindMailMsgDto userBindMailMsgDto = new UserBindMailMsgDto();

		int userId = userBindMailPojo.getUserId();
		String userAccount = userBindMailPojo.getUserAccount();
		String userName = userBindMailPojo.getUserName();
		String userMail = userBindMailPojo.getUserEmail();
		String verifyCode = generatorCommon.getVerifyCode();

		if(null == userMail || userMail.equals("") || userMail.isEmpty()) {

			userBindMailMsgDto.setStatus(false);
			userBindMailMsgDto.setMsg("請輸入電子信箱(Email)。");
			return userBindMailMsgDto;
		}

		//先進行Email正規化驗證
		if(!checkCommon.checkMail(userMail)) {

			userBindMailMsgDto.setStatus(false);
			userBindMailMsgDto.setMsg("電子信箱(Email)格式不正確。");
			return userBindMailMsgDto;
		}
		
		//檢查有無重複的Email已經被綁定
		int selectCount = iUserInfoMapper.selectExistBindMailCount(userMail);
		if(selectCount != 0) {

			//已有相同Email被綁定
			userBindMailMsgDto.setStatus(false);
			userBindMailMsgDto.setMsg("這個電子信箱(Email)已綁定，請更換其他電子信箱(Email)。");
			return userBindMailMsgDto;
		}
			
		//如果使用者Email正規化通過，就更新 user_info.email 這個欄位，
		//讓使用者去驗證的時候，系統找得到Email
		boolean updateMailStatus = iUserInfoMapper.updateMailByUserId(userId, userMail);
		
		if(!updateMailStatus) {

			//Email更新失敗
			userBindMailMsgDto.setStatus(false);
			userBindMailMsgDto.setMsg("目前無法寄發電子信箱(Email)的認證碼，請稍後再試，或是更換其他綁定方法。");
			return userBindMailMsgDto;
		}

		//存入user_verify，給使用者來驗證
		boolean insertStatus = iUserVerifyMapper.insertMailVerifyCodeByUserId(
				userId, VerifyTypeEnum.EMAIL.getType(), verifyCode);

		if(!insertStatus) {

			//認證碼insert失敗
			userBindMailMsgDto.setStatus(false);
			userBindMailMsgDto.setMsg("目前無法寄發電子信箱(Email)的認證碼，請稍後再試，或是更換其他綁定方法。");
			return userBindMailMsgDto;
		}

		//使用發送公用類，呼叫發送電子信箱認證碼的方法
		boolean sendMailStatus = sendCommon.sendVerifyCodeMail(
				userAccount, userName, userMail, verifyCode);

		if(!sendMailStatus) {

			//Email發送失敗
			userBindMailMsgDto.setStatus(false);
			userBindMailMsgDto.setMsg("目前無法寄發電子信箱(Email)的認證碼，請稍後再試，或是更換其他綁定方法。");
			return userBindMailMsgDto;
		}

		userBindMailMsgDto.setStatus(true);
		userBindMailMsgDto.setMsg("");
		
		return userBindMailMsgDto;
	}

	/**
	 * 取得使用者基本資料
	 * @param userId
	 * @return
	 */
	public UserInfoMsgDto getUserInfoByEntity(UserInfoEntity userInfoEntity) {

		UserInfoMsgDto userInfoMsgDto = new UserInfoMsgDto();

		if(null == userInfoEntity) {

			userInfoMsgDto.setStatus(false);
			userInfoMsgDto.setMsg("找不到基本資料");

		} else {

			UserInfoDto userInfoDto = new UserInfoDto();

			String dePwd = cryptionCommon.decryptionPwd(userInfoEntity.getUserPwd());

			String maskPwd = "";
			for(int p=0; p<dePwd.length(); p++) {
				maskPwd = maskPwd + "*";
			}

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日");

	        String createDate = userInfoEntity.getCreateDateTime().format(formatter);

			userInfoDto.setUserName(userInfoEntity.getUserName());
			userInfoDto.setUserAcc(userInfoEntity.getUserAccount());
			userInfoDto.setMaskPwd(maskPwd);
			userInfoDto.setUserEmail(userInfoEntity.getUserEmail());
			userInfoDto.setUserPhone(userInfoEntity.getUserPhone());
			userInfoDto.setCreateDate(createDate);

			userInfoMsgDto.setStatus(true);
			userInfoMsgDto.setMsg("");
			userInfoMsgDto.setDto(userInfoDto);
		}

		return userInfoMsgDto;
	}

	/**
	 * 要先驗證舊密碼，再修改新密碼
	 * @param changePwdPojo
	 * @return
	 */
	public UserPwdChangeMsgDto modifyPwd(ChangePwdPojo changePwdPojo, String oriPwd) {

		UserPwdChangeMsgDto userPwdChangeMsgDto = new UserPwdChangeMsgDto();

		String userOldPwd = changePwdPojo.getOldPwd();
		String userNewPwd = changePwdPojo.getNewPwd();

		if(null == userOldPwd || userOldPwd.equals("") || userOldPwd.isEmpty()) {

			userPwdChangeMsgDto.setStatus(false);
			userPwdChangeMsgDto.setMsg("請輸入舊密碼。");

		} else if(null == userNewPwd || userNewPwd.equals("") || userNewPwd.isEmpty()) {

			userPwdChangeMsgDto.setStatus(false);
			userPwdChangeMsgDto.setMsg("請輸入新密碼。");

		} else {

			//把資料庫裡的密碼解密，比對使用者輸入的舊密碼是否相同
			String deOriPwd = cryptionCommon.decryptionPwd(oriPwd);

			if(deOriPwd.equals(userOldPwd)) {

				//如果都相同，再把新密碼加密，存入資料庫
				String enNewPwd = cryptionCommon.encryptionPwd(userNewPwd);

				boolean updateStatus = iUserInfoMapper.updateUserNewPwdById(
						changePwdPojo.getUserId(), enNewPwd);

				if(updateStatus) {

					userPwdChangeMsgDto.setStatus(true);
					userPwdChangeMsgDto.setMsg("");

				} else {

					userPwdChangeMsgDto.setStatus(false);
					userPwdChangeMsgDto.setMsg("舊密碼不正確，請重新輸入，或請客服協助處理。");

				}

			} else {

				userPwdChangeMsgDto.setStatus(false);
				userPwdChangeMsgDto.setMsg("舊密碼不正確，請重新輸入。");
			}
		}

		return userPwdChangeMsgDto;
	}

	/**
	 * 修改使用者基本資料，只能修改 名稱、帳號、Email、手機號碼
	 * @param modifyPojo
	 * @return
	 */
	public UserInfoModifyMsgDto modifyByPojo(ModifyPojo modifyPojo) {

		UserInfoModifyMsgDto userInfoModifyMsgDto = new UserInfoModifyMsgDto();

		int userId = modifyPojo.getUserId();
		String userName = modifyPojo.getUserName();
		//String userAcc = modifyPojo.getUserAccount();
		String userEmail = modifyPojo.getUserEmail();
		//String userPhone = modifyPojo.getUserPhone();

		//更新使用者資料前，先檢查使用者帳號是否重複
		UserAccCheckPojo userAccCheckPojo = new UserAccCheckPojo();
		userAccCheckPojo.setUserId(userId);
		//userAccCheckPojo.setUserAcc(userAcc);

		//UserAccCheckMsgDto userAccCheckMsgDto = this.checkUserAccByPojo(userAccCheckPojo);

		/*
		if(userAccCheckMsgDto.isStatus()) {

			try {
			*/
			//確定帳號沒有重複，就可以更新到資料庫了
			boolean updateStatus = iUserInfoMapper.updateUserInfoById(
					userId, userName, userEmail);

			if(updateStatus) {

				//基本資料更新後，重新把新的資料寫入到entity裡面，因為回到controller要更新session
				UserInfoEntity entity = modifyPojo.getEntity();
				entity.setUserName(userName);
				//entity.setUserAccount(userAcc);
				entity.setUserEmail(userEmail);
				//entity.setUserPhone(userPhone);

				userInfoModifyMsgDto.setStatus(true);
				userInfoModifyMsgDto.setMsg("");
				userInfoModifyMsgDto.setEntity(entity);

			} else {

				userInfoModifyMsgDto.setStatus(false);
				userInfoModifyMsgDto.setMsg("更新基本資料發生錯誤，請稍後再嘗試。");

			}
			/*
			} catch(Exception ex) {

				//如果因為時間差，導致更新帳號發生異常錯誤，從這裡攔截

				userInfoModifyMsgDto.setStatus(false);
				userInfoModifyMsgDto.setMsg(String.format("太可惜了！這個 %s 帳號被其他人搶先使用了，再換一個試試看。", userAcc));
			}
		} else {

			userInfoModifyMsgDto.setStatus(false);
			userInfoModifyMsgDto.setMsg(userAccCheckMsgDto.getMsg());
		}
		*/

		return userInfoModifyMsgDto;
	}

	/**
	 * 刪除使用者帳號，連同以下內容都刪除
	 * 帳戶(account)、帳戶類型(account_types)
	 * 收入(income)、收入項目(income_items)
	 * 支出(spend)、支出項目(spend_items)
	 * 轉帳(transfer)
	 * @param id
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public UserDeleteMsgDto deleteUserById(int userId) throws Exception {

		UserDeleteMsgDto userDeleteMsgDto = new UserDeleteMsgDto();

		//刪除前，先查詢有無資料，避免發生錯誤
		if(iAccountMapper.selectListByUserId(userId).size() != 0) {

			boolean accountDeleteStatus = iAccountMapper.deleteAllByUserId(userId);

			if(!accountDeleteStatus)
				throw new Exception("刪除使用者ID: "+userId+" 的帳戶時，發生錯誤。");
		}

		//刪除前，先查詢有無資料，避免發生錯誤
		if(iAccountTypesMapper.selectItemListByUserId(userId).size() != 0) {

			boolean accountTypesDeleteStatus = iAccountTypesMapper.deleteAllByUserId(userId);

			if(!accountTypesDeleteStatus)
				throw new Exception("刪除使用者ID: "+userId+" 的帳戶類型時，發生錯誤。");
		}

		//刪除前，先查詢有無資料，避免發生錯誤
		if(iIncomeMapper.selectRecordsByUserId(userId, "0001-01-01", "9999-12-31").size() != 0) {

			boolean incomeDeleteStatus = iIncomeMapper.deleteAllByUserId(userId);

			if(!incomeDeleteStatus)
				throw new Exception("刪除使用者ID: "+userId+" 的收入時，發生錯誤。");
		}

		//刪除前，先查詢有無資料，避免發生錯誤
		if(iIncomeItemsMapper.selectListByUserId(userId).size() != 0) {

			boolean incomeItemsDeleteStatus = iIncomeItemsMapper.deleteAllByUserId(userId);

			if(!incomeItemsDeleteStatus)
				throw new Exception("刪除使用者ID: "+userId+" 的收入項目時，發生錯誤。");
		}

		//刪除前，先查詢有無資料，避免發生錯誤
		if(iSpendMapper.selectRecordsByUserId(userId, "0001-01-01", "9999-12-31").size() != 0) {

			boolean spendDeleteStatus = iSpendMapper.deleteAllByUserId(userId);

			if(!spendDeleteStatus)
				throw new Exception("刪除使用者ID: "+userId+" 的支出時，發生錯誤。");
		}

		//刪除前，先查詢有無資料，避免發生錯誤
		if(iSpendItemsMapper.selectItemListByUserId(userId).size() != 0) {

			boolean spendItemsDeleteStatus = iSpendItemsMapper.deleteAllByUserId(userId);

			if(!spendItemsDeleteStatus)
				throw new Exception("刪除使用者ID: "+userId+" 的支出項目時，發生錯誤。");
		}

		//刪除前，先查詢有無資料，避免發生錯誤
		if(iTransferMapper.selectRecordsByUserId(userId, "0001-01-01", "9999-12-31").size() != 0) {

			boolean transferDeleteStatus = iTransferMapper.deleteAllByUserId(userId);

			if(!transferDeleteStatus)
				throw new Exception("刪除使用者ID: "+userId+" 的轉帳時，發生錯誤。");
		}

		boolean userDeleteStatus = iUserInfoMapper.deleteByUserId(userId);

		if(userDeleteStatus) {

			userDeleteMsgDto.setStatus(true);
			userDeleteMsgDto.setMsg("");

		} else {

			throw new Exception("刪除使用者ID: "+userId+" 的使用者資料時，發生錯誤。");
		}

		return userDeleteMsgDto;
	}

	/**
	 * 傳入 base64 邊碼後的使用者帳號，解碼後，查詢使用者電子信箱
	 * @param base64UserAccount
	 * @return
	 */
	public UserMailMsgDto getUserMailByAccount(String base64UserAccount) {

		UserMailMsgDto userMailMsgDto = new UserMailMsgDto();
		Base64.Decoder decoder = Base64.getDecoder();

		try {

			//使用Base64對帳號進行解碼
			String userAccount = new String(decoder.decode(base64UserAccount), "UTF-8");

			//用帳號查ID、mail、最後寄發認證碼的時間
			userMailMsgDto = iUserInfoMapper.selectMailByAccount(userAccount);

			if(null != userMailMsgDto) {
				
				String reSendSec = "";
				
				//查詢還有多久可以重寄認證碼
				MailVerifyCodeLastTimeDto mailVerifyCodeLastTimeDto = 
						iUserVerifyMapper.selectLastCodeTimeByUserId(
						userMailMsgDto.getUserId());

				if(null == mailVerifyCodeLastTimeDto) {
					
					//因為使用者看到信件，加上點擊網址需要時間，
					//所以如果真的沒有查到時間，最慢也就設定120秒就可以了
					reSendSec = "120";
					
				} else {
					
					if(!mailVerifyCodeLastTimeDto.isUsed()) {
						
						LocalDateTime now = LocalDateTime.now();
						Duration duration = Duration.between(
								mailVerifyCodeLastTimeDto.getSystemSendTime(), now);
						
						int reSendSecInt = (int) duration.getSeconds();
						
						logger.debug("reSendSecInt: " + reSendSecInt);
						
						if(reSendSecInt > 180) reSendSecInt = 180;
						
						reSendSec = String.valueOf(180-reSendSecInt);
						
						logger.debug("reSendSec: " + reSendSec);
					}
				}
				
				userMailMsgDto.setStatus(true);
				userMailMsgDto.setMsg("");
				userMailMsgDto.setReSendSec(reSendSec);
				userMailMsgDto.setUserId(0);
				userMailMsgDto.setUsed(mailVerifyCodeLastTimeDto.isUsed());

				return userMailMsgDto;

			}

		} catch (Exception e) {}

		userMailMsgDto = new UserMailMsgDto();
		userMailMsgDto.setMsg("");
		userMailMsgDto.setUserMail("");
		userMailMsgDto.setStatus(false);
		userMailMsgDto.setUserId(0);
		userMailMsgDto.setUsed(false);

		return userMailMsgDto;
	}

	/**
	 * 綁定使用者信箱，然後寄發臨時密碼給使用者
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
					mailBindMsgDto.setMsg("認證碼已過期，請您重新綁定。");
					
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
					mailBindMsgDto.setMsg("認證碼已使用，請您重新綁定。");
					
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
					mailBindMsgDto.setMsg("目前無法綁定電子信箱，請您稍後再試。");
					
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
					mailBindMsgDto.setMsg("目前無法為您產生臨時密碼，請您稍後再試。");
				}
			}

			//寄發臨時密碼給使用者
			if(bindSign) {
				
				//根據使用者ID，查詢要寄發臨時密碼的Email
				UserMailDto userMailDto = iUserInfoMapper.selectMailByUserId(userId);
				
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
						mailBindMsgDto.setMsg("目前無法寄發臨時密碼給您，請您稍後再試。");
					}
				}
			}
			
		} catch (Exception e) {
			
			logger.error("使用Base64對使用者帳號解碼時，發生問題！！！");
			
			mailBindMsgDto.setStatus(false);
			mailBindMsgDto.setMsg("目前無法寄發臨時密碼給您，請您稍後再試。");
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

	public UserMailMsgDto resSendVerifyCodeMailByAccount(String base64UserAccount) {

		UserMailMsgDto userMailMsgDto = new UserMailMsgDto();
		Base64.Decoder decoder = Base64.getDecoder();

		try {

			String userAccount = new String(decoder.decode(base64UserAccount), "UTF-8");

			userMailMsgDto = iUserInfoMapper.selectMailByAccount(userAccount);
			
			if(null == userMailMsgDto) {
				
				userMailMsgDto = new UserMailMsgDto();
				userMailMsgDto.setStatus(false);
				userMailMsgDto.setMsg("目前無法重新寄發認證碼到您的信箱，請您稍候再重新操作一次。");
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
					userMailMsgDto.setMsg("目前無法重新寄發認證碼到您的信箱，請您稍候再重新操作一次。");
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
						userMailMsgDto.setMsg("您的上一個認證碼還未過期，請先使用上一個認證碼。");
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
					userMailMsgDto.setMsg("目前無法重新寄發認證碼到您的信箱，請您稍候再重新操作一次。");
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
					userMailMsgDto.setMsg("目前無法重新寄發認證碼到您的信箱，請您稍候再重新操作一次。");
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
			
			userMailMsgDto = new UserMailMsgDto();
			userMailMsgDto.setStatus(false);
			userMailMsgDto.setMsg("目前無法重新寄發認證碼到您的信箱，請您稍候再重新操作一次。");
			userMailMsgDto.setReSendSec("");
			userMailMsgDto.setUserMail("");
			userMailMsgDto.setUserId(0);
		}
		
		return userMailMsgDto;
	}
}
