package net.spark9092.MySimpleBook.pojo.user;

public class UserBindMailPojo {
	
	private int userId;
	
	private String userAccount; //寄發Email需要使用者帳號
	
	private String userName; //寄發Email需要使用者名稱
	
	private String userEmail;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
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

}
