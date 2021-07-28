package net.spark9092.MySimpleBook.dto.user;

import java.time.LocalDateTime;

public class EmailStatusDto {
	
	private String userEmail;
	
	private String verifyCode;
	
	private LocalDateTime sysSendTime;
	
	private boolean verifyCodeUsed;
	
	private boolean verifyCodeActive;
	
	private boolean verifyCodeDelete;

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	public LocalDateTime getSysSendTime() {
		return sysSendTime;
	}

	public void setSysSendTime(LocalDateTime sysSendTime) {
		this.sysSendTime = sysSendTime;
	}

	public boolean isVerifyCodeUsed() {
		return verifyCodeUsed;
	}

	public void setVerifyCodeUsed(boolean verifyCodeUsed) {
		this.verifyCodeUsed = verifyCodeUsed;
	}

	public boolean isVerifyCodeActive() {
		return verifyCodeActive;
	}

	public void setVerifyCodeActive(boolean verifyCodeActive) {
		this.verifyCodeActive = verifyCodeActive;
	}

	public boolean isVerifyCodeDelete() {
		return verifyCodeDelete;
	}

	public void setVerifyCodeDelete(boolean verifyCodeDelete) {
		this.verifyCodeDelete = verifyCodeDelete;
	}

}
