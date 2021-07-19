package net.spark9092.MySimpleBook.pojo.transfer;

import java.math.BigDecimal;

public class CreatePojo {
	
	private int userId;

	private String transferDate;
	
	private int tOutAccId;
	
	private BigDecimal tOutAmnt;
	
	private int tInAccId;
	
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

	public int gettOutAccId() {
		return tOutAccId;
	}

	public void settOutAccId(int tOutAccId) {
		this.tOutAccId = tOutAccId;
	}

	public BigDecimal gettOutAmnt() {
		return tOutAmnt;
	}

	public void settOutAmnt(BigDecimal tOutAmnt) {
		this.tOutAmnt = tOutAmnt;
	}

	public int gettInAccId() {
		return tInAccId;
	}

	public void settInAccId(int tInAccId) {
		this.tInAccId = tInAccId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
