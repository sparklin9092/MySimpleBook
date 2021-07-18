package net.spark9092.MySimpleBook.dto.items.income;

import java.time.LocalDateTime;

public class OneDto {

	private String itemName;
	
	private boolean itemDefault;
	
	private boolean itemActive;
	
	private LocalDateTime createDateTime;

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

	public LocalDateTime getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(LocalDateTime createDateTime) {
		this.createDateTime = createDateTime;
	}
}
