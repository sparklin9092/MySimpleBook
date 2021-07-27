package net.spark9092.MySimpleBook.dto.transfer;

import java.util.List;

import net.spark9092.MySimpleBook.dto.MsgDto;

public class SelectAccountMsgDto extends MsgDto {

	private List<SelectAccountListDto> accountList;

	public List<SelectAccountListDto> getAccountList() {
		return accountList;
	}

	public void setAccountList(List<SelectAccountListDto> accountList) {
		this.accountList = accountList;
	}

}
