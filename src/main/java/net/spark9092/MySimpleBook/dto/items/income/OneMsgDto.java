package net.spark9092.MySimpleBook.dto.items.income;

import net.spark9092.MySimpleBook.dto.MsgDto;

public class OneMsgDto extends MsgDto {
	
	private OneDto itemIncomeOneDto;

	public OneDto getItemIncomeOneDto() {
		return itemIncomeOneDto;
	}

	public void setItemIncomeOneDto(OneDto itemIncomeOneDto) {
		this.itemIncomeOneDto = itemIncomeOneDto;
	}

}
