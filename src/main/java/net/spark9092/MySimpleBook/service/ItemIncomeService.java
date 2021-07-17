package net.spark9092.MySimpleBook.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.spark9092.MySimpleBook.dto.ItemIncomeListDto;
import net.spark9092.MySimpleBook.dto.ItemIncomeOneDto;
import net.spark9092.MySimpleBook.dto.ItemIncomeOneMsgDto;
import net.spark9092.MySimpleBook.mapper.IItemsIncomeMapper;
import net.spark9092.MySimpleBook.pojo.ItemIncomeCreatePojo;
import net.spark9092.MySimpleBook.pojo.ItemIncomeDeletePojo;
import net.spark9092.MySimpleBook.pojo.ItemIncomeModifyPojo;

@Service
public class ItemIncomeService {

	private static final Logger logger = LoggerFactory.getLogger(UserLoginService.class);

	@Autowired
	private IItemsIncomeMapper iItemsIncomeMapper;

	public List<List<String>> getListByUserId(int userId) {

		logger.debug("使用者ID: " + userId);

		List<List<String>> dataList = new ArrayList<>();

		List<ItemIncomeListDto> itemIncomeListDto = iItemsIncomeMapper.selectItemListByUserId(userId);

		if(itemIncomeListDto.size() != 0) {

			itemIncomeListDto.stream().forEach(dto -> {

				String itemDefault = "否";
				if(dto.isItemDefault()) {
					itemDefault = "是";
				}

				String itemActive = "停用";
				if(dto.isItemActive()) {
					itemActive = "啟用";
				}

				List<String> itemList = new ArrayList<>();
				itemList.add(String.valueOf(dto.getItemId()));
				itemList.add(dto.getItemName());
				itemList.add(itemDefault);
				itemList.add(itemActive);
				itemList.add("");

				dataList.add(itemList);
			});
		}
		return dataList;
	}

	public ItemIncomeOneMsgDto getOneByIds(int userId, int itemId) {

		ItemIncomeOneMsgDto itemIncomeOneMsgDto = new ItemIncomeOneMsgDto();

		ItemIncomeOneDto itemIncomeOneDto = iItemsIncomeMapper.selectOneByIds(itemId, userId);

		if(null == itemIncomeOneDto) {

			itemIncomeOneMsgDto.setStatus(false);
			itemIncomeOneMsgDto.setMsg("找不到資料");

			logger.error(String.format("查詢某一筆收入項目時，找不到資料。User ID: %d、Item ID: %d",
					userId, itemId));

		} else {

			itemIncomeOneMsgDto.setStatus(true);
			itemIncomeOneMsgDto.setMsg("");
			itemIncomeOneMsgDto.setItemIncomeOneDto(itemIncomeOneDto);

		}

		return itemIncomeOneMsgDto;
	}

	public boolean createByPojo(ItemIncomeCreatePojo itemIncomeCreatePojo) {

		if(null == itemIncomeCreatePojo) {

			return false;

		} else {

			int userId = itemIncomeCreatePojo.getUserId();
			String itemName = itemIncomeCreatePojo.getItemName();
			String itemDefaultStr = itemIncomeCreatePojo.getItemDefault();

			boolean itemDefault = false;
			if(itemDefaultStr.equals("1")) {
				itemDefault = true;
			}

			logger.debug(itemIncomeCreatePojo.toString());

			return iItemsIncomeMapper.createByValues(userId, itemName, itemDefault);
		}
	}

	public boolean modifyByPojo(ItemIncomeModifyPojo itemIncomeModifyPojo) {

		if(null == itemIncomeModifyPojo) {

			return false;

		} else {

			int userId = itemIncomeModifyPojo.getUserId();
			int itemId = itemIncomeModifyPojo.getItemId();
			String itemName = itemIncomeModifyPojo.getItemName();
			String itemActiveStr = itemIncomeModifyPojo.getItemActive();
			String itemDefaultStr = itemIncomeModifyPojo.getItemDefault();

			logger.debug(itemIncomeModifyPojo.toString());

			boolean itemActive = false;
			if(itemActiveStr.equals("1")) {
				itemActive = true;
			}

			boolean itemDefault = false;
			if(itemDefaultStr.equals("1")) {
				itemDefault = true;
			}

			return iItemsIncomeMapper.modifyByValues(userId, itemId, itemName, itemActive, itemDefault);
		}
	}

	public boolean deleteByPojo(ItemIncomeDeletePojo itemIncomeDeletePojo) {

		if(null == itemIncomeDeletePojo) {

			return false;

		} else {

			int userId = itemIncomeDeletePojo.getUserId();
			int itemId = itemIncomeDeletePojo.getItemId();

			logger.debug(itemIncomeDeletePojo.toString());

			return iItemsIncomeMapper.deleteByIds(userId, itemId);
		}
	}

}
