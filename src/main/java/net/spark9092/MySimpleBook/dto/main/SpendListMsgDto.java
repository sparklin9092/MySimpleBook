package net.spark9092.MySimpleBook.dto.main;

import java.util.List;

import net.spark9092.MySimpleBook.dto.BaseMsgDto;

public class SpendListMsgDto extends BaseMsgDto {

	private List<SpendListDto> listDtos;

	public List<SpendListDto> getListDtos() {
		return listDtos;
	}

	public void setListDtos(List<SpendListDto> listDtos) {
		this.listDtos = listDtos;
	}
}
