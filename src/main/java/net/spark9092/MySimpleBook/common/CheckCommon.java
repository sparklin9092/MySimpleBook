package net.spark9092.MySimpleBook.common;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

@Component
public class CheckCommon {

	public boolean checkAmnt(BigDecimal amount) {
		
		String regex = "^[0-9]{1,10}(\\.[0-9]{1,2})?$";
		
		return amount.toString().matches(regex);
	}
}
