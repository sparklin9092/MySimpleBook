package net.spark9092.MySimpleBook.pojo;

public class ItemSpendDeletePojo {
	
	private int userId;

	private int itemId;

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

	@Override
	public String toString() {
		return String.format("ItemSpendDeletePojo [userId=%s, itemId=%s]", userId, itemId);
	}

}
