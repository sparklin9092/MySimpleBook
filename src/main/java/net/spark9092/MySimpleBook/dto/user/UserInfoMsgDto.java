package net.spark9092.MySimpleBook.dto.user;

public class UserInfoMsgDto {

	private UserInfoDto dto;

	private boolean status;

	private String msg;

	public UserInfoDto getDto() {
		return dto;
	}

	public void setDto(UserInfoDto dto) {
		this.dto = dto;
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
