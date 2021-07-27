package net.spark9092.MySimpleBook.dto.richCode;

import java.util.List;

import net.spark9092.MySimpleBook.dto.BaseMsgDto;

public class ListMsgDto extends BaseMsgDto {

	private List<ListDto> listDtos;

	public List<ListDto> getListDtos() {
		return listDtos;
	}

	public void setListDtos(List<ListDto> listDtos) {
		this.listDtos = listDtos;
	}
	
}
