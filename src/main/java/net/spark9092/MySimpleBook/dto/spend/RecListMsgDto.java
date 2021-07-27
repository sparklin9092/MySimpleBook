package net.spark9092.MySimpleBook.dto.spend;

import java.util.List;

import net.spark9092.MySimpleBook.dto.BaseMsgDto;

public class RecListMsgDto extends BaseMsgDto {

	private List<List<String>> list;

	public List<List<String>> getList() {
		return list;
	}

	public void setList(List<List<String>> list) {
		this.list = list;
	}
}
