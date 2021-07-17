package net.spark9092.MySimpleBook.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import net.spark9092.MySimpleBook.dto.user.LoginMsgDto;
import net.spark9092.MySimpleBook.dto.user.LoginResultDto;
import net.spark9092.MySimpleBook.entity.UserInfoEntity;
import net.spark9092.MySimpleBook.enums.SessinNameEnum;
import net.spark9092.MySimpleBook.pojo.user.LoginPojo;
import net.spark9092.MySimpleBook.service.UserLoginService;

@RestController
public class LoginController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserLoginService.class);

	@Autowired
	private UserLoginService userLoginService;

	@PostMapping("/login")
    @ResponseBody
	public LoginMsgDto login(HttpSession session, @RequestBody LoginPojo loginPojo) {
		
		logger.debug(loginPojo.toString());
		
		LoginResultDto loginResultDto = userLoginService.userLogin(loginPojo);
		
		UserInfoEntity userInfoEntity = loginResultDto.getUserInfoEntity();
		boolean loginStatus = loginResultDto.isStatus();
		String loginMsg = loginResultDto.getMsg();
		
		if(loginStatus) {
			//在 Session 寫入 User 基本資料，後續的功能基本上都要 User ID 去查資料
			session.setAttribute(SessinNameEnum.USER_INFO.getName(), userInfoEntity);
		}
		
		LoginMsgDto loginMsgDto = new LoginMsgDto();
		loginMsgDto.setStatus(loginStatus);
		loginMsgDto.setMsg(loginMsg);

		return loginMsgDto;
	}
}
