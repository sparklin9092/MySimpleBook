package net.spark9092.MySimpleBook.dto;

public class ItemAccountTypeCreateMsgDto {
	
	private boolean status;
	
	private String msg;

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
		return String.format("ItemSpendCreateMsgDto [status=%s, msg=%s]", status, msg);
	}

}
