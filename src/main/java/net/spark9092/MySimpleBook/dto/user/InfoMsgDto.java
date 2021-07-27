package net.spark9092.MySimpleBook.dto.user;

import net.spark9092.MySimpleBook.dto.BaseMsgDto;

public class InfoMsgDto extends BaseMsgDto {

	private InfoDto dto;

	public InfoDto getDto() {
		return dto;
	}

	public void setDto(InfoDto dto) {
		this.dto = dto;
	}

}
