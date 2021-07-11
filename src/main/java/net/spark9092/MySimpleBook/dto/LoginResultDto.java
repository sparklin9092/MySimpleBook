package net.spark9092.MySimpleBook.dto;

public class LoginResultDto {
	
	private boolean status;
	private String msg;
	
	public LoginResultDto() {};

	public LoginResultDto(boolean status, String msg) {
		this.status = status;
		this.msg = msg;
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
		return String.format("LoginResult [status=%s, msg=%s]", status, msg);
	}
}
