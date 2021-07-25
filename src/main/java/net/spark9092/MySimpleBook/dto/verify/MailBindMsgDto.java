package net.spark9092.MySimpleBook.dto.verify;

public class MailBindMsgDto {
	
	private int userId;
	
	private boolean status;
	
	private String msg;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
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
