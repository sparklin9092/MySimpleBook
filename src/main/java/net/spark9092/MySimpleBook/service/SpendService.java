package net.spark9092.MySimpleBook.service;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.spark9092.MySimpleBook.dto.spend.SelectAccountListDto;
import net.spark9092.MySimpleBook.dto.spend.SelectItemListDto;
import net.spark9092.MySimpleBook.mapper.ISpendMapper;
import net.spark9092.MySimpleBook.pojo.spend.CreatePojo;

@Service
public class SpendService {

	private static final Logger logger = LoggerFactory.getLogger(UserLoginService.class);

	@Autowired
	private ISpendMapper iSpendMapper;

	public List<SelectItemListDto> getSpendListByUserId(int userId) {

		logger.debug("使用者ID: {}", userId);

		return iSpendMapper.selectItemListByUserId(userId);
	}

	public List<SelectAccountListDto> getAccountListByUserId(int userId) {

		logger.debug("使用者ID: {}", userId);

		return iSpendMapper.selectAccountListByUserId(userId);
	}

	public boolean createByPojo(CreatePojo createPojo) {

		if(null == createPojo) {

			return false;

		} else {

			int userId = createPojo.getUserId();
			int spendItemId = createPojo.getSpendItemId();
			int accountItemId = createPojo.getAccountItemId();
			String spendDate = createPojo.getSpendDate();
			BigDecimal amount = createPojo.getAmount();
			String remark = createPojo.getRemark();

			return iSpendMapper.createByValues(userId, spendItemId, accountItemId,
					spendDate, amount, remark);
		}
	}

}
