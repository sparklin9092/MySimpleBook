package net.spark9092.MySimpleBook.dto;

public class AccountListDto {

	private int accountId;
	
	private String typeName;
	
	private String accountName;
	
	private boolean itemActive;

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public boolean isItemActive() {
		return itemActive;
	}

	public void setItemActive(boolean itemActive) {
		this.itemActive = itemActive;
	}
}
