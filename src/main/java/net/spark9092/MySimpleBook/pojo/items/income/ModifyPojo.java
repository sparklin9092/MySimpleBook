package net.spark9092.MySimpleBook.pojo.items.income;

public class ModifyPojo {
	
	private int userId;

	private int itemId;
	
	private String itemName;
	
	private String itemActive;
	
	private String itemDefault;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

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

	public String getItemActive() {
		return itemActive;
	}

	public void setItemActive(String itemActive) {
		this.itemActive = itemActive;
	}

	public String getItemDefault() {
		return itemDefault;
	}

	public void setItemDefault(String itemDefault) {
		this.itemDefault = itemDefault;
	}

	@Override
	public String toString() {
		return String.format("ItemSpendModifyPojo [userId=%s, itemId=%s, itemName=%s, itemActive=%s, itemDefault=%s]",
				userId, itemId, itemName, itemActive, itemDefault);
	}
}
