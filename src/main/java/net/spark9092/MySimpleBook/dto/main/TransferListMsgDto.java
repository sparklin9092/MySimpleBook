package net.spark9092.MySimpleBook.dto.main;

import java.util.List;

public class TransferListMsgDto {

	private List<TransferListDto> listDtos;
	
	private boolean status;
	
	private String msg;

	public List<TransferListDto> getListDtos() {
		return listDtos;
	}

	public void setListDtos(List<TransferListDto> listDtos) {
		this.listDtos = listDtos;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
