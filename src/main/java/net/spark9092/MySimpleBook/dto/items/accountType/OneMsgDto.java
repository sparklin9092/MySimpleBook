package net.spark9092.MySimpleBook.dto.items.accountType;

import net.spark9092.MySimpleBook.dto.BaseMsgDto;

public class OneMsgDto extends BaseMsgDto {
	
	private OneDto itemAccountTypeOneDto;

	public OneDto getItemAccountTypeOneDto() {
		return itemAccountTypeOneDto;
	}

	public void setItemAccountTypeOneDto(OneDto itemAccountTypeOneDto) {
		this.itemAccountTypeOneDto = itemAccountTypeOneDto;
	}

}
