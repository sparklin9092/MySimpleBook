package net.spark9092.MySimpleBook.dto.income;

import java.math.BigDecimal;

public class RecListDto {
	
	private int incomeId;
	
	private String incomeDate;
	
	private String incomeItemName;
	
	private BigDecimal amount;

	public int getIncomeId() {
		return incomeId;
	}

	public void setIncomeId(int incomeId) {
		this.incomeId = incomeId;
	}

	public String getIncomeDate() {
		return incomeDate;
	}

	public void setIncomeDate(String incomeDate) {
		this.incomeDate = incomeDate;
	}

	public String getIncomeItemName() {
		return incomeItemName;
	}

	public void setIncomeItemName(String incomeItemName) {
		this.incomeItemName = incomeItemName;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

}
