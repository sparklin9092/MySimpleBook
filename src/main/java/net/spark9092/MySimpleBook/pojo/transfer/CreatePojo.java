package net.spark9092.MySimpleBook.pojo.transfer;

import java.math.BigDecimal;

public class CreatePojo {
	
	private int userId;

	private String transferDate;
	
	private BigDecimal amount;
	
	private int tOutAccId;
	
	private int tInAccId;
	
	private boolean outSideCheck;

	private String tOutsideAccName;
	
	private String remark;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getTransferDate() {
		return transferDate;
	}

	public void setTransferDate(String transferDate) {
		this.transferDate = transferDate;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public int gettOutAccId() {
		return tOutAccId;
	}

	public void settOutAccId(int tOutAccId) {
		this.tOutAccId = tOutAccId;
	}

	public int gettInAccId() {
		return tInAccId;
	}

	public void settInAccId(int tInAccId) {
		this.tInAccId = tInAccId;
	}

	public boolean isOutSideCheck() {
		return outSideCheck;
	}

	public void setOutSideCheck(boolean outSideCheck) {
		this.outSideCheck = outSideCheck;
	}

	public String gettOutsideAccName() {
		return tOutsideAccName;
	}

	public void settOutsideAccName(String tOutsideAccName) {
		this.tOutsideAccName = tOutsideAccName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
