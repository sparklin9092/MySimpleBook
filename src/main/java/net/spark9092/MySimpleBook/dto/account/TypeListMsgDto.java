package net.spark9092.MySimpleBook.dto.account;

import java.util.List;

public class TypeListMsgDto {

	private List<TypeListDto> accountTypeListDto;
	
	private boolean status;
	
	private String msg;

	public List<TypeListDto> getAccountTypeListDto() {
		return accountTypeListDto;
	}

	public void setAccountTypeListDto(List<TypeListDto> accountTypeListDto) {
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
