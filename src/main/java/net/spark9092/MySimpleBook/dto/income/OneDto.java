package net.spark9092.MySimpleBook.dto.income;

import java.math.BigDecimal;

public class OneDto {

	private String incomeDate;

	private int incomeItemId;

	private int accountId;

	private BigDecimal amount;

	private String remark;

	public String getIncomeDate() {
		return incomeDate;
	}

	public void setIncomeDate(String incomeDate) {
		this.incomeDate = incomeDate;
	}

	public int getIncomeItemId() {
		return incomeItemId;
	}

	public void setIncomeItemId(int incomeItemId) {
		this.incomeItemId = incomeItemId;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
