package net.spark9092.MySimpleBook.dto.main;

import java.util.List;

import net.spark9092.MySimpleBook.dto.BaseMsgDto;

public class IncomeRecMsgDto extends BaseMsgDto {

	private List<IncomeRecordDto> listDtos;

	public List<IncomeRecordDto> getListDtos() {
		return listDtos;
	}

	public void setListDtos(List<IncomeRecordDto> listDtos) {
		this.listDtos = listDtos;
	}
}
