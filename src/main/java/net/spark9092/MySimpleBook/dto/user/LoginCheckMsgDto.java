package net.spark9092.MySimpleBook.dto.user;

import net.spark9092.MySimpleBook.dto.MsgDto;

public class LoginCheckMsgDto extends MsgDto {

	private String userName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
