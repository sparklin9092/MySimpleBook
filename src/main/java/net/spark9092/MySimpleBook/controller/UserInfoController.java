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
import net.spark9092.MySimpleBook.dto.user.UserInfoMsgDto;
import net.spark9092.MySimpleBook.dto.user.UserPwdChangeMsgDto;
import net.spark9092.MySimpleBook.entity.UserInfoEntity;
import net.spark9092.MySimpleBook.enums.SessinNameEnum;
import net.spark9092.MySimpleBook.pojo.user.ChangePwdPojo;
import net.spark9092.MySimpleBook.pojo.user.UserAccCheckPojo;
import net.spark9092.MySimpleBook.pojo.user.UserBindAccPwdPojo;
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

		}

		return userBindAccPwdMsgDto;
	}

	@PostMapping("")
	public UserInfoMsgDto userInfo(HttpSession session) {

		UserInfoMsgDto userInfoMsgDto = new UserInfoMsgDto();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if(null == userInfoEntity) {

			userInfoMsgDto.setStatus(false);
			userInfoMsgDto.setMsg("使用者未登入");

		} else {

			userInfoMsgDto = userInfoService.getUserInfoById(userInfoEntity);
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
		}

		return userPwdChangeMsgDto;
	}

	//    /user/info/change      修改基本資料
	//    /user/info/delete      刪除帳號

	//    /user/info/bind/email  綁定 e-mail
	//    /user/info/bind/phone  綁定手機號碼

}
