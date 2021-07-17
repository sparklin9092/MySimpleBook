package net.spark9092.MySimpleBook.dto.items.income;

public class OneMsgDto {
	
	private OneDto itemIncomeOneDto;
	
	private boolean status;
	
	private String msg;

	public OneDto getItemIncomeOneDto() {
		return itemIncomeOneDto;
	}

	public void setItemIncomeOneDto(OneDto itemIncomeOneDto) {
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
