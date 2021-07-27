package net.spark9092.MySimpleBook.dto.items.income;

import net.spark9092.MySimpleBook.dto.BaseMsgDto;

public class OneMsgDto extends BaseMsgDto {
	
	private OneDto itemIncomeOneDto;

	public OneDto getItemIncomeOneDto() {
		return itemIncomeOneDto;
	}

	public void setItemIncomeOneDto(OneDto itemIncomeOneDto) {
		this.itemIncomeOneDto = itemIncomeOneDto;
	}

}
