package net.spark9092.MySimpleBook;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import net.spark9092.MySimpleBook.service.RichCodeService;
import net.spark9092.MySimpleBook.service.UserLoginService;

@SpringBootTest
public class RichCodeTests {

	private static final Logger logger = LoggerFactory.getLogger(UserLoginService.class);

	@Autowired
	private RichCodeService richCodeService;

	@Test
	public void getRichCodeList() {
		
		logger.debug("測試讀取財富密碼的列表");

		richCodeService.getRichCodeList();
	}
}
