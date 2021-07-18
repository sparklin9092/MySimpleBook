package net.spark9092.MySimpleBook.dto.income;

import java.util.List;

public class SelectItemMsgDto {

	private List<SelectItemListDto> itemList;
	
	private boolean status;
	
	private String msg;

	public List<SelectItemListDto> getItemList() {
		return itemList;
	}

	public void setItemList(List<SelectItemListDto> itemList) {
		this.itemList = itemList;
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
