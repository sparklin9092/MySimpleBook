package net.spark9092.MySimpleBook.service;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.spark9092.MySimpleBook.common.CheckCommon;
import net.spark9092.MySimpleBook.common.CryptionCommon;
import net.spark9092.MySimpleBook.common.GeneratorCommon;
import net.spark9092.MySimpleBook.common.SendMailCommon;
import net.spark9092.MySimpleBook.dto.guest.UserAccCheckMsgDto;
import net.spark9092.MySimpleBook.dto.guest.UserBindAccPwdMsgDto;
import net.spark9092.MySimpleBook.dto.guest.UserBindMailMsgDto;
import net.spark9092.MySimpleBook.dto.user.LoginResultDto;
import net.spark9092.MySimpleBook.entity.UserInfoEntity;
import net.spark9092.MySimpleBook.enums.SeqsNameEnum;
import net.spark9092.MySimpleBook.enums.SystemEnum;
import net.spark9092.MySimpleBook.enums.VerifyTypeEnum;
import net.spark9092.MySimpleBook.mapper.IGuestMapper;
import net.spark9092.MySimpleBook.mapper.ISystemSeqsMapper;
import net.spark9092.MySimpleBook.mapper.IUserInfoMapper;
import net.spark9092.MySimpleBook.mapper.IUserVerifyMapper;
import net.spark9092.MySimpleBook.pojo.guest.UserAccCheckPojo;
import net.spark9092.MySimpleBook.pojo.guest.UserBindAccPwdPojo;
import net.spark9092.MySimpleBook.pojo.guest.UserBindMailPojo;

@Service
public class GuestService {

	private static final Logger logger = LoggerFactory.getLogger(GuestService.class);

	@Autowired
	private IUserInfoMapper iUserInfoMapper;

	@Autowired
	private IUserVerifyMapper iUserVerifyMapper;

	@Autowired
	private IGuestMapper iGuestMapper;

	@Autowired
	private ISystemSeqsMapper iSystemSeqsMapper;

	@Autowired
	private CheckCommon checkCommon;

	@Autowired
	private CryptionCommon cryptionCommon;

	@Autowired
	private GeneratorCommon generatorCommon;

	@Autowired
	private SendMailCommon sendCommon;

	/**
	 * 訪客登入
	 * @param ipAddress
	 * @param guestDevice
	 * @return
	 */
	public LoginResultDto guestLogin(String ipAddress, String guestDevice) {

		LoginResultDto loginResultDto = new LoginResultDto();

		//計算這個 IP 已經建立多少次訪客了
		int guestLoginTimes = iGuestMapper.selectLoginTimes(ipAddress);

		//超過 5 次就不能再用訪客登入了
		if(guestLoginTimes == 5 && !checkCommon.isWhiteIp(ipAddress)) {

			loginResultDto.setStatus(false);
			loginResultDto.setMsg("今日訪客數量已達系統上限。");
			return loginResultDto;
		}

		//對訪客取號碼牌(訪客序號)
		int guestSeq = iSystemSeqsMapper.selectSeqByName(SeqsNameEnum.GUEST.getName());

		logger.info("目前是第 " + guestSeq + " 位訪客使用致富寶典系統！");

		try {

			//訪客資料寫不了就算了，不要讓使用者對系統的初次感覺不好
			iGuestMapper.insertByValues(guestSeq, ipAddress, guestDevice);

		} catch (Exception e) {}

		String userName = "訪客";
		String UserPwd = generatorCommon.getUserPwd();

		boolean createStatus = iUserInfoMapper.insertGuest(userName, UserPwd, SystemEnum.SYSTEM_USER_ID.getId(), guestSeq);

		//訪客帳號建立成功之後，就走一般的登入流程
		if(createStatus) {

			UserInfoEntity userInfoEntity = iUserInfoMapper.selectGuestBySeq(guestSeq);

			if(userInfoEntity == null) {

				loginResultDto.setStatus(false);
				loginResultDto.setMsg("訪客數量已達系統上限");

			} else {

				loginResultDto.setStatus(true);
				loginResultDto.setUserInfoEntity(userInfoEntity);

				try {
					iUserInfoMapper.updateLastLoginTimeById(LocalDateTime.now(), userInfoEntity.getId());
				} catch (Exception e) {
					//更新最後登入時間，如果發生錯誤也沒關係
				}
			}
		} else {

			loginResultDto.setStatus(false);
			loginResultDto.setMsg("訪客數量已達系統上限");

		}

		return loginResultDto;
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
	 * 根據使用者輸入的Email，寄發認證碼給使用者，讓使用者可以使用認證碼綁定訪客帳號
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

}
