package net.spark9092.MySimpleBook.dto.user;

public class LoginCheckMsgDto {

	private String userName;
	
	private boolean status;
	
	private String msg;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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
		return String.format("UserCheckMsgDto [userName=%s, status=%s, msg=%s]", userName, status, msg);
	}
}
