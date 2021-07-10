package net.spark9092.MySimpleBook.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.spark9092.MySimpleBook.entity.UserInfoEntity;
import net.spark9092.MySimpleBook.mapper.IUserInfoMapper;

@RequestMapping("/userinfo")
@RestController
public class UserInfoController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserInfoController.class);

	@Autowired
	private IUserInfoMapper userInfoMapper;
	
	public UserInfoController(IUserInfoMapper userInfoMapper) {
		this.userInfoMapper = userInfoMapper;
	}
	
	@GetMapping("{id}")
	public UserInfoEntity getUserById(@PathVariable int id) {
		
		logger.debug("***** User ID is %d ****", id);
		
		return userInfoMapper.selectAllById(id);
	}
	
}
