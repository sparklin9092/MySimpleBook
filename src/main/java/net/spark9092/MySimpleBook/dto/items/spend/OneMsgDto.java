package net.spark9092.MySimpleBook.dto.items.spend;

import net.spark9092.MySimpleBook.dto.MsgDto;

public class OneMsgDto extends MsgDto {
	
	private OneDto itemSpendOneDto;

	public OneDto getItemSpendOneDto() {
		return itemSpendOneDto;
	}

	public void setItemSpendOneDto(OneDto itemSpendOneDto) {
		this.itemSpendOneDto = itemSpendOneDto;
	}

}
