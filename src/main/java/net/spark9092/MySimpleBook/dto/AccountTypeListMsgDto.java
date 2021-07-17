package net.spark9092.MySimpleBook.dto;

import java.util.List;

public class AccountTypeListMsgDto {

	private List<AccountTypeListDto> accountTypeListDto;
	
	private boolean status;
	
	private String msg;

	public List<AccountTypeListDto> getAccountTypeListDto() {
		return accountTypeListDto;
	}

	public void setAccountTypeListDto(List<AccountTypeListDto> accountTypeListDto) {
		this.accountTypeListDto = accountTypeListDto;
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
