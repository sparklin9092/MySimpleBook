package net.spark9092.MySimpleBook.service;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.spark9092.MySimpleBook.dto.UserLoginResultDto;
import net.spark9092.MySimpleBook.entity.UserInfoEntity;
import net.spark9092.MySimpleBook.mapper.IUserInfoMapper;
import net.spark9092.MySimpleBook.pojo.UserLoginPojo;

@Service
public class UserLoginService {

	private static final Logger logger = LoggerFactory.getLogger(UserLoginService.class);

	@Autowired
	private IUserInfoMapper iUserInfoMapper;

	public UserLoginResultDto userLogin(UserLoginPojo userLoginPojo) {

		UserLoginResultDto loginResultDto = new UserLoginResultDto();

		String userName = userLoginPojo.getUserName();
		UserInfoEntity userInfoEntity = new UserInfoEntity();
		userInfoEntity = iUserInfoMapper.selectByUserName(userName);

		if(userInfoEntity == null) {

			loginResultDto.setStatus(false);
			loginResultDto.setMsg("帳號或密碼錯誤");

		} else {

			logger.debug(userInfoEntity.toString());

			String inputPwd = userLoginPojo.getUserPwd();
			String dataPwd = userInfoEntity.getUserPwd();

			boolean isActive = userInfoEntity.isActive();
			boolean isDelete = userInfoEntity.isDelete();

			if(!isActive) {

				loginResultDto.setStatus(false);
				loginResultDto.setMsg("帳號已停用");

			} else if (isDelete) {

				loginResultDto.setStatus(false);
				loginResultDto.setMsg("帳號已刪除");

			} else if(!inputPwd.equals(dataPwd)) {

				loginResultDto.setStatus(false);
				loginResultDto.setMsg("帳號或密碼錯誤");

			} else {

				loginResultDto.setStatus(true);
				loginResultDto.setUserInfoEntity(userInfoEntity);
				
				//更新最後登入時間，如果發生錯誤也沒關係
				try {
					iUserInfoMapper.updateById(LocalDateTime.now(), userInfoEntity.getId());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		logger.debug(loginResultDto.toString());

		return loginResultDto;
	}
}
