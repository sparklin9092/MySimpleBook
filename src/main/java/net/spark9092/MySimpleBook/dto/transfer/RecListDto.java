package net.spark9092.MySimpleBook.dto.transfer;

import java.math.BigDecimal;

public class RecListDto {
	
	private int transId;
	
	private String transDate;
	
	private String transOutAccName;
	
	private String transInAccName;
	
	private BigDecimal transAmnt;

	public int getTransId() {
		return transId;
	}

	public void setTransId(int transId) {
		this.transId = transId;
	}

	public String getTransDate() {
		return transDate;
	}

	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}

	public String getTransOutAccName() {
		return transOutAccName;
	}

	public void setTransOutAccName(String transOutAccName) {
		this.transOutAccName = transOutAccName;
	}

	public String getTransInAccName() {
		return transInAccName;
	}

	public void setTransInAccName(String transInAccName) {
		this.transInAccName = transInAccName;
	}

	public BigDecimal getTransAmnt() {
		return transAmnt;
	}

	public void setTransAmnt(BigDecimal transAmnt) {
		this.transAmnt = transAmnt;
	}

}
