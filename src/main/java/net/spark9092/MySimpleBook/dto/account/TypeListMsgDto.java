package net.spark9092.MySimpleBook.dto.account;

import java.util.List;

import net.spark9092.MySimpleBook.dto.MsgDto;

public class TypeListMsgDto extends MsgDto {

	private List<TypeListDto> accountTypeListDto;

	public List<TypeListDto> getAccountTypeListDto() {
		return accountTypeListDto;
	}

	public void setAccountTypeListDto(List<TypeListDto> accountTypeListDto) {
		this.accountTypeListDto = accountTypeListDto;
	}
}
