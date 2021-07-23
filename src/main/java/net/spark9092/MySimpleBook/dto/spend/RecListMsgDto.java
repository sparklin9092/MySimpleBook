package net.spark9092.MySimpleBook.dto.spend;

import java.util.List;

public class RecListMsgDto {

	private List<List<String>> list;
	
	private boolean status;
	
	private String msg;

	public List<List<String>> getList() {
		return list;
	}

	public void setList(List<List<String>> list) {
		this.list = list;
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
