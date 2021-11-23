package net.spark9092.MySimpleBook.dto.main;

import java.util.List;

import net.spark9092.MySimpleBook.dto.BaseMsgDto;

public class AccRecMsgDto extends BaseMsgDto {

	private List<AccRecordDto> listDtos;

	public List<AccRecordDto> getListDtos() {
		return listDtos;
	}

	public void setListDtos(List<AccRecordDto> listDtos) {
		this.listDtos = listDtos;
	}
}
