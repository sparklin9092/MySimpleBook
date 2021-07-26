package net.spark9092.MySimpleBook.dto.richCode;

import java.io.Serializable;

public class ListDto implements Serializable {

	private static final long serialVersionUID = 2074706733273009676L;

	private int richCodeId;

	private String richCode;

	private int richCodeShowTime;

	public int getRichCodeId() {
		return richCodeId;
	}

	public void setRichCodeId(int richCodeId) {
		this.richCodeId = richCodeId;
	}

	public String getRichCode() {
		return richCode;
	}

	public void setRichCode(String richCode) {
		this.richCode = richCode;
	}

	public int getRichCodeShowTime() {
		return richCodeShowTime;
	}

	public void setRichCodeShowTime(int richCodeShowTime) {
		this.richCodeShowTime = richCodeShowTime;
	}

	@Override
	public String toString() {
		return String.format("ListDto [richCodeId=%s, richCode=%s, richCodeShowTime=%s]", richCodeId, richCode,
				richCodeShowTime);
	}

}
