package net.spark9092.MySimpleBook.dto.verify;

import net.spark9092.MySimpleBook.dto.BaseMsgDto;

public class MailBindMsgDto extends BaseMsgDto {
	
	private String userMail;

	public String getUserMail() {
		return userMail;
	}

	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}
}
