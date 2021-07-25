package net.spark9092.MySimpleBook.common;

import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class GeneratorCommon {

	/**
	 * 隨機取得8個字的使用者密碼
	 * @return 
	 */
	public String getUserPwd() {

		String outStr = "";
		int num = 0;

		while (outStr.length() < 8) {

			// 亂數取編號為 50~90 的字符 (排除 0 和 1)
			num = (int) (Math.random() * (90 - 50 + 1)) + 50;

			if (num > 57 && num < 65) {
				
				// 排除非數字非字母
				continue;
				
			} else if (num == 73 || num == 76 || num == 79) {
				
				// 排除 I、L、O
				continue;
				
			}

			outStr += (char) num;
		}

		return outStr.toLowerCase();

	}
	
	public String getVerifyCode() {

		Random random = new Random();
		String outStr = "";
		int num = 0;

		while (outStr.length() < 6) {
			
			num = random.nextInt(10);
			
			outStr += num;
		}

		return outStr;
		
	}
}
