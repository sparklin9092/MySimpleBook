package net.spark9092.MySimpleBook.dto.account;

import net.spark9092.MySimpleBook.dto.BaseMsgDto;

public class OneMsgDto extends BaseMsgDto {
	
	private OneDto accountOneDto;

	public OneDto getAccountOneDto() {
		return accountOneDto;
	}

	public void setAccountOneDto(OneDto accountOneDto) {
		this.accountOneDto = accountOneDto;
	}

}
