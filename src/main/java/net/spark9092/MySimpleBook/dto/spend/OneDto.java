package net.spark9092.MySimpleBook.dto.spend;

import java.math.BigDecimal;

public class OneDto {

	private String spendDate;

	private int spendItemId;

	private int accountId;

	private BigDecimal amount;

	private String remark;

	public String getSpendDate() {
		return spendDate;
	}

	public void setSpendDate(String spendDate) {
		this.spendDate = spendDate;
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
