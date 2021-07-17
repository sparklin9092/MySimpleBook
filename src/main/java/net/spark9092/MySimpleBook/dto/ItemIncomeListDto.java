package net.spark9092.MySimpleBook.dto;

public class ItemIncomeListDto {

	private int itemId;
	
	private String itemName;
	
	private boolean itemDefault;
	
	private boolean itemActive;

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

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

}
