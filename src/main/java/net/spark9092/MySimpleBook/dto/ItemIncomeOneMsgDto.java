package net.spark9092.MySimpleBook.dto;

public class ItemIncomeOneMsgDto {
	
	private ItemIncomeOneDto itemIncomeOneDto;
	
	private boolean status;
	
	private String msg;

	public ItemIncomeOneDto getItemIncomeOneDto() {
		return itemIncomeOneDto;
	}

	public void setItemIncomeOneDto(ItemIncomeOneDto itemIncomeOneDto) {
		this.itemIncomeOneDto = itemIncomeOneDto;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return String.format("ItemIncomeOneMsgDto [status=%s, msg=%s]", status, msg);
	}

}
