package net.spark9092.MySimpleBook.dto.verify;

import java.time.LocalDateTime;

public class UserMailVerifyDataDto {

	private int verifyId;
	
	private String verifyCode;
	
	private boolean used;
	
	private LocalDateTime systemSendDatetime;

	public int getVerifyId() {
		return verifyId;
	}

	public void setVerifyId(int verifyId) {
		this.verifyId = verifyId;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}

	public LocalDateTime getSystemSendDatetime() {
		return systemSendDatetime;
	}

	public void setSystemSendDatetime(LocalDateTime systemSendDatetime) {
		this.systemSendDatetime = systemSendDatetime;
	}
}
