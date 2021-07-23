package net.spark9092.MySimpleBook.service;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.spark9092.MySimpleBook.common.GeneratorCommon;
import net.spark9092.MySimpleBook.common.GetCommon;
import net.spark9092.MySimpleBook.dto.user.LoginResultDto;
import net.spark9092.MySimpleBook.entity.UserInfoEntity;
import net.spark9092.MySimpleBook.enums.SeqsNameEnum;
import net.spark9092.MySimpleBook.enums.SystemEnum;
import net.spark9092.MySimpleBook.mapper.IGuestMapper;
import net.spark9092.MySimpleBook.mapper.ISeqsMapper;
import net.spark9092.MySimpleBook.mapper.IUserInfoMapper;
import net.spark9092.MySimpleBook.pojo.user.LoginPojo;

@Service
public class UserLoginService {

	private static final Logger logger = LoggerFactory.getLogger(UserLoginService.class);

	@Autowired
	private IUserInfoMapper iUserInfoMapper;
	
	@Autowired
	private ISeqsMapper iSeqsMapper;
	
	@Autowired
	private IGuestMapper iGuestMapper;
	
	@Autowired
	private GeneratorCommon generatorCommon;
	
	@Autowired
	private GetCommon getCommon;

	public LoginResultDto userLogin(LoginPojo userLoginPojo) {

		LoginResultDto loginResultDto = new LoginResultDto();
		UserInfoEntity userInfoEntity = new UserInfoEntity();

		String userName = userLoginPojo.getUserName();
		userInfoEntity = iUserInfoMapper.selectByUserName(userName);

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

	public LoginResultDto guestLogin(String ipAddress, String guestDevice) {

		LoginResultDto loginResultDto = new LoginResultDto();
		
		//計算這個 IP 已經建立多少次訪客了
		int guestLoginTimes = iGuestMapper.getLoginTimes(ipAddress);
		
		//超過 5 次就不能再用訪客登入了
		if(guestLoginTimes == 5 && !getCommon.isIntranet(ipAddress)) {
			
			loginResultDto.setStatus(false);
			loginResultDto.setMsg("今日訪客數量已達系統上限。");
			return loginResultDto;
		}
		
		//對訪客取號碼牌(訪客序號)
		int guestSeq = iSeqsMapper.getSeq(SeqsNameEnum.GUEST.getName());
		
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
}
