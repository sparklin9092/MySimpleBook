package net.spark9092.MySimpleBook.dto.verify;

import net.spark9092.MySimpleBook.dto.BaseMsgDto;

public class MailBindMsgDto extends BaseMsgDto {
	
	private int userId;
	
	private String userMail;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserMail() {
		return userMail;
	}

	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}
}
