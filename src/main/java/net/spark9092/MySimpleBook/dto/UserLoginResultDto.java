package net.spark9092.MySimpleBook.dto;

import net.spark9092.MySimpleBook.entity.UserInfoEntity;

public class UserLoginResultDto {
	
	private UserInfoEntity userInfoEntity;
	
	private boolean status;
	
	private String msg;

	public UserInfoEntity getUserInfoEntity() {
		return userInfoEntity;
	}

	public void setUserInfoEntity(UserInfoEntity userInfoEntity) {
		this.userInfoEntity = userInfoEntity;
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

	@Override
	public String toString() {
		return String.format("LoginResultDto [status=%s, msg=%s]", status, msg);
	}
}
