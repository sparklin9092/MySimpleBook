package net.spark9092.MySimpleBook.dto;

public class ItemAccountTypeOneMsgDto {
	
	private ItemAccountTypeOneDto itemAccountTypeOneDto;
	
	private boolean status;
	
	private String msg;

	public ItemAccountTypeOneDto getItemAccountTypeOneDto() {
		return itemAccountTypeOneDto;
	}

	public void setItemAccountTypeOneDto(ItemAccountTypeOneDto itemAccountTypeOneDto) {
		this.itemAccountTypeOneDto = itemAccountTypeOneDto;
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

}
