package net.spark9092.MySimpleBook.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import net.spark9092.MySimpleBook.dto.user.LoginCheckMsgDto;
import net.spark9092.MySimpleBook.entity.UserInfoEntity;
import net.spark9092.MySimpleBook.enums.SessinNameEnum;

@RestController
public class UserCheckController {

	private static final Logger logger = LoggerFactory.getLogger(UserCheckController.class);

	@GetMapping("/userCheck")
	public LoginCheckMsgDto userCheck(HttpSession session) {

		LoginCheckMsgDto loginCheckMsgDto = new LoginCheckMsgDto();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if(null == userInfoEntity) {

			loginCheckMsgDto.setStatus(false);
			loginCheckMsgDto.setMsg("使用者未登入");
			loginCheckMsgDto.setUserName("");

		} else {

			logger.debug(userInfoEntity.toString());

			loginCheckMsgDto.setStatus(true);
			loginCheckMsgDto.setMsg("");
			loginCheckMsgDto.setUserName(userInfoEntity.getUserName());
		}

		return loginCheckMsgDto;
	}

}
