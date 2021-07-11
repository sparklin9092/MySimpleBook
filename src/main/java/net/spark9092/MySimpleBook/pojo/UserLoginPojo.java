package net.spark9092.MySimpleBook.pojo;

public class UserLoginPojo {

	private String userName;
	private String userPwd;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPwd() {
		return userPwd;
	}
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
	@Override
	public String toString() {
		return String.format("UserLoginPojo [userName=%s, userPwd=%s]", userName, userPwd);
	}
}
