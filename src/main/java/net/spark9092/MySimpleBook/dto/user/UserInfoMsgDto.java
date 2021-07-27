package net.spark9092.MySimpleBook.dto.user;

import net.spark9092.MySimpleBook.dto.MsgDto;

public class UserInfoMsgDto extends MsgDto {

	private UserInfoDto dto;

	public UserInfoDto getDto() {
		return dto;
	}

	public void setDto(UserInfoDto dto) {
		this.dto = dto;
	}

}
