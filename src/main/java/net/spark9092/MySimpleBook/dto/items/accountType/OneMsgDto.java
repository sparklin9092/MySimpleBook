package net.spark9092.MySimpleBook.dto.items.accountType;

public class OneMsgDto {
	
	private OneDto itemAccountTypeOneDto;
	
	private boolean status;
	
	private String msg;

	public OneDto getItemAccountTypeOneDto() {
		return itemAccountTypeOneDto;
	}

	public void setItemAccountTypeOneDto(OneDto itemAccountTypeOneDto) {
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
