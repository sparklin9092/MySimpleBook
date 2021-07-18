package net.spark9092.MySimpleBook.pojo.account;

import java.math.BigDecimal;

public class CreatePojo {

	private int userId;
	
	private int accountType;
	
	private String accountName;
	
	private BigDecimal initAmnt;
	
	private String accountDefault;
	
	private boolean enableLimitDate;
	
	private String limitYear;
	
	private String limitMonth;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getAccountType() {
		return accountType;
	}

	public void setAccountType(int accountType) {
		this.accountType = accountType;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public BigDecimal getInitAmnt() {
		return initAmnt;
	}

	public void setInitAmnt(BigDecimal initAmnt) {
		this.initAmnt = initAmnt;
	}

	public String getAccountDefault() {
		return accountDefault;
	}

	public void setAccountDefault(String accountDefault) {
		this.accountDefault = accountDefault;
	}

	public boolean isEnableLimitDate() {
		return enableLimitDate;
	}

	public void setEnableLimitDate(boolean enableLimitDate) {
		this.enableLimitDate = enableLimitDate;
	}

	public String getLimitYear() {
		return limitYear;
	}

	public void setLimitYear(String limitYear) {
		this.limitYear = limitYear;
	}

	public String getLimitMonth() {
		return limitMonth;
	}

	public void setLimitMonth(String limitMonth) {
		this.limitMonth = limitMonth;
	}
}
