package net.spark9092.MySimpleBook.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.spark9092.MySimpleBook.dto.items.spend.ListDto;
import net.spark9092.MySimpleBook.dto.items.spend.OneDto;
import net.spark9092.MySimpleBook.dto.items.spend.OneMsgDto;
import net.spark9092.MySimpleBook.mapper.IItemsSpendMapper;
import net.spark9092.MySimpleBook.pojo.items.spend.CreatePojo;
import net.spark9092.MySimpleBook.pojo.items.spend.DeletePojo;
import net.spark9092.MySimpleBook.pojo.items.spend.ModifyPojo;

@Service
public class ItemSpendService {

	private static final Logger logger = LoggerFactory.getLogger(UserLoginService.class);

	@Autowired
	private IItemsSpendMapper iItemsSpendMapper;

	public List<List<String>> getListByUserId(int userId) {

		logger.debug("使用者ID: " + userId);

		List<List<String>> dataList = new ArrayList<>();

		List<ListDto> listDtos = iItemsSpendMapper.selectItemListByUserId(userId);

		if(listDtos.size() != 0) {

			listDtos.stream().forEach(dto -> {

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

	public OneMsgDto getOneByIds(int userId, int itemId) {

		OneMsgDto oneMsgDto = new OneMsgDto();

		OneDto oneDto = iItemsSpendMapper.selectOneByIds(itemId, userId);

		if(null == oneDto) {

			oneMsgDto.setStatus(false);
			oneMsgDto.setMsg("找不到資料");

			logger.error(String.format("查詢某一筆支出項目時，找不到資料。User ID: %d、Item ID: %d",
					userId, itemId));

		} else {

			oneMsgDto.setStatus(true);
			oneMsgDto.setMsg("");
			oneMsgDto.setItemSpendOneDto(oneDto);

		}

		return oneMsgDto;
	}

	public boolean createByPojo(CreatePojo createPojo) {

		if(null == createPojo) {

			return false;

		} else {

			int userId = createPojo.getUserId();
			String itemName = createPojo.getItemName();
			String itemDefaultStr = createPojo.getItemDefault();

			boolean itemDefault = false;
			if(itemDefaultStr.equals("1")) {
				itemDefault = true;
			}

			logger.debug(createPojo.toString());

			return iItemsSpendMapper.createByValues(userId, itemName, itemDefault);
		}
	}

	public boolean modifyByPojo(ModifyPojo modifyPojo) {

		if(null == modifyPojo) {

			return false;

		} else {

			int userId = modifyPojo.getUserId();
			int itemId = modifyPojo.getItemId();
			String itemName = modifyPojo.getItemName();
			String itemActiveStr = modifyPojo.getItemActive();
			String itemDefaultStr = modifyPojo.getItemDefault();

			logger.debug(modifyPojo.toString());

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

	public boolean deleteByPojo(DeletePojo deletePojo) {

		if(null == deletePojo) {

			return false;

		} else {

			int userId = deletePojo.getUserId();
			int itemId = deletePojo.getItemId();

			logger.debug(deletePojo.toString());

			return iItemsSpendMapper.deleteByIds(userId, itemId);
		}
	}

}
