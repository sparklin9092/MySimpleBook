package net.spark9092.MySimpleBook.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.spark9092.MySimpleBook.dto.ItemAccountTypeListDto;
import net.spark9092.MySimpleBook.dto.ItemAccountTypeOneDto;
import net.spark9092.MySimpleBook.dto.ItemAccountTypeOneMsgDto;
import net.spark9092.MySimpleBook.mapper.IItemsAccountTypeMapper;
import net.spark9092.MySimpleBook.pojo.ItemAccountTypeCreatePojo;
import net.spark9092.MySimpleBook.pojo.ItemAccountTypeDeletePojo;
import net.spark9092.MySimpleBook.pojo.ItemAccountTypeModifyPojo;

@Service
public class ItemAccountTypeService {

	private static final Logger logger = LoggerFactory.getLogger(UserLoginService.class);

	@Autowired
	private IItemsAccountTypeMapper iItemsAccountTypeMapper;

	public List<List<String>> getListByUserId(int userId) {

		logger.debug("使用者ID: " + userId);

		List<List<String>> dataList = new ArrayList<>();

		List<ItemAccountTypeListDto> itemAccountTypeListDto = iItemsAccountTypeMapper.selectItemListByUserId(userId);

		if(itemAccountTypeListDto.size() != 0) {

			itemAccountTypeListDto.stream().forEach(dto -> {

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

	public ItemAccountTypeOneMsgDto getOneByIds(int userId, int itemId) {

		ItemAccountTypeOneMsgDto itemAccountTypeOneMsgDto = new ItemAccountTypeOneMsgDto();

		ItemAccountTypeOneDto itemAccountTypeOneDto = iItemsAccountTypeMapper.selectOneByIds(itemId, userId);

		if(null == itemAccountTypeOneDto) {

			itemAccountTypeOneMsgDto.setStatus(false);
			itemAccountTypeOneMsgDto.setMsg("找不到資料");

			logger.error(String.format("查詢某一筆支出項目時，找不到資料。User ID: %d、Item ID: %d",
					userId, itemId));

		} else {

			itemAccountTypeOneMsgDto.setStatus(true);
			itemAccountTypeOneMsgDto.setMsg("");
			itemAccountTypeOneMsgDto.setItemAccountTypeOneDto(itemAccountTypeOneDto);

		}

		return itemAccountTypeOneMsgDto;
	}

	public boolean createByPojo(ItemAccountTypeCreatePojo itemAccountTypeCreatePojo) {

		if(null == itemAccountTypeCreatePojo) {

			return false;

		} else {

			int userId = itemAccountTypeCreatePojo.getUserId();
			String itemName = itemAccountTypeCreatePojo.getItemName();
			String itemDefaultStr = itemAccountTypeCreatePojo.getItemDefault();

			boolean itemDefault = false;
			if(itemDefaultStr.equals("1")) {
				itemDefault = true;
			}

			logger.debug(itemAccountTypeCreatePojo.toString());

			return iItemsAccountTypeMapper.createByValues(userId, itemName, itemDefault);
		}
	}

	public boolean modifyByPojo(ItemAccountTypeModifyPojo itemAccountTypeModifyPojo) {

		if(null == itemAccountTypeModifyPojo) {

			return false;

		} else {

			int userId = itemAccountTypeModifyPojo.getUserId();
			int itemId = itemAccountTypeModifyPojo.getItemId();
			String itemName = itemAccountTypeModifyPojo.getItemName();
			String itemActiveStr = itemAccountTypeModifyPojo.getItemActive();
			String itemDefaultStr = itemAccountTypeModifyPojo.getItemDefault();

			logger.debug(itemAccountTypeModifyPojo.toString());

			boolean itemActive = false;
			if(itemActiveStr.equals("1")) {
				itemActive = true;
			}

			boolean itemDefault = false;
			if(itemDefaultStr.equals("1")) {
				itemDefault = true;
			}

			return iItemsAccountTypeMapper.modifyByValues(
					userId, itemId, itemName, itemActive, itemDefault);
		}
	}

	public boolean deleteByPojo(ItemAccountTypeDeletePojo itemAccountTypeDeletePojo) {

		if(null == itemAccountTypeDeletePojo) {

			return false;

		} else {

			int userId = itemAccountTypeDeletePojo.getUserId();
			int itemId = itemAccountTypeDeletePojo.getItemId();

			logger.debug(itemAccountTypeDeletePojo.toString());

			return iItemsAccountTypeMapper.deleteByIds(userId, itemId);
		}
	}

}
