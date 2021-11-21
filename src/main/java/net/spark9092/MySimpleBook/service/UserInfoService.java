package net.spark9092.MySimpleBook.service;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import net.spark9092.MySimpleBook.common.CheckCommon;
import net.spark9092.MySimpleBook.common.CryptionCommon;
import net.spark9092.MySimpleBook.common.GeneratorCommon;
import net.spark9092.MySimpleBook.common.GetCommon;
import net.spark9092.MySimpleBook.common.SendMailCommon;
import net.spark9092.MySimpleBook.dto.login.ResultMsgDto;
import net.spark9092.MySimpleBook.dto.user.BindEmailMsgDto;
import net.spark9092.MySimpleBook.dto.user.DeleteMsgDto;
import net.spark9092.MySimpleBook.dto.user.EmailStatusDto;
import net.spark9092.MySimpleBook.dto.user.InfoDto;
import net.spark9092.MySimpleBook.dto.user.InfoModifyMsgDto;
import net.spark9092.MySimpleBook.dto.user.InfoMsgDto;
import net.spark9092.MySimpleBook.dto.user.MailDataDto;
import net.spark9092.MySimpleBook.dto.user.PwdChangeMsgDto;
import net.spark9092.MySimpleBook.dto.verify.MailVerifyCodeLastTimeDto;
import net.spark9092.MySimpleBook.dto.verify.UserMailDto;
import net.spark9092.MySimpleBook.dto.verify.UserMailMsgDto;
import net.spark9092.MySimpleBook.entity.UserInfoEntity;
import net.spark9092.MySimpleBook.enums.UserEmailStatusEnum;
import net.spark9092.MySimpleBook.enums.VerifyTypeEnum;
import net.spark9092.MySimpleBook.mapper.IAccountMapper;
import net.spark9092.MySimpleBook.mapper.IAccountTypesMapper;
import net.spark9092.MySimpleBook.mapper.IIncomeItemsMapper;
import net.spark9092.MySimpleBook.mapper.IIncomeMapper;
import net.spark9092.MySimpleBook.mapper.ISpendItemsMapper;
import net.spark9092.MySimpleBook.mapper.ISpendMapper;
import net.spark9092.MySimpleBook.mapper.ITransferMapper;
import net.spark9092.MySimpleBook.mapper.IUserInfoMapper;
import net.spark9092.MySimpleBook.mapper.IUserVerifyMapper;
import net.spark9092.MySimpleBook.pojo.user.BindEmailPojo;
import net.spark9092.MySimpleBook.pojo.user.ChangePwdPojo;
import net.spark9092.MySimpleBook.pojo.user.LoginPojo;
import net.spark9092.MySimpleBook.pojo.user.ModifyPojo;

@Service
public class UserInfoService extends BaseService {

//	private static final Logger logger = LoggerFactory.getLogger(UserInfoService.class);

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
	private IUserVerifyMapper iUserVerifyMapper;

	@Autowired
	private CheckCommon checkCommon;

	@Autowired
	private CryptionCommon cryptionCommon;

	@Autowired
	private GeneratorCommon generatorCommon;

	@Autowired
	private GetCommon getCommon;

	@Autowired
	private SendMailCommon sendMailCommon;

	/**
	 * 使用者登入
	 * @param userLoginPojo
	 * @return
	 */
	public ResultMsgDto userLogin(LoginPojo userLoginPojo) {

		ResultMsgDto loginResultDto = new ResultMsgDto();
		UserInfoEntity userInfoEntity = new UserInfoEntity();

		String userAcc = userLoginPojo.getUserAcc();
		userInfoEntity = iUserInfoMapper.selectUserInfoByAccount(userAcc);

		if(userInfoEntity == null) {

			loginResultDto.setStatus(false);
			loginResultDto.setMsg("您的帳號或密碼錯誤。");

		} else {

			String inputPwd = userLoginPojo.getUserPwd();
			String dataPwd = userInfoEntity.getUserPwd();

			boolean isActive = userInfoEntity.isActive();
			boolean isDelete = userInfoEntity.isDelete();

			if(!isActive) {

				loginResultDto.setStatus(false);
				loginResultDto.setMsg("您的帳號已停用。");

			} else if (isDelete) {

				loginResultDto.setStatus(false);
				loginResultDto.setMsg("您的帳號或密碼錯誤。");

			} else if(!inputPwd.equals(dataPwd)) {

				loginResultDto.setStatus(false);
				loginResultDto.setMsg("您的帳號或密碼錯誤。");

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
	 * 取得使用者基本資料
	 * @param userId
	 * @return
	 */
	public InfoMsgDto getUserInfoByEntity(UserInfoEntity userInfoEntity) {

		InfoMsgDto userInfoMsgDto = new InfoMsgDto();

		if(null == userInfoEntity) {

			userInfoMsgDto.setStatus(false);
			userInfoMsgDto.setMsg("似乎找不到您的基本資料，已將您的問題提報，請稍後再試試看。");

		} else {

			InfoDto userInfoDto = new InfoDto();

			String dePwd = cryptionCommon.decryptionAESPwd(
					userInfoEntity.getId(), userInfoEntity.getUserPwd());

			String maskPwd = "";
			for(int p=0; p<dePwd.length(); p++) {
				maskPwd = maskPwd + "*";
			}

	        String createDate = getCommon.getFormatDate(userInfoEntity.getCreateDateTime());

	        //查詢目前 User Email 資料
	        EmailStatusDto emailStatusDto = iUserInfoMapper.selectEmailStatusById(userInfoEntity.getId());

	        //檢查目前 User Email 在什麼階段，1: 已寄發、2: 可綁定(預設)、3: 已認證
	        int emailStatus = UserEmailStatusEnum.CANBIND.getStatus();
	        if(null != emailStatusDto) {
		        //1: 已寄發，表示系統已經寄發認證碼給使用者，
		        //   而且認證碼目前是可以用的狀態，
		        //   還沒有過期、已使用、被停用、被刪除
	        	if(emailStatusDto.getUserEmail() != "" &&
        			emailStatusDto.getVerifyCode() != "" &&
        			!emailStatusDto.isVerifyCodeUsed() &&
        			emailStatusDto.isVerifyCodeActive() &&
        			!emailStatusDto.isVerifyCodeDelete()) {

	        		//已條件都通過，表示這個認證碼可用，但還要判斷是否已經過期
	        		LocalDateTime now = LocalDateTime.now(); //驗證當下的時間
	    			Duration duration = Duration.between(
	    					emailStatusDto.getSysSendTime(), now); // 與系統寄發時間之差

	    			//如果超過 3 分鐘
	    			if(duration.toMinutes() < 3) {

	    				emailStatus = UserEmailStatusEnum.ISSEND.getStatus();
	    			}
	        	}

		        //2: 可綁定，表示使用者從來沒有索取過認證碼，或是認證碼不可使用了
	        	//預設就是可綁定，所以這裡不需要寫任何判斷

		        //3: 已認證，表示使用者已經綁定這個Email了，在user_info.email有值，
		        //   而且在user_verify也找的到對應的認證碼，還有認證碼已經被使用了
	        	if(emailStatusDto.getUserEmail() != "" &&
        			emailStatusDto.getVerifyCode() != "" &&
        			emailStatusDto.isVerifyCodeUsed()) {

	        		emailStatus = UserEmailStatusEnum.ISBIND.getStatus();
	        	}
	        }

			userInfoDto.setUserName(userInfoEntity.getUserName());
			userInfoDto.setUserAcc(userInfoEntity.getUserAccount());
			userInfoDto.setMaskPwd(maskPwd);
			userInfoDto.setUserEmail(userInfoEntity.getUserEmail());
			userInfoDto.setEmailStatus(emailStatus);
			userInfoDto.setUserPhone(userInfoEntity.getUserPhone());
			userInfoDto.setCreateDate(createDate);

			userInfoMsgDto.setStatus(true);
			userInfoMsgDto.setMsg("");
			userInfoMsgDto.setDto(userInfoDto);
		}

		return userInfoMsgDto;
	}

	/**
	 * 傳入 base64 邊碼後的使用者帳號，解碼後，查詢使用者電子信箱
	 * @param base64UserAccount
	 * @return
	 */
	public UserMailMsgDto getUserMailByAccount(String base64UserAccount) {

		UserMailMsgDto userMailMsgDto = new UserMailMsgDto();

		try {

			//使用Base64對帳號進行解碼
			String userAccount = cryptionCommon.decoderBase64UserAccount(
					0, base64UserAccount);

			//用帳號查ID、mail、最後寄發認證碼的時間
			UserMailDto userMailDto = iUserInfoMapper.selectMailByAccount(userAccount);

			if(null != userMailDto) {

				String reSendSec = "";

				//查詢還有多久可以重寄認證碼
				MailVerifyCodeLastTimeDto mailVerifyCodeLastTimeDto =
						iUserVerifyMapper.selectLastCodeTimeByUserId(userMailDto.getUserId());

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

						if(reSendSecInt > 180) reSendSecInt = 180;

						reSendSec = String.valueOf(180-reSendSecInt);
					}
				}

				userMailMsgDto.setStatus(true);
				userMailMsgDto.setMsg("");
				userMailMsgDto.setReSendSec(reSendSec);
				userMailMsgDto.setUsed(mailVerifyCodeLastTimeDto.isUsed());
				userMailMsgDto.setUserMail(userMailDto.getUserMail());

				return userMailMsgDto;

			}

		} catch (Exception e) {}

		userMailMsgDto = new UserMailMsgDto();
		userMailMsgDto.setMsg("");
		userMailMsgDto.setStatus(false);

		return userMailMsgDto;
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
	 * 要先驗證舊密碼，再修改新密碼
	 * @param changePwdPojo
	 * @return
	 */
	public PwdChangeMsgDto modifyPwd(ChangePwdPojo changePwdPojo, String oriPwd) {

		PwdChangeMsgDto userPwdChangeMsgDto = new PwdChangeMsgDto();

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
			String deOriPwd = cryptionCommon.decryptionAESPwd(
					changePwdPojo.getUserId(), oriPwd);

			if(deOriPwd.equals(userOldPwd)) {

				//如果都相同，再把新密碼加密，存入資料庫
				String enNewPwd = cryptionCommon.encryptionAESPwd(
						changePwdPojo.getUserId(), userNewPwd);

				boolean updateStatus = iUserInfoMapper.updateUserNewPwdById(
						changePwdPojo.getUserId(), enNewPwd);

				if(updateStatus) {

					userPwdChangeMsgDto.setStatus(true);
					userPwdChangeMsgDto.setMsg("");

				} else {

					userPwdChangeMsgDto.setStatus(false);
					userPwdChangeMsgDto.setMsg("您的舊密碼不正確，請重新輸入，或請客服協助處理。");

				}

			} else {

				userPwdChangeMsgDto.setStatus(false);
				userPwdChangeMsgDto.setMsg("您的舊密碼不正確，請重新輸入。");
			}
		}

		return userPwdChangeMsgDto;
	}

	/**
	 * 修改使用者基本資料，只能修改 名稱、Email
	 * 如果 Email 已經綁定，Email也不能改
	 * @param modifyPojo
	 * @return
	 */
	public InfoModifyMsgDto modifyByPojo(ModifyPojo modifyPojo) {

		InfoModifyMsgDto userInfoModifyMsgDto = new InfoModifyMsgDto();

		int userId = modifyPojo.getUserId();
		String userName = modifyPojo.getUserName();
		String userEmail = modifyPojo.getUserEmail();

		MailDataDto mailDataDto = iUserInfoMapper.selectEmailDataById(userId);
		if(null != mailDataDto && !mailDataDto.getUserEmail().equals(userEmail)) {

			iUserVerifyMapper.updateUnUsedByUserId(userId);
		}

		//根據 User ID 更新使用者基本資料
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
			userInfoModifyMsgDto.setMsg("您的基本資料沒有更新成功，已將您的問題提報，請稍後再試試看。");

		}

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
	public DeleteMsgDto deleteUserById(int userId) throws Exception {

		DeleteMsgDto userDeleteMsgDto = new DeleteMsgDto();

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
	 * 使用者綁定Email，寄發認證碼的Email
	 * @param bindEmailPojo
	 * @return
	 */
	public BindEmailMsgDto bindUserMailByPojo(BindEmailPojo bindEmailPojo) {

		BindEmailMsgDto BindEmailMsgDto = new BindEmailMsgDto();

		int userId = bindEmailPojo.getUserId();
		String userAcc = bindEmailPojo.getUserAcc();
		String userName = bindEmailPojo.getUserName();
		String userMail = bindEmailPojo.getUserEmail();
		String verifyCode = generatorCommon.getVerifyCode();

		if(null == userMail || userMail.equals("") || userMail.isEmpty()) {

			BindEmailMsgDto.setStatus(false);
			BindEmailMsgDto.setMsg("請輸入電子信箱(Email)。");
			return BindEmailMsgDto;
		}

		//先進行Email正規化驗證
		if(!checkCommon.checkMail(userMail)) {

			BindEmailMsgDto.setStatus(false);
			BindEmailMsgDto.setMsg("電子信箱(Email)格式不正確。");
			return BindEmailMsgDto;
		}

		//檢查有無重複的Email已經被綁定
		int selectCount = iUserInfoMapper.selectExistBindMailCount(userMail);
		if(selectCount != 0) {

			//已有相同Email被綁定
			BindEmailMsgDto.setStatus(false);
			BindEmailMsgDto.setMsg("這個電子信箱(Email)已綁定，請更換其他電子信箱(Email)。");
			return BindEmailMsgDto;
		}

		//如果使用者Email正規化通過，就更新 user_info.email 這個欄位，
		//讓使用者去驗證的時候，系統找得到Email
		boolean updateMailStatus = iUserInfoMapper.updateMailByUserId(userId, userMail);

		if(!updateMailStatus) {

			//Email更新失敗
			BindEmailMsgDto.setStatus(false);
			BindEmailMsgDto.setMsg("目前無法寄發電子信箱(Email)的認證碼，已將您的問題提報，請稍後再試。");
			return BindEmailMsgDto;
		}

		//存入user_verify，給使用者來驗證
		boolean insertStatus = iUserVerifyMapper.insertMailVerifyCodeByUserId(
				userId, VerifyTypeEnum.EMAIL.getType(), verifyCode);

		if(!insertStatus) {

			//認證碼insert失敗
			BindEmailMsgDto.setStatus(false);
			BindEmailMsgDto.setMsg("目前無法寄發電子信箱(Email)的認證碼，已將您的問題提報，請稍後再試。");
			return BindEmailMsgDto;
		}

		//使用發送公用類，呼叫發送電子信箱認證碼的方法
		boolean sendMailStatus = sendMailCommon.sendVerifyCodeMail(
				userAcc, userName, userMail, verifyCode);

		if(!sendMailStatus) {

			//Email發送失敗
			BindEmailMsgDto.setStatus(false);
			BindEmailMsgDto.setMsg("目前無法寄發電子信箱(Email)的認證碼，已將您的問題提報，請稍後再試。");
			return BindEmailMsgDto;
		}

		BindEmailMsgDto.setStatus(true);
		BindEmailMsgDto.setMsg("");
		BindEmailMsgDto.setUserEmail(userMail);

		return BindEmailMsgDto;
	}
}
