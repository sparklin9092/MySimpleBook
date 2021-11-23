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
import net.spark9092.MySimpleBook.dto.income.CreateMsgDto;
import net.spark9092.MySimpleBook.dto.income.DeleteMsgDto;
import net.spark9092.MySimpleBook.dto.income.ModifyMsgDto;
import net.spark9092.MySimpleBook.dto.income.OneDto;
import net.spark9092.MySimpleBook.dto.income.OneMsgDto;
import net.spark9092.MySimpleBook.dto.income.RecListDto;
import net.spark9092.MySimpleBook.dto.income.RecListMsgDto;
import net.spark9092.MySimpleBook.dto.income.SelectAccountListDto;
import net.spark9092.MySimpleBook.dto.income.SelectAccountMsgDto;
import net.spark9092.MySimpleBook.dto.income.SelectItemListDto;
import net.spark9092.MySimpleBook.dto.income.SelectItemMsgDto;
import net.spark9092.MySimpleBook.dto.main.IncomeRecordDto;
import net.spark9092.MySimpleBook.dto.main.IncomeRecMsgDto;
import net.spark9092.MySimpleBook.mapper.IAccountMapper;
import net.spark9092.MySimpleBook.mapper.IIncomeMapper;
import net.spark9092.MySimpleBook.pojo.income.CreatePojo;
import net.spark9092.MySimpleBook.pojo.income.DeletePojo;
import net.spark9092.MySimpleBook.pojo.income.ModifyPojo;
import net.spark9092.MySimpleBook.pojo.income.RecordPojo;

@Service
public class IncomeService extends BaseService {

	private static final Logger logger = LoggerFactory.getLogger(IncomeService.class);

	@Autowired
	private CheckCommon checkCommon;

	@Autowired
	private GetCommon getCommon;

	@Autowired
	private IIncomeMapper iIncomeMapper;

	@Autowired
	private IAccountMapper iAccountMapper;
	
	public IncomeService() {
		logger.info("");
	}

	/**
	 * 取得收入項目的清單，用於下拉選單
	 * @param userId
	 * @return
	 */
	public SelectItemMsgDto getIncomeListByUserId(int userId) {

		SelectItemMsgDto selectItemMsgDto = new SelectItemMsgDto();

		List<SelectItemListDto> selectItemListDtos = iIncomeMapper.selectItemListByUserId(userId);

		if(selectItemListDtos.size() == 0) {

			selectItemMsgDto.setStatus(false);
			selectItemMsgDto.setMsg("您目前沒有可以使用的收入項目，先新增一個或啟用收入項目吧！");

		} else {

			selectItemMsgDto.setStatus(true);
			selectItemMsgDto.setMsg("");
			selectItemMsgDto.setItemList(selectItemListDtos);
		}

		return selectItemMsgDto;
	}

	/**
	 * 取得帳戶的清單，用於下拉選單
	 * @param userId
	 * @return
	 */
	public SelectAccountMsgDto getAccountListByUserId(int userId) {

		SelectAccountMsgDto selectAccountMsgDto = new SelectAccountMsgDto();

		List<SelectAccountListDto> selectAccountListDtos = iIncomeMapper.selectAccountListByUserId(userId);

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
	 * 取得收入紀錄
	 * @param recordPojo
	 * @return
	 */
	public RecListMsgDto getRecordsByUserId(RecordPojo recordPojo) {

		RecListMsgDto recListMsgDto = new RecListMsgDto();

		int userId = recordPojo.getUserId();
		String startDate = recordPojo.getStartDate();
		String endDate = recordPojo.getEndDate();

		List<RecListDto> recListDtos = iIncomeMapper.selectRecordsByUserId(userId, startDate, endDate);

		if(recListDtos.size() == 0) {

			recListMsgDto.setStatus(false);
			recListMsgDto.setMsg("沒有收入紀錄。");

		} else {

			List<List<String>> dataList = new ArrayList<>();

			recListDtos.stream().forEach(dto -> {

				List<String> record = new ArrayList<>();
				record.add(String.valueOf(dto.getIncomeId()));
				record.add(dto.getIncomeDate());
				record.add(dto.getIncomeItemName());
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
	 * 取得某一筆收入資料
	 * @param userId
	 * @param incomeId
	 * @return
	 */
	public OneMsgDto getOneByIds(int userId, int incomeId) {

		OneMsgDto oneMsgDto = new OneMsgDto();

		OneDto oneDto = iIncomeMapper.selectOneByIds(incomeId, userId);

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
	 * 首頁取得當天的收入資料
	 * @param userId
	 * @return
	 */
	public IncomeRecMsgDto getTodayListForMain(int userId) {

		IncomeRecMsgDto incomeListMsgDto = new IncomeRecMsgDto();

		List<IncomeRecordDto> listDtos = iIncomeMapper.selectTodayListForMain(userId);

		if(listDtos.size() == 0) {

			incomeListMsgDto.setStatus(false);
			incomeListMsgDto.setMsg("沒關係，今天還沒有收入，明天再加把勁！");

		} else {
			//處理金額格式化
			listDtos.stream().forEach(dto -> {
				dto.setAmnt(getCommon.getNoZeroAmnt(decimalFormat.format(new BigDecimal(dto.getAmnt()))));
			});

			incomeListMsgDto.setListDtos(listDtos);
			incomeListMsgDto.setStatus(true);
			incomeListMsgDto.setMsg("");

		}

		return incomeListMsgDto;
	}

	/**
	 * 增加一筆收入
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
			int incomeItemId = createPojo.getIncomeItemId();
			int accountItemId = createPojo.getAccountItemId();
			String incomeDate = createPojo.getIncomeDate();
			BigDecimal amount = createPojo.getAmount();
			String remark = createPojo.getRemark();

			if(!checkCommon.checkAmnt(amount)) {

				createMsgDto.setStatus(false);
				createMsgDto.setMsg("您輸入的金額格式不正確，再檢查看看。");
				return createMsgDto;

			}

			boolean createIncomeStatus =  iIncomeMapper.insertByValues(
					userId, incomeItemId, accountItemId, incomeDate, amount, remark);

			if(createIncomeStatus) {

				boolean increaseAmntStatus = iAccountMapper.increaseAmnt(
						userId, accountItemId, amount);

				if(increaseAmntStatus) {

					createMsgDto.setStatus(true);
					createMsgDto.setMsg("");

				} else {

					//當增加帳戶餘額發生錯誤時，rollback 全部交易
					throw new Exception("增加帳戶餘額發生錯誤");
				}

			} else {

				//新增一筆收入發生錯誤，rollback 全部交易
				throw new Exception("新增一筆收入發生錯誤");
			}
		}

		return createMsgDto;
	}

	/**
	 * 修改某一筆收入資料
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
			int incomeId = modifyPojo.getIncomeId();
			int incomeItemId = modifyPojo.getIncomeItemId();
			int accountId = modifyPojo.getAccountId();
			String incomeDate = modifyPojo.getIncomeDate();
			BigDecimal amount = modifyPojo.getAmount();
			String remark = modifyPojo.getRemark();

			if(!checkCommon.checkAmnt(amount)) {

				modifyMsgDto.setStatus(false);
				modifyMsgDto.setMsg("您輸入的金額格式不正確，再檢查看看。");
				return modifyMsgDto;

			}

			//先查出原本的資料
			OneDto oneDto = iIncomeMapper.selectOneByIds(incomeId, userId);

			if(null == oneDto) {

				//查詢要刪除某一筆收入發生錯誤時，rollback 全部交易
				throw new Exception("查詢要修改的某一筆收入發生錯誤");
			}

			//先恢復這筆收入之前的帳戶餘額
			//加進去的錢要扣回來
			boolean rDecreaseAmntStatus = iAccountMapper.decreaseAmnt(userId, oneDto.getAccountId(), oneDto.getAmount());

			if(!rDecreaseAmntStatus) {

				//當  [減少]  帳戶餘額發生錯誤時，rollback 全部交易
				throw new Exception("減少帳戶餘額發生錯誤");
			}

			//把加進去的錢扣回來之後，再更新這筆收入資料
			boolean modifyStatus = false;
			modifyStatus = iIncomeMapper.updateByValues(userId, incomeId, incomeItemId, accountId, incomeDate, amount, remark);

			//收入資料修改成功之後，就重新把錢加入到帳戶裡面
			if(modifyStatus) {

				boolean increaseAmntStatus = iAccountMapper.increaseAmnt(userId, accountId, amount);

				if(increaseAmntStatus) {

					modifyMsgDto.setStatus(true);
					modifyMsgDto.setMsg("");

				} else {

					//當  [增加]  帳戶餘額發生錯誤時，rollback 全部交易
					throw new Exception("增加帳戶餘額發生錯誤");
				}
			} else {

				//當修改一筆收入發生錯誤時，rollback 全部交易
				throw new Exception("修改一筆收入發生錯誤");
			}
		}

		return modifyMsgDto;
	}

	/**
	 * 移除某一筆收入
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
			int incomeId = deletePojo.getIncomeId();

			//查出要刪除的那筆收入資料
			OneDto oneDto = iIncomeMapper.selectOneByIds(incomeId, userId);

			if(null == oneDto) {

				//查詢要刪除某一筆收入發生錯誤時，rollback 全部交易
				throw new Exception("查詢要刪除的某一筆收入發生錯誤");
			}

			boolean deleteStatus = iIncomeMapper.deleteByIds(userId, incomeId);

			if(deleteStatus) {

				//原本加進去帳戶的錢要扣回來
				boolean decreaseAmntStatus = iAccountMapper.decreaseAmnt(userId, oneDto.getAccountId(), oneDto.getAmount());

				if(decreaseAmntStatus) {

					deleteMsgDto.setStatus(true);
					deleteMsgDto.setMsg("");

				} else {

					//當  [減少]  帳戶餘額發生錯誤時，rollback 全部交易
					throw new Exception("減少帳戶餘額發生錯誤");
				}
			} else {

				//當刪除一筆收入發生錯誤時，rollback 全部交易
				throw new Exception("刪除一筆收入發生錯誤");
			}
		}

		return deleteMsgDto;
	}

}
