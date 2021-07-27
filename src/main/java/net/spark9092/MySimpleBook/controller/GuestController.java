package net.spark9092.MySimpleBook.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.spark9092.MySimpleBook.dto.guest.UserAccCheckMsgDto;
import net.spark9092.MySimpleBook.dto.guest.UserBindAccPwdMsgDto;
import net.spark9092.MySimpleBook.dto.guest.UserBindMailMsgDto;
import net.spark9092.MySimpleBook.entity.UserInfoEntity;
import net.spark9092.MySimpleBook.enums.SessinNameEnum;
import net.spark9092.MySimpleBook.pojo.guest.UserAccCheckPojo;
import net.spark9092.MySimpleBook.pojo.guest.UserBindAccPwdPojo;
import net.spark9092.MySimpleBook.pojo.guest.UserBindMailPojo;
import net.spark9092.MySimpleBook.service.GuestService;

@RequestMapping("/guest")
@RestController
public class GuestController {

//	private static final Logger logger = LoggerFactory.getLogger(GuestController.class);

	@Autowired
	private GuestService guestService;

	@PostMapping("/datacount")
	public int getDataCount(HttpSession session) {

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

			userAccCheckMsgDto = guestService.checkUserAccByPojo(userAccCheckPojo);
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

			userBindAccPwdMsgDto = guestService.bindUserByAccPwdPojo(userBindAccPwdPojo);

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

			userBindMailMsgDto = guestService.bindUserByMailPojo(userBindMailPojo);

			//把session註銷掉，強制讓使用者重新登入
			if(userBindMailMsgDto.isStatus()) session.invalidate();

		}

		return userBindMailMsgDto;
	}

}
