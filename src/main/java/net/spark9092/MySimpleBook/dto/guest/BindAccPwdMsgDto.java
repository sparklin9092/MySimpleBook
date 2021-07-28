package net.spark9092.MySimpleBook.dto.guest;

import net.spark9092.MySimpleBook.dto.BaseMsgDto;

public class BindAccPwdMsgDto extends BaseMsgDto {

	private String userAccount;

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
}
