package net.spark9092.MySimpleBook.dto.items.accountType;

import net.spark9092.MySimpleBook.dto.MsgDto;

public class OneMsgDto extends MsgDto {
	
	private OneDto itemAccountTypeOneDto;

	public OneDto getItemAccountTypeOneDto() {
		return itemAccountTypeOneDto;
	}

	public void setItemAccountTypeOneDto(OneDto itemAccountTypeOneDto) {
		this.itemAccountTypeOneDto = itemAccountTypeOneDto;
	}

}
