package net.spark9092.MySimpleBook.dto.items.accountType;

import java.time.LocalDateTime;

public class OneDto {

	private String typeName;

	private boolean typeDefault;

	private boolean typeActive;

	private LocalDateTime createDateTime;

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

	public LocalDateTime getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(LocalDateTime createDateTime) {
		this.createDateTime = createDateTime;
	}
}
