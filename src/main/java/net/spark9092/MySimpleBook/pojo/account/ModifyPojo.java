package net.spark9092.MySimpleBook.pojo.account;

public class ModifyPojo {

	private int userId;

	private int accountId;
	
	private String accountName;
	
	private String accountActive;
	
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

	public String getAccountActive() {
		return accountActive;
	}

	public void setAccountActive(String accountActive) {
		this.accountActive = accountActive;
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
