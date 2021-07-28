package net.spark9092.MySimpleBook.dto.items.accountType;

import net.spark9092.MySimpleBook.dto.BaseMsgDto;

public class OneMsgDto extends BaseMsgDto {

	private String typeName;

	private boolean typeDefault;

	private boolean typeActive;

	private String createDateTime;

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public boolean isTypeDefault() {
		return typeDefault;
	}

	public void setTypeDefault(boolean typeDefault) {
		this.typeDefault = typeDefault;
	}

	public boolean isTypeActive() {
		return typeActive;
	}

	public void setTypeActive(boolean typeActive) {
		this.typeActive = typeActive;
	}

	public String getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(String createDateTime) {
		this.createDateTime = createDateTime;
	}

}
