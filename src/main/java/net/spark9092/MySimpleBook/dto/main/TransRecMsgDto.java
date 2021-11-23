package net.spark9092.MySimpleBook.dto.main;

import java.util.List;

import net.spark9092.MySimpleBook.dto.BaseMsgDto;

public class TransRecMsgDto extends BaseMsgDto {

	private List<TransRecordDto> listDtos;

	public List<TransRecordDto> getListDtos() {
		return listDtos;
	}

	public void setListDtos(List<TransRecordDto> listDtos) {
		this.listDtos = listDtos;
	}

}
