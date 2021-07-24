package net.spark9092.MySimpleBook.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.spark9092.MySimpleBook.dto.user.UserInfoMsgDto;
import net.spark9092.MySimpleBook.entity.UserInfoEntity;
import net.spark9092.MySimpleBook.enums.SessinNameEnum;
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
	
	@PostMapping("")
	public UserInfoMsgDto userInfo(HttpSession session) {
		
		UserInfoMsgDto userInfoMsgDto = new UserInfoMsgDto();
		
		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());
		
		if(null == userInfoEntity) {

			userInfoMsgDto.setStatus(false);
			userInfoMsgDto.setMsg("使用者未登入");
			
		} else {
			
			userInfoMsgDto = userInfoService.getUserInfoById(userInfoEntity.getId());
		}
		
		return userInfoMsgDto;
	}

}
