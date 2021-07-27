package net.spark9092.MySimpleBook.dto.main;

import java.util.List;

import net.spark9092.MySimpleBook.dto.MsgDto;

public class TransferListMsgDto extends MsgDto {

	private List<TransferListDto> listDtos;

	public List<TransferListDto> getListDtos() {
		return listDtos;
	}

	public void setListDtos(List<TransferListDto> listDtos) {
		this.listDtos = listDtos;
	}

}
