package net.spark9092.MySimpleBook.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import net.spark9092.MySimpleBook.dto.LoginResultDto;
import net.spark9092.MySimpleBook.pojo.UserLoginPojo;
import net.spark9092.MySimpleBook.service.UserLoginService;

@RestController
public class LoginController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserLoginService.class);

	@Autowired
	UserLoginService tUserLoginService;

	@PostMapping("/login")
    @ResponseBody
	public LoginResultDto login(HttpSession session, @RequestBody UserLoginPojo userLoginPojo) {
		
		logger.debug(userLoginPojo.toString());

		return tUserLoginService.userLogin(userLoginPojo);
	}
}
