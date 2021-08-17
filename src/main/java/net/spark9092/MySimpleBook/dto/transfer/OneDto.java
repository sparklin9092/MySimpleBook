package net.spark9092.MySimpleBook.dto.transfer;

import java.math.BigDecimal;

public class OneDto {

	private String transDate;

	private BigDecimal amount;

	private int outAccId;

	private int inAccId;

	private boolean isOutside;

	private String outsideAccName;

	private String remark;

	public String getTransDate() {
		return transDate;
	}

	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public int getOutAccId() {
		return outAccId;
	}

	public void setOutAccId(int outAccId) {
		this.outAccId = outAccId;
	}

	public int getInAccId() {
		return inAccId;
	}

	public void setInAccId(int inAccId) {
		this.inAccId = inAccId;
	}

	public boolean isOutside() {
		return isOutside;
	}

	public void setOutside(boolean isOutside) {
		this.isOutside = isOutside;
	}

	public String getOutsideAccName() {
		return outsideAccName;
	}

	public void setOutsideAccName(String outsideAccName) {
		this.outsideAccName = outsideAccName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
