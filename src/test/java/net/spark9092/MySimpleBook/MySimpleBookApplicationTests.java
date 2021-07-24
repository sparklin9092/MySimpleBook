package net.spark9092.MySimpleBook;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MySimpleBookApplicationTests {

	private static final Logger logger = LoggerFactory.getLogger(MySimpleBookApplicationTests.class);

	@Test
	void contextLoads() throws Exception {
		logger.debug("第一個測試");
	}

}
