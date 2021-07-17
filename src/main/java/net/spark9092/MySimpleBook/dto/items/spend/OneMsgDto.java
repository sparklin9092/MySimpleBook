package net.spark9092.MySimpleBook.dto.items.spend;

public class OneMsgDto {
	
	private OneDto itemSpendOneDto;
	
	private boolean status;
	
	private String msg;

	public OneDto getItemSpendOneDto() {
		return itemSpendOneDto;
	}

	public void setItemSpendOneDto(OneDto itemSpendOneDto) {
		this.itemSpendOneDto = itemSpendOneDto;
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
		return String.format("ItemSpendOneMsgDto [status=%s, msg=%s]", status, msg);
	}

}
