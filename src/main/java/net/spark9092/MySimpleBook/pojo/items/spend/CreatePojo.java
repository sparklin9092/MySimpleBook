package net.spark9092.MySimpleBook.pojo.items.spend;

public class CreatePojo {
	
	private int userId;

	private String itemName;
	
	private String itemDefault;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemDefault() {
		return itemDefault;
	}

	public void setItemDefault(String itemDefault) {
		this.itemDefault = itemDefault;
	}

	@Override
	public String toString() {
		return String.format("ItemSpendCreatePojo [userId=%s, itemName=%s, itemDefault=%s]", userId, itemName,
				itemDefault);
	}
}
