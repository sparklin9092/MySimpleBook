package net.spark9092.MySimpleBook.dto.spend;

import net.spark9092.MySimpleBook.dto.BaseMsgDto;

public class OneMsgDto extends BaseMsgDto {
	
	private OneDto oneDto;

	public OneDto getOneDto() {
		return oneDto;
	}

	public void setOneDto(OneDto oneDto) {
		this.oneDto = oneDto;
	}

}
