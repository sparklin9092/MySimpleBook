package net.spark9092.MySimpleBook.dto.user;

import net.spark9092.MySimpleBook.dto.MsgDto;
import net.spark9092.MySimpleBook.entity.UserInfoEntity;

public class UserInfoModifyMsgDto extends MsgDto {
	
	private UserInfoEntity entity;

	public UserInfoEntity getEntity() {
		return entity;
	}

	public void setEntity(UserInfoEntity entity) {
		this.entity = entity;
	}

}
