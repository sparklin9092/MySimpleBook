package net.spark9092.MySimpleBook.dto.transfer;

import java.util.List;

public class SelectAccountMsgDto {

	private List<SelectAccountListDto> accountList;
	
	private boolean status;
	
	private String msg;

	public List<SelectAccountListDto> getAccountList() {
		return accountList;
	}

	public void setAccountList(List<SelectAccountListDto> accountList) {
		this.accountList = accountList;
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
