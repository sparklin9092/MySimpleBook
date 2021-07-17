package net.spark9092.MySimpleBook.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.spark9092.MySimpleBook.dto.spend.SelectItemListDto;
import net.spark9092.MySimpleBook.mapper.IItemsSpendMapper;

@Service
public class SpendService {

	private static final Logger logger = LoggerFactory.getLogger(UserLoginService.class);

	@Autowired
	private IItemsSpendMapper iItemsSpendMapper;

	public List<SelectItemListDto> getSpendListByUserId(int userId) {

		List<SelectItemListDto> selectItemListDtos = new ArrayList<SelectItemListDto>();
		
		logger.debug("使用者ID: {}", userId);

		selectItemListDtos = iItemsSpendMapper.selectListByUserId(userId);
		
		if(selectItemListDtos.size() != 0) {
			logger.debug(selectItemListDtos.toString());
		}


		return selectItemListDtos;
	}

}
