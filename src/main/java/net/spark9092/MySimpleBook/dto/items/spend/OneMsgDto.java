package net.spark9092.MySimpleBook.dto.items.spend;

import net.spark9092.MySimpleBook.dto.BaseMsgDto;

public class OneMsgDto extends BaseMsgDto {
	
	private OneDto itemSpendOneDto;

	public OneDto getItemSpendOneDto() {
		return itemSpendOneDto;
	}

	public void setItemSpendOneDto(OneDto itemSpendOneDto) {
		this.itemSpendOneDto = itemSpendOneDto;
	}

}
