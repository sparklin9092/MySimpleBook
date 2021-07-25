package net.spark9092.MySimpleBook.dto.user;

import net.spark9092.MySimpleBook.entity.UserInfoEntity;

public class UserInfoModifyMsgDto {
	
	private UserInfoEntity entity;
	
	private boolean status;
	
	private String msg;

	public UserInfoEntity getEntity() {
		return entity;
	}

	public void setEntity(UserInfoEntity entity) {
		this.entity = entity;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
