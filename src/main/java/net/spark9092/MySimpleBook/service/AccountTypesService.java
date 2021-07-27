package net.spark9092.MySimpleBook.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.spark9092.MySimpleBook.dto.items.accountType.CreateMsgDto;
import net.spark9092.MySimpleBook.dto.items.accountType.DeleteMsgDto;
import net.spark9092.MySimpleBook.dto.items.accountType.ListDto;
import net.spark9092.MySimpleBook.dto.items.accountType.ModifyMsgDto;
import net.spark9092.MySimpleBook.dto.items.accountType.OneDto;
import net.spark9092.MySimpleBook.dto.items.accountType.OneMsgDto;
import net.spark9092.MySimpleBook.mapper.IAccountTypesMapper;
import net.spark9092.MySimpleBook.pojo.items.accountType.CreatePojo;
import net.spark9092.MySimpleBook.pojo.items.accountType.DeletePojo;
import net.spark9092.MySimpleBook.pojo.items.accountType.ModifyPojo;

@Service
public class AccountTypesService {

//	private static final Logger logger = LoggerFactory.getLogger(AccountTypesService.class);

	@Autowired
	private IAccountTypesMapper iItemsAccountTypeMapper;

	/**
	 * 根據使用者ID，取得帳戶類型的清單
	 * @param userId
	 * @return
	 */
	public List<List<String>> getListByUserId(int userId) {

		List<List<String>> dataList = new ArrayList<>();

		List<ListDto> listDtos = iItemsAccountTypeMapper.selectItemListByUserId(userId);

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
	 * 根據帳戶類型索引和使用者ID，取得某一筆帳戶類型的資料
	 * @param userId
	 * @param typeId
	 * @return
	 */
	public OneMsgDto getOneByIds(int userId, int typeId) {

		OneMsgDto oneMsgDto = new OneMsgDto();

		OneDto oneDto = iItemsAccountTypeMapper.selectOneByIds(typeId, userId);

		if(null == oneDto) {

			oneMsgDto.setStatus(false);
			oneMsgDto.setMsg("似乎找不到您的資料，已將您的問題提報，請稍後再試試看。");

		} else {

			oneMsgDto.setStatus(true);
			oneMsgDto.setMsg("");
			oneMsgDto.setItemAccountTypeOneDto(oneDto);

		}

		return oneMsgDto;
	}

	/**
	 * 增加一筆帳戶類型
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

			boolean createStatus = iItemsAccountTypeMapper.insertByValues(userId, itemName, itemDefault);

			if(createStatus) {

				createMsgDto.setStatus(true);
				createMsgDto.setMsg("");

			} else {

				createMsgDto.setStatus(false);
				createMsgDto.setMsg("沒有成功新增帳戶類型，已將您的問題提報，請稍後再試試看。");

			}
		}

		return createMsgDto;
	}

	/**
	 * 修改一筆帳戶類型
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

			boolean modifyStatus = iItemsAccountTypeMapper.updateByValues(
					userId, itemId, itemName, itemActive, itemDefault);

			if(modifyStatus) {

				modifyMsgDto.setStatus(true);
				modifyMsgDto.setMsg("");

			} else {

				modifyMsgDto.setStatus(false);
				modifyMsgDto.setMsg("沒有成功修改帳戶類型，已將您的問題提報，請稍後再試試看。");

			}
		}

		return modifyMsgDto;
	}

	/**
	 * 刪除一筆帳戶類型
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

			boolean deleteStatus = iItemsAccountTypeMapper.deleteByIds(userId, itemId);

			if(deleteStatus) {

				deleteMsgDto.setStatus(true);
				deleteMsgDto.setMsg("");

			} else {

				deleteMsgDto.setStatus(false);
				deleteMsgDto.setMsg("沒有成功刪除帳戶類型，已將您的問題提報，請稍後再試試看。");

			}
		}

		return deleteMsgDto;
	}

}
