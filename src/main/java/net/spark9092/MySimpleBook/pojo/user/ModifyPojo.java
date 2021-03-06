package net.spark9092.MySimpleBook.pojo.user;

import net.spark9092.MySimpleBook.entity.UserInfoEntity;

public class ModifyPojo {
	
	private int userId;
	
	private String userName;
	
	private String userEmail;
	
	private UserInfoEntity entity;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public UserInfoEntity getEntity() {
		return entity;
	}

	public void setEntity(UserInfoEntity entity) {
		this.entity = entity;
	}

}
