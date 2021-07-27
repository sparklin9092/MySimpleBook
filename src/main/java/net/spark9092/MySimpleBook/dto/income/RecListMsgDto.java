package net.spark9092.MySimpleBook.dto.income;

import java.util.List;

import net.spark9092.MySimpleBook.dto.MsgDto;

public class RecListMsgDto extends MsgDto {

	private List<List<String>> list;

	public List<List<String>> getList() {
		return list;
	}

	public void setList(List<List<String>> list) {
		this.list = list;
	}
}
