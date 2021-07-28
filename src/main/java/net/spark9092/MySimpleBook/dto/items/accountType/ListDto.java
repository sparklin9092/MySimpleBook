package net.spark9092.MySimpleBook.dto.items.accountType;

public class ListDto {

	private int typeId;
	
	private String typeName;
	
	private boolean typeActive;

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public boolean isTypeActive() {
		return typeActive;
	}

	public void setTypeActive(boolean typeActive) {
		this.typeActive = typeActive;
	}

}
