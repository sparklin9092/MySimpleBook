package net.spark9092.MySimpleBook.dto.user;

import net.spark9092.MySimpleBook.dto.BaseMsgDto;

public class BindEmailMsgDto extends BaseMsgDto {

	private String userEmail;

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
}
