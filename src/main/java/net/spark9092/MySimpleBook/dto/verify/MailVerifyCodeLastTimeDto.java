package net.spark9092.MySimpleBook.dto.verify;

import java.time.LocalDateTime;

public class MailVerifyCodeLastTimeDto {

	private LocalDateTime systemSendTime;

	public LocalDateTime getSystemSendTime() {
		return systemSendTime;
	}

	public void setSystemSendTime(LocalDateTime systemSendTime) {
		this.systemSendTime = systemSendTime;
	}
}
