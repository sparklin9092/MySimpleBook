package net.spark9092.MySimpleBook.dto.items.income;

import net.spark9092.MySimpleBook.dto.BaseMsgDto;

public class OneMsgDto extends BaseMsgDto {

	private String itemName;

	private boolean itemDefault;

	private boolean itemActive;

	private String createDateTime;

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public boolean isItemDefault() {
		return itemDefault;
	}

	public void setItemDefault(boolean itemDefault) {
		this.itemDefault = itemDefault;
	}

	public boolean isItemActive() {
		return itemActive;
	}

	public void setItemActive(boolean itemActive) {
		this.itemActive = itemActive;
	}

	public String getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(String createDateTime) {
		this.createDateTime = createDateTime;
	}

}
