package net.spark9092.MySimpleBook.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.spark9092.MySimpleBook.dto.verify.MailBindMsgDto;
import net.spark9092.MySimpleBook.dto.verify.UserMailMsgDto;
import net.spark9092.MySimpleBook.enums.SessinNameEnum;
import net.spark9092.MySimpleBook.pojo.verify.BindMailPojo;
import net.spark9092.MySimpleBook.pojo.verify.MailBuAccPojo;
import net.spark9092.MySimpleBook.service.UserInfoService;
import net.spark9092.MySimpleBook.service.VerifyService;

@RequestMapping("/verify")
@RestController
public class VerifyController {

//	private static final Logger logger = LoggerFactory.getLogger(VerifyController.class);

	@Autowired
	private UserInfoService userInfoService;

	@Autowired
	private VerifyService verifyService;

	@PostMapping("/mail/buacc")
	public UserMailMsgDto getUserMailByAccount(@RequestBody MailBuAccPojo mailBuAccPojo) {

		String base64UserAccount = mailBuAccPojo.getBuAcc();

		return userInfoService.getUserMailByAccount(base64UserAccount);
	}

	@PostMapping("/mail/bindAct")
	public MailBindMsgDto mailBindAct(HttpSession session, @RequestBody BindMailPojo bindMailPojo) {

		MailBindMsgDto mailBindMsgDto = verifyService.bindUserMailByPojo(bindMailPojo);

		if(mailBindMsgDto.isStatus()) {

			session.setAttribute(SessinNameEnum.USER_MAIL.getName(), mailBindMsgDto.getUserMail());
		}

		return mailBindMsgDto;
	}

	@PostMapping("/mail/resend")
	public UserMailMsgDto resSendVerifyCodeMailByAccount(@RequestBody MailBuAccPojo mailBuAccPojo) {

		String base64UserAccount = mailBuAccPojo.getBuAcc();

		return verifyService.resSendVerifyCodeMailByAccount(base64UserAccount);
	}

}
