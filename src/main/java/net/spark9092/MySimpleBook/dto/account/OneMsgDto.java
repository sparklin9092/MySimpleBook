package net.spark9092.MySimpleBook.dto.account;

import java.math.BigDecimal;

import net.spark9092.MySimpleBook.dto.BaseMsgDto;

public class OneMsgDto extends BaseMsgDto {

	private String accTypeName;

	private String accName;

	private BigDecimal initAmnt;

	private BigDecimal accAmnt;

	private boolean accDefault;

	private boolean accActive;

	private boolean enableLimitDate;

	private String limitYear;

	private String limitMonth;

	private String createDateTime;

	public String getAccTypeName() {
		return accTypeName;
	}

	public void setAccTypeName(String accTypeName) {
		this.accTypeName = accTypeName;
	}

	public String getAccName() {
		return accName;
	}

	public void setAccName(String accName) {
		this.accName = accName;
	}

	public BigDecimal getInitAmnt() {
		return initAmnt;
	}

	public void setInitAmnt(BigDecimal initAmnt) {
		this.initAmnt = initAmnt;
	}

	public BigDecimal getAccAmnt() {
		return accAmnt;
	}

	public void setAccAmnt(BigDecimal accAmnt) {
		this.accAmnt = accAmnt;
	}

	public boolean isAccDefault() {
		return accDefault;
	}

	public void setAccDefault(boolean accDefault) {
		this.accDefault = accDefault;
	}

	public boolean isAccActive() {
		return accActive;
	}

	public void setAccActive(boolean accActive) {
		this.accActive = accActive;
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

	public String getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(String createDateTime) {
		this.createDateTime = createDateTime;
	}

}
