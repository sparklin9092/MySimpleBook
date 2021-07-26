package net.spark9092.MySimpleBook.dto.verify;

import java.time.LocalDateTime;

public class MailVerifyCodeLastTimeDto {

	private LocalDateTime systemSendTime;
	
	private boolean used;

	public LocalDateTime getSystemSendTime() {
		return systemSendTime;
	}

	public void setSystemSendTime(LocalDateTime systemSendTime) {
		this.systemSendTime = systemSendTime;
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}
}
