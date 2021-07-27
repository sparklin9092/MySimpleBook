package net.spark9092.MySimpleBook.dto.main;

import java.util.List;

import net.spark9092.MySimpleBook.dto.BaseMsgDto;

public class IncomeListMsgDto extends BaseMsgDto {

	private List<IncomeListDto> listDtos;

	public List<IncomeListDto> getListDtos() {
		return listDtos;
	}

	public void setListDtos(List<IncomeListDto> listDtos) {
		this.listDtos = listDtos;
	}
}
