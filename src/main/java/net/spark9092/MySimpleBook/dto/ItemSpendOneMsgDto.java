package net.spark9092.MySimpleBook.dto;

public class ItemSpendOneMsgDto {
	
	private ItemSpendOneDto itemSpendOneDto;
	
	private boolean status;
	
	private String msg;

	public ItemSpendOneDto getItemSpendOneDto() {
		return itemSpendOneDto;
	}

	public void setItemSpendOneDto(ItemSpendOneDto itemSpendOneDto) {
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
		return String.format("ItemSpendOneMsgDto [itemSpendOneDto=%s, status=%s, msg=%s]", itemSpendOneDto, status,
				msg);
	}

}
