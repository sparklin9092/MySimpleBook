package net.spark9092.MySimpleBook.dto.main;

import java.util.List;

import net.spark9092.MySimpleBook.dto.BaseMsgDto;

public class AccountListMsgDto extends BaseMsgDto {

	private List<AccountListDto> listDtos;

	public List<AccountListDto> getListDtos() {
		return listDtos;
	}

	public void setListDtos(List<AccountListDto> listDtos) {
		this.listDtos = listDtos;
	}
}
