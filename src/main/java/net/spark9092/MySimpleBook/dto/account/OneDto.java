package net.spark9092.MySimpleBook.dto.account;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OneDto {

	private String accountTypeName;

	private String accountName;

	private BigDecimal initAmnt;

	private BigDecimal accountAmnt;

	private boolean accountDefault;

	private boolean accountActive;

	private boolean enableLimitDate;

	private String limitYear;

	private String limitMonth;

	private LocalDateTime createDateTime;

	public String getAccountTypeName() {
		return accountTypeName;
	}

	public void setAccountTypeName(String accountTypeName) {
		this.accountTypeName = accountTypeName;
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

	public BigDecimal getAccountAmnt() {
		return accountAmnt;
	}

	public void setAccountAmnt(BigDecimal accountAmnt) {
		this.accountAmnt = accountAmnt;
	}

	public boolean isAccountDefault() {
		return accountDefault;
	}

	public void setAccountDefault(boolean accountDefault) {
		this.accountDefault = accountDefault;
	}

	public boolean isAccountActive() {
		return accountActive;
	}

	public void setAccountActive(boolean accountActive) {
		this.accountActive = accountActive;
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

	public LocalDateTime getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(LocalDateTime createDateTime) {
		this.createDateTime = createDateTime;
	}
}
