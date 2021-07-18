package net.spark9092.MySimpleBook.pojo.income;

import java.math.BigDecimal;

public class CreatePojo {
	
	private int userId;
	
	private int incomeItemId;
	
	private int accountItemId;

	private String incomeDate;
	
	private BigDecimal amount;
	
	private String remark;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getIncomeItemId() {
		return incomeItemId;
	}

	public void setIncomeItemId(int incomeItemId) {
		this.incomeItemId = incomeItemId;
	}

	public int getAccountItemId() {
		return accountItemId;
	}

	public void setAccountItemId(int accountItemId) {
		this.accountItemId = accountItemId;
	}

	public String getIncomeDate() {
		return incomeDate;
	}

	public void setIncomeDate(String incomeDate) {
		this.incomeDate = incomeDate;
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
