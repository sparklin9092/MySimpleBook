package net.spark9092.MySimpleBook.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import net.spark9092.MySimpleBook.dto.UserLoginMsgDto;
import net.spark9092.MySimpleBook.dto.UserLoginResultDto;
import net.spark9092.MySimpleBook.entity.UserInfoEntity;
import net.spark9092.MySimpleBook.enums.SessinNameEnum;
import net.spark9092.MySimpleBook.pojo.UserLoginPojo;
import net.spark9092.MySimpleBook.service.UserLoginService;

@RestController
public class LoginController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserLoginService.class);

	@Autowired
	private UserLoginService userLoginService;

	@PostMapping("/login")
    @ResponseBody
	public UserLoginMsgDto login(HttpSession session, @RequestBody UserLoginPojo userLoginPojo) {
		
		logger.debug(userLoginPojo.toString());
		
		UserLoginResultDto userLoginResultDto = userLoginService.userLogin(userLoginPojo);
		
		UserInfoEntity userInfoEntity = userLoginResultDto.getUserInfoEntity();
		boolean loginStatus = userLoginResultDto.isStatus();
		String loginMsg = userLoginResultDto.getMsg();
		
		if(loginStatus) {
			//在 Session 寫入 User 基本資料，後續的功能基本上都要 User ID 去查資料
			session.setAttribute(SessinNameEnum.USER_INFO.getName(), userInfoEntity);
		}
		
		UserLoginMsgDto userLoginMsgDto = new UserLoginMsgDto();
		userLoginMsgDto.setStatus(loginStatus);
		userLoginMsgDto.setMsg(loginMsg);

		return userLoginMsgDto;
	}
}
