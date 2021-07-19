package net.spark9092.MySimpleBook.dto.richCode;

import java.util.List;

public class ListMsgDto {

	private List<ListDto> listDtos;
	
	private boolean status;
	
	private String msg;

	public List<ListDto> getListDtos() {
		return listDtos;
	}

	public void setListDtos(List<ListDto> listDtos) {
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
