package net.spark9092.MySimpleBook.dto.account;

public class OneMsgDto {
	
	private OneDto accountOneDto;
	
	private boolean status;
	
	private String msg;

	public OneDto getAccountOneDto() {
		return accountOneDto;
	}

	public void setAccountOneDto(OneDto accountOneDto) {
		this.accountOneDto = accountOneDto;
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
