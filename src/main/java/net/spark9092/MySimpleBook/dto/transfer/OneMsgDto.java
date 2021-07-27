package net.spark9092.MySimpleBook.dto.transfer;

import net.spark9092.MySimpleBook.dto.MsgDto;

public class OneMsgDto extends MsgDto {
	
	private OneDto oneDto;

	public OneDto getOneDto() {
		return oneDto;
	}

	public void setOneDto(OneDto oneDto) {
		this.oneDto = oneDto;
	}

}
