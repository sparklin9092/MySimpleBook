package net.spark9092.MySimpleBook.dto.verify;

import net.spark9092.MySimpleBook.dto.MsgDto;

public class UserMailMsgDto extends MsgDto {
	
	private int userId;
	
	private String userMail;
	
	private String reSendSec;
	
	private boolean used;

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

	public String getReSendSec() {
		return reSendSec;
	}

	public void setReSendSec(String reSendSec) {
		this.reSendSec = reSendSec;
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}

}
