package net.spark9092.MySimpleBook.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.spark9092.MySimpleBook.dto.verify.MailBindMsgDto;
import net.spark9092.MySimpleBook.dto.verify.UserMailMsgDto;
import net.spark9092.MySimpleBook.pojo.verify.BindMailPojo;
import net.spark9092.MySimpleBook.pojo.verify.MailBuAccPojo;
import net.spark9092.MySimpleBook.service.UserInfoService;

@RequestMapping("/verify")
@RestController
public class VerifyController {

	private static final Logger logger = LoggerFactory.getLogger(VerifyController.class);
	
	@Autowired UserInfoService userInfoService;
	
	@PostMapping("/mail/buacc")
	public UserMailMsgDto getUserMailByAccount(@RequestBody MailBuAccPojo mailBuAccPojo) {
		
		String base64UserAccount = mailBuAccPojo.getBuAcc();
		
		logger.debug("getUserMailByAccount base64UserAccount: " + base64UserAccount);
		
		return userInfoService.getUserMailByAccount(base64UserAccount);
	}
	
	@PostMapping("/mail/bindAct")
	public MailBindMsgDto mailBindAct(@RequestBody BindMailPojo bindMailPojo) {
		
		return userInfoService.bindUserMailByPojo(bindMailPojo);
	}

}
