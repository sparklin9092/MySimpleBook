package net.spark9092.MySimpleBook.service;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.spark9092.MySimpleBook.dto.LoginResultDto;
import net.spark9092.MySimpleBook.dto.UserLoginInfoDto;
import net.spark9092.MySimpleBook.mapper.IUserInfoMapper;
import net.spark9092.MySimpleBook.pojo.UserLoginPojo;

@Service
public class UserLoginService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserLoginService.class);

	@Autowired
	IUserInfoMapper tIUserInfoMapper;

	public LoginResultDto userLogin(UserLoginPojo tUserLoginPojo) {

		LoginResultDto tLoginResultDto = new LoginResultDto(true, "");
		
		String userName = tUserLoginPojo.getUserName();
		UserLoginInfoDto tUserLoginInfoDto = new UserLoginInfoDto();
		tUserLoginInfoDto = tIUserInfoMapper.selectByUserName(userName);
		
		if(tUserLoginInfoDto == null) {
			
			tLoginResultDto.setStatus(false);
			tLoginResultDto.setMsg("帳號或密碼錯誤");
			
		} else {
			
			logger.debug(tUserLoginInfoDto.toString());
			
			String inputPwd = tUserLoginPojo.getUserPwd();
			String dataPwd = tUserLoginInfoDto.getUserPwd();
			
			boolean isActive = tUserLoginInfoDto.isActive();
			boolean isDelete = tUserLoginInfoDto.isDelete();
			
			if(!isActive) {
				tLoginResultDto.setStatus(false);
				tLoginResultDto.setMsg("帳號已停用");
			} else if (isDelete) {
				tLoginResultDto.setStatus(false);
				tLoginResultDto.setMsg("帳號已刪除");
			} else if(!inputPwd.equals(dataPwd)) {
				tLoginResultDto.setStatus(false);
				tLoginResultDto.setMsg("帳號或密碼錯誤");
			} else {
				tIUserInfoMapper.updateById(LocalDateTime.now(), tUserLoginInfoDto.getId());
			}
		}
		
		logger.debug(tLoginResultDto.toString());
		
		return tLoginResultDto;
	}
}
