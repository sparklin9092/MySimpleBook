package net.spark9092.MySimpleBook.common;

import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;

@Component
public class SendCommon {

	private static final Logger logger = LoggerFactory.getLogger(SendCommon.class);

	private Base64.Encoder encoder = Base64.getEncoder();

	private String SystemMail = "support@richnote.net";

	/**
	 * 寄發電子信箱(Email)驗證碼給使用者
	 * @param userAccount 使用者帳號
	 * @param userName 使用者名稱
	 * @param userMail 使用者電子信箱
	 * @param verifyCode 驗證碼
	 * @return
	 */
	public boolean sendVerifyCodeMail(String userAccount, String userName, String userMail, String verifyCode) {

		boolean sendMailStatus = false;

		try {
			
			String systemName = "致富寶典";

			byte[] userAccountByte = userAccount.getBytes("UTF-8");

			String defaultSubject = "%s-認證碼通知信";
			String systemSubject = String.format(defaultSubject, systemName);

			String defaultHtmlBody = "<h3>親愛的 %s 您好：</h3>"
									+ "<p>您的認證碼為：「<b><i> %s </i></b>」，認證碼有效時間為 <b><u>3</u></b> 分鐘</p>"
									+ "<p>請盡快至 <a href='%s'>%s-電子信箱綁定功能</a> 使用。</p>"
									+ "<p>請勿將認證碼提供給任何人使用！</p>"
									+ "<p>如果您未曾要求該認證碼，請忽略這個通知，謝謝。</p>"
									+ "<p><b>致富寶典 如獲至寶</b></p>";

			String buAcc = encoder.encodeToString(userAccountByte);
			
			String defaultVerifyUrl = "https://richnote.net/verify/mail/%s";
//			String defaultVerifyUrl = "http://127.0.0.1:8080/verify/mail/%s";
			String systemVerifyUrl = String.format(defaultVerifyUrl, buAcc);
			
			String systemHtmlBody = String.format(defaultHtmlBody, userName, //第一段：使用者名稱
					verifyCode, //第二段：驗證碼
					systemVerifyUrl, systemName //第三段：驗證網址、系統名稱
			);

			AmazonSimpleEmailService client =
					AmazonSimpleEmailServiceClientBuilder.standard().withRegion(Regions.AP_SOUTH_1).build();

			SendEmailRequest request = new SendEmailRequest()
					.withDestination(new Destination().withToAddresses(userMail))
					.withMessage(
						new Message().withBody(
							new Body().withHtml(new Content().withCharset("UTF-8").withData(systemHtmlBody))
						).withSubject(new Content().withCharset("UTF-8").withData(systemSubject))
					).withSource(SystemMail);

			client.sendEmail(request);

			sendMailStatus = true;

		} catch (Exception ex) {

			logger.error("認證碼信件未發送！！！ 錯誤原因是： " + ex.getMessage());
		}

		return sendMailStatus;
	}

}
