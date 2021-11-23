package net.spark9092.MySimpleBook.dto.main;

import java.util.List;

import net.spark9092.MySimpleBook.dto.BaseMsgDto;

public class SpendRecMsgDto extends BaseMsgDto {

	private List<SpendRecordDto> listDtos;

	public List<SpendRecordDto> getListDtos() {
		return listDtos;
	}

	public void setListDtos(List<SpendRecordDto> listDtos) {
		this.listDtos = listDtos;
	}
}
