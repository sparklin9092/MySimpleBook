package net.spark9092.MySimpleBook.dto.account;

import java.math.BigDecimal;

public class ListDto {

	private int accountId;
	
	private String accountName;
	
	private BigDecimal accountAmnt;

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public BigDecimal getAccountAmnt() {
		return accountAmnt;
	}

	public void setAccountAmnt(BigDecimal accountAmnt) {
		this.accountAmnt = accountAmnt;
	}
}
