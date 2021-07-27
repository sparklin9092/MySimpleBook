package net.spark9092.MySimpleBook.dto.main;

import java.util.List;

import net.spark9092.MySimpleBook.dto.MsgDto;

public class AccountListMsgDto extends MsgDto {

	private List<AccountListDto> listDtos;

	public List<AccountListDto> getListDtos() {
		return listDtos;
	}

	public void setListDtos(List<AccountListDto> listDtos) {
		this.listDtos = listDtos;
	}
}
