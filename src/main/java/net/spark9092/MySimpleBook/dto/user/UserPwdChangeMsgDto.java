package net.spark9092.MySimpleBook.dto.user;

public class UserPwdChangeMsgDto {
	
	private boolean status;
	
	private String msg;

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
