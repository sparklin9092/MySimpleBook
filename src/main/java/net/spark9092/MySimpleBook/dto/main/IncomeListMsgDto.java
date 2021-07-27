package net.spark9092.MySimpleBook.dto.main;

import java.util.List;

import net.spark9092.MySimpleBook.dto.MsgDto;

public class IncomeListMsgDto extends MsgDto {

	private List<IncomeListDto> listDtos;

	public List<IncomeListDto> getListDtos() {
		return listDtos;
	}

	public void setListDtos(List<IncomeListDto> listDtos) {
		this.listDtos = listDtos;
	}
}
