package net.spark9092.MySimpleBook.dto.user;

import net.spark9092.MySimpleBook.dto.BaseMsgDto;
import net.spark9092.MySimpleBook.entity.UserInfoEntity;

public class InfoModifyMsgDto extends BaseMsgDto {
	
	private UserInfoEntity entity;

	public UserInfoEntity getEntity() {
		return entity;
	}

	public void setEntity(UserInfoEntity entity) {
		this.entity = entity;
	}

}
