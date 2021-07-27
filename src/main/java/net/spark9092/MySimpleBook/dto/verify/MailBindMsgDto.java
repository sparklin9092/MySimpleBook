package net.spark9092.MySimpleBook.dto.verify;

import net.spark9092.MySimpleBook.dto.MsgDto;

public class MailBindMsgDto extends MsgDto {
	
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
