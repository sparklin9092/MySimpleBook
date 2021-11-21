package net.spark9092.MySimpleBook.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.spark9092.MySimpleBook.common.GetCommon;
import net.spark9092.MySimpleBook.dto.items.income.CreateMsgDto;
import net.spark9092.MySimpleBook.dto.items.income.DeleteMsgDto;
import net.spark9092.MySimpleBook.dto.items.income.ListDto;
import net.spark9092.MySimpleBook.dto.items.income.ModifyMsgDto;
import net.spark9092.MySimpleBook.dto.items.income.OneDto;
import net.spark9092.MySimpleBook.dto.items.income.OneMsgDto;
import net.spark9092.MySimpleBook.mapper.IIncomeItemsMapper;
import net.spark9092.MySimpleBook.pojo.items.income.CreatePojo;
import net.spark9092.MySimpleBook.pojo.items.income.DeletePojo;
import net.spark9092.MySimpleBook.pojo.items.income.ModifyPojo;

@Service
public class IncomeItemsService extends BaseService {

//	private static final Logger logger = LoggerFactory.getLogger(IncomeItemsService.class);

	@Autowired
	private IIncomeItemsMapper iItemsIncomeMapper;

	@Autowired
	private GetCommon getCommon;

	/**
	 * 根據使用者ID，取得收入項目的清單
	 * @param userId
	 * @return
	 */
	public List<List<String>> getListByUserId(int userId) {

		List<List<String>> dataList = new ArrayList<>();

		List<ListDto> listDtos = iItemsIncomeMapper.selectItemListByUserId(userId);

		if(listDtos.size() != 0) {

			listDtos.stream().forEach(dto -> {

				String itemActive = "停用";
				if(dto.isItemActive()) {
					itemActive = "啟用";
				}

				List<String> itemList = new ArrayList<>();
				itemList.add(String.valueOf(dto.getItemId()));
				itemList.add(dto.getItemName());
				itemList.add(itemActive);
				itemList.add("");

				dataList.add(itemList);
			});
		}
		return dataList;
	}

	/**
	 * 取得某一筆收入項目的資料
	 * @param userId
	 * @param itemId
	 * @return
	 */
	public OneMsgDto getOneByIds(int userId, int itemId) {

		OneMsgDto oneMsgDto = new OneMsgDto();

		OneDto oneDto = iItemsIncomeMapper.selectOneByIds(itemId, userId);

		if(null == oneDto) {

			oneMsgDto.setStatus(false);
			oneMsgDto.setMsg("似乎找不到您的資料，已將您的問題提報，請稍後再試試看。");

		} else {

			String fCreateDate = getCommon.getFormatDate(oneDto.getCreateDateTime());
			
			oneMsgDto.setStatus(true);
			oneMsgDto.setMsg("");
			oneMsgDto.setItemName(oneDto.getItemName());
			oneMsgDto.setItemDefault(oneDto.isItemDefault());
			oneMsgDto.setItemActive(oneDto.isItemActive());
			oneMsgDto.setCreateDateTime(fCreateDate);

		}

		return oneMsgDto;
	}

	/**
	 * 增加一筆收入項目
	 * @param createPojo
	 * @return
	 */
	public CreateMsgDto createByPojo(CreatePojo createPojo) {

		CreateMsgDto createMsgDto = new CreateMsgDto();

		if(null == createPojo) {

			createMsgDto.setStatus(false);
			createMsgDto.setMsg("您似乎沒有資料可以新增，再檢查看看還有什麼欄位沒有填寫的。");

		} else {

			int userId = createPojo.getUserId();
			String itemName = createPojo.getItemName();
			String itemDefaultStr = createPojo.getItemDefault();

			boolean itemDefault = false;
			if(itemDefaultStr.equals("1")) {
				itemDefault = true;
			}

			boolean createStatus = iItemsIncomeMapper.insertByValues(userId, itemName, itemDefault);

			if(createStatus) {

				createMsgDto.setStatus(true);
				createMsgDto.setMsg("");

			} else {

				createMsgDto.setStatus(false);
				createMsgDto.setMsg("沒有成功新增收入項目，已將您的問題提報，請稍後再試試看。");

			}
		}

		return createMsgDto;
	}

	/**
	 * 修改某一筆收入項目
	 * @param modifyPojo
	 * @return
	 */
	public ModifyMsgDto modifyByPojo(ModifyPojo modifyPojo) {

		ModifyMsgDto modifyMsgDto = new ModifyMsgDto();

		if(null == modifyPojo) {

			modifyMsgDto.setStatus(false);
			modifyMsgDto.setMsg("您似乎沒有可以修改的資料，再檢查看看還有什麼欄位沒有填寫的。");

		} else {

			int userId = modifyPojo.getUserId();
			int itemId = modifyPojo.getItemId();
			String itemName = modifyPojo.getItemName();
			String itemActiveStr = modifyPojo.getItemActive();
			String itemDefaultStr = modifyPojo.getItemDefault();

			boolean itemActive = false;
			if(itemActiveStr.equals("1")) {
				itemActive = true;
			}

			boolean itemDefault = false;
			if(itemDefaultStr.equals("1")) {
				itemDefault = true;
			}

			boolean modifyStatus = iItemsIncomeMapper.updateByValues(userId, itemId, itemName, itemActive, itemDefault);

			if(modifyStatus) {

				modifyMsgDto.setStatus(true);
				modifyMsgDto.setMsg("");

			} else {

				modifyMsgDto.setStatus(false);
				modifyMsgDto.setMsg("沒有成功修改收入項目，已將您的問題提報，請稍後再試試看。");

			}
		}

		return modifyMsgDto;
	}

	/**
	 * 刪除某一筆收入項目
	 * @param deletePojo
	 * @return
	 */
	public DeleteMsgDto deleteByPojo(DeletePojo deletePojo) {

		DeleteMsgDto deleteMsgDto = new DeleteMsgDto();

		if(null == deletePojo) {

			deleteMsgDto.setStatus(false);
			deleteMsgDto.setMsg("您似乎沒有可以刪除的資料，再檢查看看還有什麼欄位沒有填寫的。");

		} else {

			int userId = deletePojo.getUserId();
			int itemId = deletePojo.getItemId();

			boolean deleteStatus = iItemsIncomeMapper.deleteByIds(userId, itemId);

			if(deleteStatus) {

				deleteMsgDto.setStatus(true);
				deleteMsgDto.setMsg("");

			} else {

				deleteMsgDto.setStatus(false);
				deleteMsgDto.setMsg("沒有成功刪除收入項目，已將您的問題提報，請稍後再試試看。");

			}
		}

		return deleteMsgDto;
	}

}
