package net.spark9092.MySimpleBook.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import net.spark9092.MySimpleBook.dto.UserCheckMsgDto;
import net.spark9092.MySimpleBook.entity.UserInfoEntity;
import net.spark9092.MySimpleBook.enums.SessinNameEnum;

@RestController
public class UserCheckController {

	private static final Logger logger = LoggerFactory.getLogger(UserInfoController.class);

	@GetMapping("/userCheck")
	public UserCheckMsgDto userCheck(HttpSession session) {

		UserCheckMsgDto userCheckMsgDto = new UserCheckMsgDto();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if(null == userInfoEntity) {

			userCheckMsgDto.setStatus(false);
			userCheckMsgDto.setMsg("使用者未登入");
			userCheckMsgDto.setUserName("");

		} else {

			logger.debug(userInfoEntity.toString());

			userCheckMsgDto.setStatus(true);
			userCheckMsgDto.setMsg("");
			userCheckMsgDto.setUserName(userInfoEntity.getUserName());
		}

		return userCheckMsgDto;
	}

}
