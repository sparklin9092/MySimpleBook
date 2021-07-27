package net.spark9092.MySimpleBook.dto.richCode;

import java.util.List;

import net.spark9092.MySimpleBook.dto.MsgDto;

public class ListMsgDto extends MsgDto {

	private List<ListDto> listDtos;

	public List<ListDto> getListDtos() {
		return listDtos;
	}

	public void setListDtos(List<ListDto> listDtos) {
		this.listDtos = listDtos;
	}
	
}
