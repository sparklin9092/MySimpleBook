package net.spark9092.MySimpleBook.dto.spend;

import java.math.BigDecimal;

public class RecListDto {
	
	private int spendId;
	
	private String spendDate;
	
	private String spendItmeName;
	
	private BigDecimal amount;

	public int getSpendId() {
		return spendId;
	}

	public void setSpendId(int spendId) {
		this.spendId = spendId;
	}

	public String getSpendDate() {
		return spendDate;
	}

	public void setSpendDate(String spendDate) {
		this.spendDate = spendDate;
	}

	public String getSpendItmeName() {
		return spendItmeName;
	}

	public void setSpendItmeName(String spendItmeName) {
		this.spendItmeName = spendItmeName;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

}
