package net.spark9092.MySimpleBook.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.spark9092.MySimpleBook.dto.guest.AccCheckMsgDto;
import net.spark9092.MySimpleBook.dto.guest.BindAccPwdMsgDto;
import net.spark9092.MySimpleBook.dto.guest.BindMailMsgDto;
import net.spark9092.MySimpleBook.entity.UserInfoEntity;
import net.spark9092.MySimpleBook.enums.SessinNameEnum;
import net.spark9092.MySimpleBook.pojo.guest.AccCheckPojo;
import net.spark9092.MySimpleBook.pojo.guest.BindAccPwdPojo;
import net.spark9092.MySimpleBook.pojo.guest.BindMailPojo;
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
	public AccCheckMsgDto userAccCheck(HttpSession session, @RequestBody AccCheckPojo userAccCheckPojo) {

		AccCheckMsgDto userAccCheckMsgDto = new AccCheckMsgDto();

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
	public BindAccPwdMsgDto guestBindAccPwd(HttpSession session, @RequestBody BindAccPwdPojo userBindAccPwdPojo) {

		BindAccPwdMsgDto userBindAccPwdMsgDto = new BindAccPwdMsgDto();

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
	public BindMailMsgDto guestBindMail(HttpSession session, @RequestBody BindMailPojo userBindMailPojo) {

		BindMailMsgDto userBindMailMsgDto = new BindMailMsgDto();

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
