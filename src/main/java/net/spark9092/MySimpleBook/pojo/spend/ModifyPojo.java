package net.spark9092.MySimpleBook.pojo.spend;

import java.math.BigDecimal;

public class ModifyPojo {
	
	private int userId;
	
	private int spendId;
	
	private int spendItemId;
	
	private int accountId;

	private String spendDate;
	
	private BigDecimal amount;
	
	private String remark;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getSpendId() {
		return spendId;
	}

	public void setSpendId(int spendId) {
		this.spendId = spendId;
	}

	public int getSpendItemId() {
		return spendItemId;
	}

	public void setSpendItemId(int spendItemId) {
		this.spendItemId = spendItemId;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
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
