package net.spark9092.MySimpleBook.dto;

import java.util.List;

public class SpendItemMsgDto {

	private List<SpendItemListDto> spendItemListDto;
	
	private boolean status;
	
	private String msg;

	public List<SpendItemListDto> getSpendItemListDto() {
		return spendItemListDto;
	}

	public void setSpendItemListDto(List<SpendItemListDto> spendItemListDto) {
		this.spendItemListDto = spendItemListDto;
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

	@Override
	public String toString() {
		return String.format("SpendItemMsgDto [status=%s, msg=%s]", status, msg);
	}
}
