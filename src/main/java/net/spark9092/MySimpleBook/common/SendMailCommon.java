package net.spark9092.MySimpleBook.common;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class SendMailCommon {

	private static final String systemName = "致富寶典";

//	private static final String systemUrl = "https://richnote.net";
	private static final String systemUrl = "http://192.168.1.238:8080";

	private static final String SystemMail = "RichNote Support <support@richnote.net>";

	private static final Logger logger = LoggerFactory.getLogger(SendMailCommon.class);

	private Base64.Encoder encoder = Base64.getEncoder();

	@Autowired
	private CheckCommon checkCommon;

	/**
	 * 透過 Amazon Simple Email Service 發送電子郵件
	 * @param userMail 收件者
	 * @param htmlBody 郵件內容
	 * @param subject 郵件主旨
	 * @return
	 */
	private boolean sendMailByAwsSes(String userMail, String htmlBody, String subject) {

		boolean sendMailStatus = false;

		//檢查Email有沒有存在、有沒有值、而且不是空值
		if(null == userMail || userMail.equals("") || userMail.isEmpty()) {

			logger.error("信件未發送！！！ 錯誤原因是： 沒有提供可以發送的 Email！！！");

			return sendMailStatus;
		}

		//Email正規化校驗，檢查是否是合格的Email格式
		if(!checkCommon.checkMail(userMail)) {

			logger.error("信件未發送！！！ 錯誤原因是： Email 格式不正確！！！");

			return sendMailStatus;
		}

		try {
			
			AmazonSimpleEmailService client =
					AmazonSimpleEmailServiceClientBuilder.standard().withRegion(Regions.AP_SOUTH_1).build();

			SendEmailRequest request = new SendEmailRequest()
					.withDestination(new Destination().withToAddresses(userMail))
					.withMessage(
						new Message().withBody(
							new Body().withHtml(new Content().withCharset("UTF-8").withData(htmlBody))
						).withSubject(new Content().withCharset("UTF-8").withData(subject))
					).withSource(SystemMail);

			client.sendEmail(request);

			sendMailStatus = true;

		} catch (Exception ex) {

			logger.error("信件未發送！！！ 錯誤原因是： " + ex.getMessage());
		}

		return sendMailStatus;
	}

	/**
	 * 寄發電子信箱(Email)認證碼給使用者
	 * @param userAccount 使用者帳號
	 * @param userName 使用者名稱
	 * @param userMail 使用者電子信箱
	 * @param verifyCode 認證碼
	 * @return
	 */
	public boolean sendVerifyCodeMail(String userAccount, String userName, String userMail, String verifyCode) {

		boolean sendMailStatus = false;

		byte[] userAccountByte = {};
		try {

			userAccountByte = userAccount.getBytes("UTF-8");

			sendMailStatus = true;

		} catch (UnsupportedEncodingException e) {

			logger.error("寄發認證碼通知信，因為轉換使用者帳號為Bytes類型時發生錯誤！！！");
		}

		if(sendMailStatus) {

			String defaultSubject = "%s-認證碼通知信";
			String systemSubject = String.format(defaultSubject, systemName);

			String buAcc = encoder.encodeToString(userAccountByte);

			String defaultVerifyUrl = "%s/verify/mail/%s";
			String systemVerifyUrl = String.format(defaultVerifyUrl, systemUrl, buAcc);

			String defaultHtmlBody = "<h3>親愛的 %s 您好：</h3>"
									+ "<p>您的認證碼為：「<b><i> %s </i></b>」，認證碼有效時間為 <b><u>3</u></b> 分鐘。</p>"
									+ "<p>請盡快至 <a href='%s'>%s-電子信箱綁定功能</a> 使用。</p>"
									+ "<p>這封通知信經過加密處理，請勿將認證碼提供給任何人使用！</p>"
									+ "<p>若您未曾提出要求，請忽略這個通知，謝謝。</p>"
									+ "<p><b><a href='%s'>%s</a> 如獲至寶</b></p>";

			String systemHtmlBody = String.format(defaultHtmlBody,
					userName, //第一行：使用者名稱
					verifyCode, //第二行：認證碼
					systemVerifyUrl, systemName, //第三行：驗證網址、系統名稱
					systemUrl, systemName //最後一行：官網網址、系統名稱
			);

			sendMailStatus = sendMailByAwsSes(userMail, systemHtmlBody, systemSubject);
		}

		return sendMailStatus;
	}

	/**
	 * 寄發隨機密碼的電子信箱(Email)給使用者
	 * @param userName 使用者名稱
	 * @param userMail 使用者電子信箱
	 * @param randomPwd 隨機密碼
	 * @return
	 */
	public boolean sendRandomPwdMail(String userName, String userMail, String randomPwd) {

		boolean sendMailStatus = false;

		String defaultSubject = "%s-臨時密碼通知信";
		String systemSubject = String.format(defaultSubject, systemName);

		String defaultHtmlBody = "<h3>親愛的 %s 您好：</h3>"
								+ "<p>歡迎使用<a href='%s'>%s</a>，您的臨時密碼為：「<b><i> %s </i></b>」</p>"
								+ "<p>請使用您的電子信箱(Email)為登入帳號。</p>"
								+ "<p>建議您登入後，盡快修改新的密碼。</p>"
								+ "<p>這封通知信經過加密處理，請勿將您的密碼提供給任何人使用！</p>"
								+ "<p>若您未曾提出要求，請忽略這個通知，謝謝。</p>"
								+ "<p><b><a href='%s'>%s</a> 如獲至寶</b></p>";

		String systemHtmlBody = String.format(defaultHtmlBody,
				userName, //第一段：使用者名稱
				systemUrl, systemName, randomPwd, //第二段：系統名稱、隨機密碼
				systemUrl, systemName //最後一行：官網網址、系統名稱
		);

		sendMailStatus = sendMailByAwsSes(userMail, systemHtmlBody, systemSubject);

		return sendMailStatus;
	}

}
