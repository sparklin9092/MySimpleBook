package net.spark9092.MySimpleBook.dto.income;

public class SelectItemListDto {

	private int itemId;
	
	private String itemName;

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

	@Override
	public String toString() {
		return String.format("SpendItemListDto [itemId=%s, itemName=%s]", itemId, itemName);
	}
	
	
}
