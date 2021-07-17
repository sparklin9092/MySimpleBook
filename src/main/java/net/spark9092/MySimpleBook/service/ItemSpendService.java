package net.spark9092.MySimpleBook.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.spark9092.MySimpleBook.dto.ItemSpendListDto;
import net.spark9092.MySimpleBook.dto.ItemSpendOneDto;
import net.spark9092.MySimpleBook.dto.ItemSpendOneMsgDto;
import net.spark9092.MySimpleBook.mapper.IItemsSpendMapper;
import net.spark9092.MySimpleBook.pojo.ItemSpendCreatePojo;
import net.spark9092.MySimpleBook.pojo.ItemSpendDeletePojo;
import net.spark9092.MySimpleBook.pojo.ItemSpendModifyPojo;

@Service
public class ItemSpendService {

	private static final Logger logger = LoggerFactory.getLogger(UserLoginService.class);

	@Autowired
	private IItemsSpendMapper iItemsSpendMapper;

	public List<List<String>> getListByUserId(int userId) {

		logger.debug("使用者ID: " + userId);

		List<List<String>> dataList = new ArrayList<>();

		List<ItemSpendListDto> itemSpendListDto = iItemsSpendMapper.selectItemListByUserId(userId);

		if(itemSpendListDto.size() != 0) {

			itemSpendListDto.stream().forEach(dto -> {

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

	public ItemSpendOneMsgDto getOneByIds(int userId, int itemId) {

		ItemSpendOneMsgDto itemSpendOneMsgDto = new ItemSpendOneMsgDto();

		ItemSpendOneDto itemSpendOneDto = iItemsSpendMapper.selectOneByIds(itemId, userId);

		if(null == itemSpendOneDto) {

			itemSpendOneMsgDto.setStatus(false);
			itemSpendOneMsgDto.setMsg("找不到資料");

			logger.error(String.format("查詢某一筆支出項目時，找不到資料。User ID: %d、Item ID: %d",
					userId, itemId));

		} else {

			itemSpendOneMsgDto.setStatus(true);
			itemSpendOneMsgDto.setMsg("");
			itemSpendOneMsgDto.setItemSpendOneDto(itemSpendOneDto);

		}

		return itemSpendOneMsgDto;
	}

	public boolean createByPojo(ItemSpendCreatePojo itemSpendCreatePojo) {

		if(null == itemSpendCreatePojo) {

			return false;

		} else {

			int userId = itemSpendCreatePojo.getUserId();
			String itemName = itemSpendCreatePojo.getItemName();
			String itemDefaultStr = itemSpendCreatePojo.getItemDefault();

			boolean itemDefault = false;
			if(itemDefaultStr.equals("1")) {
				itemDefault = true;
			}

			logger.debug(itemSpendCreatePojo.toString());

			return iItemsSpendMapper.createByValues(userId, itemName, itemDefault);
		}
	}

	public boolean modifyByPojo(ItemSpendModifyPojo itemSpendModifyPojo) {

		if(null == itemSpendModifyPojo) {

			return false;

		} else {

			int userId = itemSpendModifyPojo.getUserId();
			int itemId = itemSpendModifyPojo.getItemId();
			String itemName = itemSpendModifyPojo.getItemName();
			String itemActiveStr = itemSpendModifyPojo.getItemActive();
			String itemDefaultStr = itemSpendModifyPojo.getItemDefault();

			logger.debug(itemSpendModifyPojo.toString());

			boolean itemActive = false;
			if(itemActiveStr.equals("1")) {
				itemActive = true;
			}

			boolean itemDefault = false;
			if(itemDefaultStr.equals("1")) {
				itemDefault = true;
			}

			return iItemsSpendMapper.modifyByValues(userId, itemId, itemName, itemActive, itemDefault);
		}
	}

	public boolean deleteByPojo(ItemSpendDeletePojo itemSpendDeletePojo) {

		if(null == itemSpendDeletePojo) {

			return false;

		} else {

			int userId = itemSpendDeletePojo.getUserId();
			int itemId = itemSpendDeletePojo.getItemId();

			logger.debug(itemSpendDeletePojo.toString());

			return iItemsSpendMapper.deleteByIds(userId, itemId);
		}
	}

}
