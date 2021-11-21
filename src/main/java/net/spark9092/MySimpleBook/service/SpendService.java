package net.spark9092.MySimpleBook.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import net.spark9092.MySimpleBook.common.CheckCommon;
import net.spark9092.MySimpleBook.common.GetCommon;
import net.spark9092.MySimpleBook.dto.main.SpendListDto;
import net.spark9092.MySimpleBook.dto.main.SpendListMsgDto;
import net.spark9092.MySimpleBook.dto.spend.CreateMsgDto;
import net.spark9092.MySimpleBook.dto.spend.DeleteMsgDto;
import net.spark9092.MySimpleBook.dto.spend.ModifyMsgDto;
import net.spark9092.MySimpleBook.dto.spend.OneDto;
import net.spark9092.MySimpleBook.dto.spend.OneMsgDto;
import net.spark9092.MySimpleBook.dto.spend.RecListDto;
import net.spark9092.MySimpleBook.dto.spend.RecListMsgDto;
import net.spark9092.MySimpleBook.dto.spend.SelectAccountListDto;
import net.spark9092.MySimpleBook.dto.spend.SelectAccountMsgDto;
import net.spark9092.MySimpleBook.dto.spend.SelectItemListDto;
import net.spark9092.MySimpleBook.dto.spend.SelectItemMsgDto;
import net.spark9092.MySimpleBook.mapper.IAccountMapper;
import net.spark9092.MySimpleBook.mapper.ISpendMapper;
import net.spark9092.MySimpleBook.pojo.spend.CreatePojo;
import net.spark9092.MySimpleBook.pojo.spend.DeletePojo;
import net.spark9092.MySimpleBook.pojo.spend.ModifyPojo;
import net.spark9092.MySimpleBook.pojo.spend.RecordPojo;

@Service
public class SpendService extends BaseService {

	private static final Logger logger = LoggerFactory.getLogger(SpendService.class);

	@Autowired
	private CheckCommon checkCommon;

	@Autowired
	private GetCommon getCommon;

	@Autowired
	private ISpendMapper iSpendMapper;

	@Autowired
	private IAccountMapper iAccountMapper;
	
	public SpendService() {
		logger.info("");
	}

	/**
	 * 取得支出項目清單，用於下拉選單
	 * @param userId
	 * @return
	 */
	public SelectItemMsgDto getSpendListByUserId(int userId) {

		SelectItemMsgDto selectItemMsgDto = new SelectItemMsgDto();

		List<SelectItemListDto> spendItemListDtos = iSpendMapper.selectItemListByUserId(userId);

		if(spendItemListDtos.size() == 0) {

			selectItemMsgDto.setStatus(false);
			selectItemMsgDto.setMsg("您目前似乎沒有可以使用的支出項目，先新增一個或啟用支出項目吧！");

		} else {

			selectItemMsgDto.setStatus(true);
			selectItemMsgDto.setMsg("");
			selectItemMsgDto.setItemList(spendItemListDtos);
		}

		return selectItemMsgDto;
	}

	/**
	 * 取得帳戶清單，用於下拉選單
	 * @param userId
	 * @return
	 */
	public SelectAccountMsgDto getAccountListByUserId(int userId) {

		SelectAccountMsgDto selectAccountMsgDto = new SelectAccountMsgDto();

		List<SelectAccountListDto> selectAccountListDtos = iSpendMapper.selectAccountListByUserId(userId);

		if(selectAccountListDtos.size() == 0) {

			selectAccountMsgDto.setStatus(false);
			selectAccountMsgDto.setMsg("您目前沒有可以使用的帳戶，先新增一個或啟用帳戶吧！");

		} else {

			selectAccountMsgDto.setStatus(true);
			selectAccountMsgDto.setMsg("");
			selectAccountMsgDto.setAccountList(selectAccountListDtos);
		}

		return selectAccountMsgDto;
	}

	/**
	 * 取得支出紀錄
	 * @param recordPojo
	 * @return
	 */
	public RecListMsgDto getRecordsByUserId(RecordPojo recordPojo) {

		RecListMsgDto recListMsgDto = new RecListMsgDto();

		int userId = recordPojo.getUserId();
		String startDate = recordPojo.getStartDate();
		String endDate = recordPojo.getEndDate();

		List<RecListDto> recListDtos = iSpendMapper.selectRecordsByUserId(userId, startDate, endDate);

		if(recListDtos.size() == 0) {

			recListMsgDto.setStatus(false);
			recListMsgDto.setMsg("沒有支出紀錄。");

		} else {

			List<List<String>> dataList = new ArrayList<>();

			recListDtos.stream().forEach(dto -> {

				List<String> record = new ArrayList<>();
				record.add(String.valueOf(dto.getSpendId()));
				record.add(dto.getSpendDate());
				record.add(dto.getSpendItmeName());
				record.add(getCommon.getNoZeroAmnt(decimalFormat.format(dto.getAmount())));
				record.add("");

				dataList.add(record);
			});

			recListMsgDto.setStatus(true);
			recListMsgDto.setMsg("");
			recListMsgDto.setList(dataList);
		}

		return recListMsgDto;
	}

	/**
	 * 取得某一筆支出資料
	 * @param userId
	 * @param spendId
	 * @return
	 */
	public OneMsgDto getOneByIds(int userId, int spendId) {

		OneMsgDto oneMsgDto = new OneMsgDto();

		OneDto oneDto = iSpendMapper.selectOneByIds(spendId, userId);

		if(null == oneDto) {

			oneMsgDto.setStatus(false);
			oneMsgDto.setMsg("似乎找不到您的資料，已將您的問題提報，請稍後再試試看。");

		} else {

			oneMsgDto.setStatus(true);
			oneMsgDto.setMsg("");
			oneMsgDto.setOneDto(oneDto);

		}

		return oneMsgDto;
	}

	/**
	 * 首頁取得當天的支出資料
	 * @param userId
	 * @return
	 */
	public SpendListMsgDto getTodayListForMain(int userId) {

		SpendListMsgDto spendListMsgDto = new SpendListMsgDto();

		List<SpendListDto> listDtos = iSpendMapper.selectTodayListForMain(userId);

		if(listDtos.size() == 0) {

			spendListMsgDto.setStatus(false);
			spendListMsgDto.setMsg("水喔！今天還沒有支出，但還是要記得吃飯喔。");

		} else {

			spendListMsgDto.setListDtos(listDtos);
			spendListMsgDto.setStatus(true);
			spendListMsgDto.setMsg("");

		}

		return spendListMsgDto;
	}

	/**
	 * 增加一筆支出
	 * @param createPojo
	 * @return
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public CreateMsgDto createByPojo(CreatePojo createPojo) throws Exception {

		CreateMsgDto createMsgDto = new CreateMsgDto();

		if(null == createPojo) {

			createMsgDto.setStatus(false);
			createMsgDto.setMsg("您似乎沒有資料可以新增，再檢查看看還有什麼欄位沒有填寫的。");

		} else {

			int userId = createPojo.getUserId();
			int spendItemId = createPojo.getSpendItemId();
			int accountItemId = createPojo.getAccountItemId();
			String spendDate = createPojo.getSpendDate();
			BigDecimal amount = createPojo.getAmount();
			String remark = createPojo.getRemark();

			if(!checkCommon.checkAmnt(amount)) {

				createMsgDto.setStatus(false);
				createMsgDto.setMsg("您輸入的金額格式不正確，再檢查看看。");
				return createMsgDto;

			}

			boolean createSpendStatus =  iSpendMapper.insertByValues(
					userId, spendItemId, accountItemId, spendDate, amount, remark);

			if(createSpendStatus) {

				boolean decreaseAmntStatus = iAccountMapper.decreaseAmnt(userId, accountItemId, amount);

				if(decreaseAmntStatus) {

					createMsgDto.setStatus(true);
					createMsgDto.setMsg("");

				} else {

					//當減少帳戶餘額發生錯誤時，rollback 全部交易
					throw new Exception("減少帳戶餘額發生錯誤");
				}

			} else {

				//新增一筆支出發生錯誤，rollback 全部交易
				throw new Exception("新增一筆支出發生錯誤");
			}
		}

		return createMsgDto;
	}

	/**
	 * 修改一筆支出
	 * @param modifyPojo
	 * @return
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ModifyMsgDto modifyByPojo(ModifyPojo modifyPojo) throws Exception {

		ModifyMsgDto modifyMsgDto = new ModifyMsgDto();

		if(null == modifyPojo) {

			modifyMsgDto.setStatus(false);
			modifyMsgDto.setMsg("您似乎沒有可以修改的資料，再檢查看看還有什麼欄位沒有填寫的。");

		} else {

			int userId = modifyPojo.getUserId();
			int spendId = modifyPojo.getSpendId();
			int spendItemId = modifyPojo.getSpendItemId();
			int accountId = modifyPojo.getAccountId();
			String spendDate = modifyPojo.getSpendDate();
			BigDecimal amount = modifyPojo.getAmount();
			String remark = modifyPojo.getRemark();

			if(!checkCommon.checkAmnt(amount)) {

				modifyMsgDto.setStatus(false);
				modifyMsgDto.setMsg("您輸入的金額格式不正確，再檢查看看。");
				return modifyMsgDto;

			}

			//先查出原本的資料
			OneDto oneDto = iSpendMapper.selectOneByIds(spendId, userId);

			if(null == oneDto) {

				//查詢要刪除某一筆支出發生錯誤時，rollback 全部交易
				throw new Exception("查詢要修改的某一筆支出發生錯誤");
			}

			//先恢復這筆支出之前的帳戶餘額
			//被扣掉的錢要加回去
			boolean rIncreaseAmntStatus = iAccountMapper.increaseAmnt(userId, oneDto.getAccountId(), oneDto.getAmount());

			if(!rIncreaseAmntStatus) {

				//當  [增加]  帳戶餘額發生錯誤時，rollback 全部交易
				throw new Exception("增加帳戶餘額發生錯誤");
			}

			//把被扣掉的錢加回去之後，再更新這筆支出資料
			boolean modifyStatus = false;
			modifyStatus = iSpendMapper.updateByValues(userId, spendId, spendItemId, accountId, spendDate, amount, remark);

			//支出資料修改成功之後，就重新把錢扣掉到帳戶裡面
			if(modifyStatus) {

				boolean decreaseAmntStatus = iAccountMapper.decreaseAmnt(userId, accountId, amount);

				if(decreaseAmntStatus) {

					modifyMsgDto.setStatus(true);
					modifyMsgDto.setMsg("");

				} else {

					//當  [減少]  帳戶餘額發生錯誤時，rollback 全部交易
					throw new Exception("減少帳戶餘額發生錯誤");
				}
			} else {

				//當修改一筆支出發生錯誤時，rollback 全部交易
				throw new Exception("修改一筆支出發生錯誤");
			}
		}

		return modifyMsgDto;
	}

	/**
	 * 移除某一筆支出
	 * @param deletePojo
	 * @return
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public DeleteMsgDto deleteByPojo(DeletePojo deletePojo) throws Exception {

		DeleteMsgDto deleteMsgDto = new DeleteMsgDto();

		if(null == deletePojo) {

			deleteMsgDto.setStatus(false);
			deleteMsgDto.setMsg("您似乎沒有可以刪除的資料，再檢查看看還有什麼欄位沒有填寫的。");

		} else {

			int userId = deletePojo.getUserId();
			int spendId = deletePojo.getSpendId();

			//查出要刪除的那筆收入資料
			OneDto oneDto = iSpendMapper.selectOneByIds(spendId, userId);

			if(null == oneDto) {

				//查詢要刪除某一筆支出發生錯誤時，rollback 全部交易
				throw new Exception("查詢要刪除的某一筆支出發生錯誤");
			}

			boolean deleteStatus = iSpendMapper.deleteByIds(userId, spendId);

			if(deleteStatus) {

				//原本被扣掉帳戶的錢要加回去
				boolean increaseAmntStatus = iAccountMapper.increaseAmnt(userId, oneDto.getAccountId(), oneDto.getAmount());

				if(increaseAmntStatus) {

					deleteMsgDto.setStatus(true);
					deleteMsgDto.setMsg("");

				} else {

					//當  [增加]  帳戶餘額發生錯誤時，rollback 全部交易
					throw new Exception("增加帳戶餘額發生錯誤");
				}
			} else {

				//當刪除一筆支出發生錯誤時，rollback 全部交易
				throw new Exception("刪除一筆支出發生錯誤");
			}
		}

		return deleteMsgDto;
	}

}
