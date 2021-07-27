package net.spark9092.MySimpleBook.dto.account;

import net.spark9092.MySimpleBook.dto.MsgDto;

public class OneMsgDto extends MsgDto {
	
	private OneDto accountOneDto;

	public OneDto getAccountOneDto() {
		return accountOneDto;
	}

	public void setAccountOneDto(OneDto accountOneDto) {
		this.accountOneDto = accountOneDto;
	}

}
