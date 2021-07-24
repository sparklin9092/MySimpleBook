package net.spark9092.MySimpleBook.dto.user;

public class UserInfoDto {

	private String userName;

	private String maskPwd;

	private String userEmail;

	private String userPhone;

	private String createDate;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMaskPwd() {
		return maskPwd;
	}

	public void setMaskPwd(String maskPwd) {
		this.maskPwd = maskPwd;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

}
