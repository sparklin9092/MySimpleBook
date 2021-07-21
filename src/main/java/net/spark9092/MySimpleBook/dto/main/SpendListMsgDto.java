package net.spark9092.MySimpleBook.dto.main;

import java.util.List;

public class SpendListMsgDto {

	private List<SpendListDto> listDtos;
	
	private boolean status;
	
	private String msg;

	public List<SpendListDto> getListDtos() {
		return listDtos;
	}

	public void setListDtos(List<SpendListDto> listDtos) {
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
