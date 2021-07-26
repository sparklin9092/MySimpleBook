package net.spark9092.MySimpleBook.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.spark9092.MySimpleBook.dto.user.UserAccCheckMsgDto;
import net.spark9092.MySimpleBook.dto.user.UserBindAccPwdMsgDto;
import net.spark9092.MySimpleBook.dto.user.UserBindMailMsgDto;
import net.spark9092.MySimpleBook.dto.user.UserDeleteMsgDto;
import net.spark9092.MySimpleBook.dto.user.UserInfoModifyMsgDto;
import net.spark9092.MySimpleBook.dto.user.UserInfoMsgDto;
import net.spark9092.MySimpleBook.dto.user.UserPwdChangeMsgDto;
import net.spark9092.MySimpleBook.entity.UserInfoEntity;
import net.spark9092.MySimpleBook.enums.SessinNameEnum;
import net.spark9092.MySimpleBook.pojo.user.ChangePwdPojo;
import net.spark9092.MySimpleBook.pojo.user.ModifyPojo;
import net.spark9092.MySimpleBook.pojo.user.UserAccCheckPojo;
import net.spark9092.MySimpleBook.pojo.user.UserBindAccPwdPojo;
import net.spark9092.MySimpleBook.pojo.user.UserBindMailPojo;
import net.spark9092.MySimpleBook.service.UserInfoService;

@RequestMapping("/user/info")
@RestController
public class UserInfoController {

	private static final Logger logger = LoggerFactory.getLogger(UserInfoController.class);

	@Autowired
	private UserInfoService userInfoService;

	@PostMapping("/guest/datacount")
	public int getUserById(HttpSession session) {

		logger.debug("取得訪客目前建立的資料數量");

		Integer guestDataCount = (Integer) session.getAttribute(SessinNameEnum.GUEST_DATA_COUNT.getName());

		if(null == guestDataCount) {

			return 0;

		} else {

			return guestDataCount;
		}
	}

	@PostMapping("/check/account")
	public UserAccCheckMsgDto userAccCheck(HttpSession session, @RequestBody UserAccCheckPojo userAccCheckPojo) {

		UserAccCheckMsgDto userAccCheckMsgDto = new UserAccCheckMsgDto();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if (null == userInfoEntity) {

			userAccCheckMsgDto.setStatus(false);
			userAccCheckMsgDto.setMsg("使用者未登入");

		} else {

			userAccCheckPojo.setUserId(userInfoEntity.getId());

			userAccCheckMsgDto = userInfoService.checkUserAccByPojo(userAccCheckPojo);
		}

		return userAccCheckMsgDto;
	}

	@PostMapping("/bind/accpwd")
	public UserBindAccPwdMsgDto userBindAccPwd(HttpSession session, @RequestBody UserBindAccPwdPojo userBindAccPwdPojo) {

		UserBindAccPwdMsgDto userBindAccPwdMsgDto = new UserBindAccPwdMsgDto();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if (null == userInfoEntity) {

			userBindAccPwdMsgDto.setStatus(false);
			userBindAccPwdMsgDto.setMsg("使用者未登入");

		} else {

			userBindAccPwdPojo.setUserId(userInfoEntity.getId());

			userBindAccPwdMsgDto = userInfoService.bindUserByAccPwdPojo(userBindAccPwdPojo);
			
			//把session註銷掉，強制讓使用者重新登入
			if(userBindAccPwdMsgDto.isStatus()) session.invalidate();

		}

		return userBindAccPwdMsgDto;
	}
	
	@PostMapping("/bind/email")
	public UserBindMailMsgDto userBindMail(HttpSession session, @RequestBody UserBindMailPojo userBindMailPojo) {
		
		UserBindMailMsgDto userBindMailMsgDto = new UserBindMailMsgDto();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if (null == userInfoEntity) {

			userBindMailMsgDto.setStatus(false);
			userBindMailMsgDto.setMsg("使用者未登入");

		} else {

			userBindMailPojo.setUserId(userInfoEntity.getId());
			userBindMailPojo.setUserAccount(userInfoEntity.getUserAccount());
			userBindMailPojo.setUserName(userInfoEntity.getUserName());

			userBindMailMsgDto = userInfoService.bindUserByMailPojo(userBindMailPojo);
			
			//把session註銷掉，強制讓使用者重新登入
			if(userBindMailMsgDto.isStatus()) session.invalidate();

		}
		
		return userBindMailMsgDto;
	}

	@PostMapping("")
	public UserInfoMsgDto userInfo(HttpSession session) {

		UserInfoMsgDto userInfoMsgDto = new UserInfoMsgDto();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if(null == userInfoEntity) {

			userInfoMsgDto.setStatus(false);
			userInfoMsgDto.setMsg("使用者未登入");

		} else {

			userInfoMsgDto = userInfoService.getUserInfoByEntity(userInfoEntity);
		}

		return userInfoMsgDto;
	}

	@PostMapping("/pwd/change")
	public UserPwdChangeMsgDto changePwd(HttpSession session, @RequestBody ChangePwdPojo changePwdPojo) {

		UserPwdChangeMsgDto userPwdChangeMsgDto = new UserPwdChangeMsgDto();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if (null == userInfoEntity) {

			userPwdChangeMsgDto.setStatus(false);
			userPwdChangeMsgDto.setMsg("使用者未登入");

		} else {

			changePwdPojo.setUserId(userInfoEntity.getId());

			userPwdChangeMsgDto = userInfoService.modifyPwd(changePwdPojo, userInfoEntity.getUserPwd());

			//把session註銷掉，強制讓使用者重新登入
			if(userPwdChangeMsgDto.isStatus()) session.invalidate();
		}

		return userPwdChangeMsgDto;
	}
	
	@PostMapping("/modify")
	public UserInfoModifyMsgDto modifyAct(HttpSession session, @RequestBody ModifyPojo modifyPojo) {
		
		UserInfoModifyMsgDto userInfoModifyMsgDto = new UserInfoModifyMsgDto();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if (null == userInfoEntity) {

			userInfoModifyMsgDto.setStatus(false);
			userInfoModifyMsgDto.setMsg("使用者未登入");

		} else {

			modifyPojo.setUserId(userInfoEntity.getId());
			modifyPojo.setEntity(userInfoEntity);

			userInfoModifyMsgDto = userInfoService.modifyByPojo(modifyPojo);
			
			//如果使用者基本資料更新成功之後，就更新 session 裡的資料
			if(userInfoModifyMsgDto.isStatus()) {
				
				session.removeAttribute(SessinNameEnum.USER_INFO.getName());
				session.setAttribute(SessinNameEnum.USER_INFO.getName(), userInfoModifyMsgDto.getEntity());
			}
		}
		
		return userInfoModifyMsgDto;
	}
	
	@PostMapping("/delete")
	public UserDeleteMsgDto deleteAct(HttpSession session) {
		
		UserDeleteMsgDto userDeleteMsgDto = new UserDeleteMsgDto();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if (null == userInfoEntity) {

			userDeleteMsgDto.setStatus(false);
			userDeleteMsgDto.setMsg("使用者未登入");

		} else {
			
			try {
				userDeleteMsgDto = userInfoService.deleteUserById(userInfoEntity.getId());
				
				//如果使用者資料都刪除成功，就註銷 session
				if(userDeleteMsgDto.isStatus()) {
					
					session.invalidate();
				}
			} catch(Exception ex) {
				
				ex.printStackTrace();
				
				userDeleteMsgDto.setStatus(false);
				userDeleteMsgDto.setMsg("目前無法刪除使用者所有資料，請稍後再試。");
			}
		}
		
		return userDeleteMsgDto;
	}

	//    /user/info/bind/phone  綁定手機號碼

}
