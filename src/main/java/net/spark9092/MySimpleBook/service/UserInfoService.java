package net.spark9092.MySimpleBook.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.spark9092.MySimpleBook.common.CheckCommon;
import net.spark9092.MySimpleBook.common.CryptionCommon;
import net.spark9092.MySimpleBook.common.GeneratorCommon;
import net.spark9092.MySimpleBook.dto.user.LoginResultDto;
import net.spark9092.MySimpleBook.dto.user.UserAccCheckMsgDto;
import net.spark9092.MySimpleBook.dto.user.UserBindAccPwdMsgDto;
import net.spark9092.MySimpleBook.dto.user.UserInfoDto;
import net.spark9092.MySimpleBook.dto.user.UserInfoMsgDto;
import net.spark9092.MySimpleBook.dto.user.UserPwdChangeMsgDto;
import net.spark9092.MySimpleBook.entity.UserInfoEntity;
import net.spark9092.MySimpleBook.enums.SeqsNameEnum;
import net.spark9092.MySimpleBook.enums.SystemEnum;
import net.spark9092.MySimpleBook.mapper.IGuestMapper;
import net.spark9092.MySimpleBook.mapper.ISystemSeqsMapper;
import net.spark9092.MySimpleBook.mapper.IUserInfoMapper;
import net.spark9092.MySimpleBook.pojo.user.ChangePwdPojo;
import net.spark9092.MySimpleBook.pojo.user.LoginPojo;
import net.spark9092.MySimpleBook.pojo.user.UserAccCheckPojo;
import net.spark9092.MySimpleBook.pojo.user.UserBindAccPwdPojo;

@Service
public class UserInfoService {

	private static final Logger logger = LoggerFactory.getLogger(UserInfoService.class);

	@Autowired
	private IUserInfoMapper iUserInfoMapper;

	@Autowired
	private ISystemSeqsMapper iSystemSeqsMapper;

	@Autowired
	private IGuestMapper iGuestMapper;

	@Autowired
	private GeneratorCommon generatorCommon;

	@Autowired
	private CheckCommon checkCommon;

	@Autowired
	private CryptionCommon cryptionCommon;

	/**
	 * 使用者登入
	 * @param userLoginPojo
	 * @return
	 */
	public LoginResultDto userLogin(LoginPojo userLoginPojo) {

		LoginResultDto loginResultDto = new LoginResultDto();
		UserInfoEntity userInfoEntity = new UserInfoEntity();

		String userAcc = userLoginPojo.getUserAcc();
		userInfoEntity = iUserInfoMapper.selectByUserAcc(userAcc);

		if(userInfoEntity == null) {

			loginResultDto.setStatus(false);
			loginResultDto.setMsg("帳號或密碼錯誤");

		} else {

			logger.debug(userInfoEntity.toString());

			String inputPwd = userLoginPojo.getUserPwd();
			String dataPwd = userInfoEntity.getUserPwd();

			boolean isActive = userInfoEntity.isActive();
			boolean isDelete = userInfoEntity.isDelete();

			if(!isActive) {

				loginResultDto.setStatus(false);
				loginResultDto.setMsg("帳號已停用");

			} else if (isDelete) {

				loginResultDto.setStatus(false);
				loginResultDto.setMsg("帳號已刪除");

			} else if(!inputPwd.equals(dataPwd)) {

				loginResultDto.setStatus(false);
				loginResultDto.setMsg("帳號或密碼錯誤");

			} else {

				loginResultDto.setStatus(true);
				loginResultDto.setUserInfoEntity(userInfoEntity);

				//更新最後登入時間，如果發生錯誤也沒關係
				try {
					iUserInfoMapper.updateById(LocalDateTime.now(), userInfoEntity.getId());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		logger.debug(loginResultDto.toString());

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

		boolean createStatus = iUserInfoMapper.createUserByGuest(userName, UserPwd, SystemEnum.SYSTEM_USER_ID.getId(), guestSeq);

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
					iUserInfoMapper.updateById(LocalDateTime.now(), userInfoEntity.getId());
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

		dataCount = iUserInfoMapper.getGuestDataCount(userId);

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

			int userAccCount = iUserInfoMapper.selectUserCountByUserAcc(userAcc, userAccCheckPojo.getUserId());

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
		
		//再次檢查是否為系統相關帳號
		if(checkCommon.isSystemAccount(userAcc)) {
			
			userBindAccPwdMsgDto.setStatus(false);
			userBindAccPwdMsgDto.setMsg(String.format("%s 帳號已使用。", userAcc));

		} else {
		
			//可能會有時間差，更新使用者資料前，再次檢查使用者帳號是否重複
			UserAccCheckPojo userAccCheckPojo = new UserAccCheckPojo();
			userAccCheckPojo.setUserAcc(userAcc);
			
			UserAccCheckMsgDto userAccCheckMsgDto = this.checkUserAccByPojo(userAccCheckPojo);
			
			if(userAccCheckMsgDto.isStatus()) {
				
				String enPwd = cryptionCommon.encryptionPwd(userBindAccPwdPojo.getUserpwd());
				
				boolean bindAccPwdStatus = false;
				
				try {
					//確定帳號沒有重複，可以更新到資料庫了
					bindAccPwdStatus = iUserInfoMapper.bindAccPwdByUserId(
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
		}
		
		return userBindAccPwdMsgDto;
	}

	/**
	 * 取得使用者基本資料
	 * @param userId
	 * @return
	 */
	public UserInfoMsgDto getUserInfoById(UserInfoEntity userInfoEntity) {

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
}
