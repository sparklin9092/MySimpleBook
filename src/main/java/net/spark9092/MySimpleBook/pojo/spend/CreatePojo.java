package net.spark9092.MySimpleBook.pojo.spend;

import java.math.BigDecimal;

public class CreatePojo {
	
	private int userId;
	
	private int spendItemId;
	
	private int accountItemId;

	private String spendDate;
	
	private BigDecimal amount;
	
	private String remark;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getSpendItemId() {
		return spendItemId;
	}

	public void setSpendItemId(int spendItemId) {
		this.spendItemId = spendItemId;
	}

	public int getAccountItemId() {
		return accountItemId;
	}

	public void setAccountItemId(int accountItemId) {
		this.accountItemId = accountItemId;
	}

	public String getSpendDate() {
		return spendDate;
	}

	public void setSpendDate(String spendDate) {
		this.spendDate = spendDate;
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
