package net.spark9092.MySimpleBook.dto.account;

import java.util.List;

import net.spark9092.MySimpleBook.dto.BaseMsgDto;

public class DetailMsgDto extends BaseMsgDto {
	
	private String accName;
	
	private String typeName;
	
	private String accAmnt;
	
	private List<List<String>> details;

	public String getAccName() {
		return accName;
	}

	public void setAccName(String accName) {
		this.accName = accName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getAccAmnt() {
		return accAmnt;
	}

	public void setAccAmnt(String accAmnt) {
		this.accAmnt = accAmnt;
	}

	public List<List<String>> getDetails() {
		return details;
	}

	public void setDetails(List<List<String>> details) {
		this.details = details;
	}

}
