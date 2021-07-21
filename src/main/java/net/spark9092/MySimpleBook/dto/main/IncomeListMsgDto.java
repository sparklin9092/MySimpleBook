package net.spark9092.MySimpleBook.dto.main;

import java.util.List;

public class IncomeListMsgDto {

	private List<IncomeListDto> listDtos;
	
	private boolean status;
	
	private String msg;

	public List<IncomeListDto> getListDtos() {
		return listDtos;
	}

	public void setListDtos(List<IncomeListDto> listDtos) {
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
