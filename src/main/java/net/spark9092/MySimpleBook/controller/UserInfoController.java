package net.spark9092.MySimpleBook.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.spark9092.MySimpleBook.enums.SessinNameEnum;

@RequestMapping("/user/info")
@RestController
public class UserInfoController {

	private static final Logger logger = LoggerFactory.getLogger(UserInfoController.class);

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

//	@GetMapping("{id}")
//	public UserInfoEntity getUserById(@PathVariable int id) {
//
//		logger.debug("***** User ID is %d ****", id);
//
//		return userInfoMapper.selectAllById(id);
//	}

}
