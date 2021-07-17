package net.spark9092.MySimpleBook.dto.spend;

import java.util.List;

public class SelectItemMsgDto {

	private List<SelectItemListDto> spendItemListDto;
	
	private boolean status;
	
	private String msg;

	public List<SelectItemListDto> getSpendItemListDto() {
		return spendItemListDto;
	}

	public void setSpendItemListDto(List<SelectItemListDto> spendItemListDto) {
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
