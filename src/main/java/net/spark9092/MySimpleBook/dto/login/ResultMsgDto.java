package net.spark9092.MySimpleBook.dto.login;

import net.spark9092.MySimpleBook.dto.BaseMsgDto;
import net.spark9092.MySimpleBook.entity.UserInfoEntity;

public class ResultMsgDto extends BaseMsgDto {
	
	private UserInfoEntity userInfoEntity;

	public UserInfoEntity getUserInfoEntity() {
		return userInfoEntity;
	}

	public void setUserInfoEntity(UserInfoEntity userInfoEntity) {
		this.userInfoEntity = userInfoEntity;
	}
}
