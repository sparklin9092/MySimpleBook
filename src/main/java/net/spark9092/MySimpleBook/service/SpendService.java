package net.spark9092.MySimpleBook.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.spark9092.MySimpleBook.dto.SpendItemListDto;
import net.spark9092.MySimpleBook.mapper.IItemsSpendMapper;

@Service
public class SpendService {

	private static final Logger logger = LoggerFactory.getLogger(UserLoginService.class);

	@Autowired
	private IItemsSpendMapper iItemsSpendMapper;

	public List<SpendItemListDto> getSpendListByUserId(int userId) {

		List<SpendItemListDto> spendItemListDto = new ArrayList<SpendItemListDto>();
		
		logger.debug("使用者ID: {}", userId);

		spendItemListDto = iItemsSpendMapper.selectListByUserId(userId);
		
		if(spendItemListDto.size() != 0) {
			logger.debug(spendItemListDto.toString());
		}


		return spendItemListDto;
	}

}
