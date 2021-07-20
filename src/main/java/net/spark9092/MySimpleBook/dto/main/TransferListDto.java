package net.spark9092.MySimpleBook.dto.main;

public class TransferListDto {
	
	private String accOutName;
	
	private String accoInName;
	
	private String transAmnt;

	public String getAccOutName() {
		return accOutName;
	}

	public void setAccOutName(String accOutName) {
		this.accOutName = accOutName;
	}

	public String getAccoInName() {
		return accoInName;
	}

	public void setAccoInName(String accoInName) {
		this.accoInName = accoInName;
	}

	public String getTransAmnt() {
		return transAmnt;
	}

	public void setTransAmnt(String transAmnt) {
		this.transAmnt = transAmnt;
	}
}
