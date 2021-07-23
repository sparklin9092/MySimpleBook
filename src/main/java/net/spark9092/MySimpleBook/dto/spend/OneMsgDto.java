package net.spark9092.MySimpleBook.dto.spend;

public class OneMsgDto {
	
	private OneDto oneDto;
	
	private boolean status;
	
	private String msg;

	public OneDto getOneDto() {
		return oneDto;
	}

	public void setOneDto(OneDto oneDto) {
		this.oneDto = oneDto;
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
