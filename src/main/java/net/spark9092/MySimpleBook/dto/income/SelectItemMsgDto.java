package net.spark9092.MySimpleBook.dto.income;

import java.util.List;

import net.spark9092.MySimpleBook.dto.BaseMsgDto;

public class SelectItemMsgDto extends BaseMsgDto {

	private List<SelectItemListDto> itemList;

	public List<SelectItemListDto> getItemList() {
		return itemList;
	}

	public void setItemList(List<SelectItemListDto> itemList) {
		this.itemList = itemList;
	}
}
