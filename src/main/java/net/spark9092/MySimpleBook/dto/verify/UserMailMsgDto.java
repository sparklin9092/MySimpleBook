package net.spark9092.MySimpleBook.dto.verify;

public class UserMailMsgDto {
	
	private int userId;
	
	private String userMail;
	
	private String reSendSec;
	
	private boolean used;
	
	private boolean status;
	
	private String msg;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserMail() {
		return userMail;
	}

	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}

	public String getReSendSec() {
		return reSendSec;
	}

	public void setReSendSec(String reSendSec) {
		this.reSendSec = reSendSec;
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
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
