package net.spark9092.MySimpleBook.dto;

public class AccountOneMsgDto {
	
	private AccountOneDto accountOneDto;
	
	private boolean status;
	
	private String msg;

	public AccountOneDto getAccountOneDto() {
		return accountOneDto;
	}

	public void setAccountOneDto(AccountOneDto accountOneDto) {
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
