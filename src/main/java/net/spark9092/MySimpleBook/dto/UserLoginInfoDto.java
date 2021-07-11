package net.spark9092.MySimpleBook.dto;

public class UserLoginInfoDto {

	private int id;
	private String userPwd;
	private boolean isActive;
	private boolean isDelete;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserPwd() {
		return userPwd;
	}
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public boolean isDelete() {
		return isDelete;
	}
	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}
	@Override
	public String toString() {
		return String.format("UserLoginInfoDto [id=%s, userPwd=%s, isActive=%s, isDelete=%s]", id, userPwd, isActive,
				isDelete);
	}
}
