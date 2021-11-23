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
import net.spark9092.MySimpleBook.dto.main.TransRecordDto;
import net.spark9092.MySimpleBook.dto.main.TransRecMsgDto;
import net.spark9092.MySimpleBook.dto.transfer.CreateMsgDto;
import net.spark9092.MySimpleBook.dto.transfer.DeleteMsgDto;
import net.spark9092.MySimpleBook.dto.transfer.ModifyMsgDto;
import net.spark9092.MySimpleBook.dto.transfer.OneDto;
import net.spark9092.MySimpleBook.dto.transfer.OneMsgDto;
import net.spark9092.MySimpleBook.dto.transfer.RecListDto;
import net.spark9092.MySimpleBook.dto.transfer.RecListMsgDto;
import net.spark9092.MySimpleBook.dto.transfer.SelectAccountListDto;
import net.spark9092.MySimpleBook.dto.transfer.SelectAccountMsgDto;
import net.spark9092.MySimpleBook.mapper.IAccountMapper;
import net.spark9092.MySimpleBook.mapper.ITransferMapper;
import net.spark9092.MySimpleBook.pojo.transfer.CreatePojo;
import net.spark9092.MySimpleBook.pojo.transfer.DeletePojo;
import net.spark9092.MySimpleBook.pojo.transfer.ModifyPojo;
import net.spark9092.MySimpleBook.pojo.transfer.RecordPojo;

@Service
public class TransferService extends BaseService {

	private static final Logger logger = LoggerFactory.getLogger(TransferService.class);

	@Autowired
	private CheckCommon checkCommon;

	@Autowired
	private GetCommon getCommon;

	@Autowired
	private ITransferMapper iTransferMapper;

	@Autowired
	private IAccountMapper iAccountMapper;
	
	public TransferService() {
		logger.info("");
	}

	/**
	 * 取得帳戶清單，用於下拉選單
	 * @param userId
	 * @return
	 */
	public SelectAccountMsgDto getAccountListByUserId(int userId) {

		SelectAccountMsgDto selectAccountMsgDto = new SelectAccountMsgDto();

		List<SelectAccountListDto> selectAccountListDtos = iTransferMapper.selectAccountListByUserId(userId);

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
	 * 取得轉帳紀錄
	 * @param recordPojo
	 * @return
	 */
	public RecListMsgDto getRecordsByUserId(RecordPojo recordPojo) {

		RecListMsgDto recListMsgDto = new RecListMsgDto();

		int userId = recordPojo.getUserId();
		String startDate = recordPojo.getStartDate();
		String endDate = recordPojo.getEndDate();

		List<RecListDto> recListDtos = iTransferMapper.selectRecordsByUserId(userId, startDate, endDate);

		if(recListDtos.size() == 0) {

			recListMsgDto.setStatus(false);
			recListMsgDto.setMsg("沒有轉帳紀錄。");

		} else {

			List<List<String>> dataList = new ArrayList<>();

			recListDtos.stream().forEach(dto -> {

				List<String> record = new ArrayList<>();
				record.add(String.valueOf(dto.getTransId()));
				record.add(dto.getTransDate());
				record.add(dto.getTransOutAccName());
				record.add(dto.getTransInAccName());
				record.add(getCommon.getNoZeroAmnt(decimalFormat.format(dto.getTransAmnt())));
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
	 * 取得某一筆轉帳資料
	 * @param userId
	 * @param transferId
	 * @return
	 */
	public OneMsgDto getOneByIds(int userId, int transferId) {

		OneMsgDto oneMsgDto = new OneMsgDto();

		OneDto oneDto = iTransferMapper.selectOneByIds(transferId, userId);

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
	 * 首頁取得當日的轉帳資料
	 * @param userId
	 * @return
	 */
	public TransRecMsgDto getTodayListForMain(int userId) {

		TransRecMsgDto transferListMsgDto = new TransRecMsgDto();

		List<TransRecordDto> listDtos = iTransferMapper.selectTodayListForMain(userId);

		if(listDtos.size() == 0) {

			transferListMsgDto.setStatus(false);
			transferListMsgDto.setMsg("今天沒有轉帳紀錄。");

		} else {

			transferListMsgDto.setListDtos(listDtos);
			transferListMsgDto.setStatus(true);
			transferListMsgDto.setMsg("");

		}

		return transferListMsgDto;
	}

	/**
	 * 增加一筆轉帳
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
			String transferDate = createPojo.getTransferDate();
			BigDecimal amnt = createPojo.getAmount();
			int tOutAccId = createPojo.gettOutAccId();
			int tInAccId = createPojo.gettInAccId();
			boolean outSideCheck = createPojo.isOutSideCheck();
			String tOutsideAccName = createPojo.gettOutsideAccName();
			String remark = createPojo.getRemark();

			if(!outSideCheck && tOutAccId == tInAccId) {

				createMsgDto.setStatus(false);
				createMsgDto.setMsg("您無法在同一個帳戶進行轉帳。");
				return createMsgDto;

			}

			if(!checkCommon.checkAmnt(amnt)) {

				createMsgDto.setStatus(false);
				createMsgDto.setMsg("您輸入的金額格式不正確，再檢查看看。");
				return createMsgDto;

			}

			boolean createTransferStatus = false;

			//檢查是否為外部帳戶轉帳
			if(outSideCheck) {

				//當轉帳是外部帳戶時
				createTransferStatus =  iTransferMapper.insertOutsideByValues(
						userId, transferDate, amnt, tOutAccId, tOutsideAccName, remark);
			} else {

				//是一般轉帳
				createTransferStatus = iTransferMapper.insertByValues(
						userId, transferDate, amnt, tOutAccId, tInAccId, remark);
			}

			if(createTransferStatus) {

				boolean decreaseAmntStatus = iAccountMapper.decreaseAmnt(userId, tOutAccId, amnt);

				if(decreaseAmntStatus) {

					//如果是一般轉帳才會餘額才會增加，外部轉帳不會
					if(!outSideCheck) {

						boolean increaseAmntStatus = iAccountMapper.increaseAmnt(userId, tInAccId, amnt);

						if(increaseAmntStatus) {

							createMsgDto.setStatus(true);
							createMsgDto.setMsg("");

						} else {

							//當  [增加]  帳戶餘額發生錯誤時，rollback 全部交易
							throw new Exception("增加帳戶餘額發生錯誤");
						}
					} else {

						createMsgDto.setStatus(true);
						createMsgDto.setMsg("");
					}
				} else {

					//當  [減少]  帳戶餘額發生錯誤時，rollback 全部交易
					throw new Exception("減少帳戶餘額發生錯誤");
				}
			} else {

				//當新增一筆轉帳發生錯誤時，rollback 全部交易
				throw new Exception("新增一筆轉帳發生錯誤");
			}
		}

		return createMsgDto;
	}

	/**
	 * 修改某一筆轉帳
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
			int transferId = modifyPojo.getTransferId();
			String transferDate = modifyPojo.getTransferDate();
			BigDecimal amnt = modifyPojo.getAmount();
			int tOutAccId = modifyPojo.gettOutAccId();
			int tInAccId = modifyPojo.gettInAccId();
			boolean outSideCheck = modifyPojo.isOutSideCheck();
			String tOutsideAccName = modifyPojo.gettOutsideAccName();
			String remark = modifyPojo.getRemark();

			if(!outSideCheck && tOutAccId == tInAccId) {

				modifyMsgDto.setStatus(false);
				modifyMsgDto.setMsg("您無法在同一個帳戶進行轉帳。");
				return modifyMsgDto;

			}

			if(!checkCommon.checkAmnt(amnt)) {

				modifyMsgDto.setStatus(false);
				modifyMsgDto.setMsg("您輸入的金額格式不正確，再檢查看看。");
				return modifyMsgDto;

			}

			//先查出原本的資料
			OneDto oneDto = iTransferMapper.selectOneByIds(transferId, userId);

			if(null == oneDto) {

				//查詢要刪除某一筆轉帳發生錯誤時，rollback 全部交易
				throw new Exception("查詢要修改的某一筆轉帳發生錯誤");
			}

			//先恢復這筆轉帳之前的帳戶餘額
			//轉出去的錢要加回來
			boolean rIncreaseAmntStatus = iAccountMapper.increaseAmnt(userId, oneDto.getOutAccId(), oneDto.getAmount());

			if(rIncreaseAmntStatus) {

				//如果是一般轉帳，轉進來的錢要扣回去
				if(!outSideCheck) {

					boolean rDecreaseAmntStatus = iAccountMapper.decreaseAmnt(userId, oneDto.getInAccId(), oneDto.getAmount());

					if(!rDecreaseAmntStatus) {

						//當  [減少]  帳戶餘額發生錯誤時，rollback 全部交易
						throw new Exception("減少帳戶餘額發生錯誤");
					}
				}
			} else {

				//當  [增加]  帳戶餘額發生錯誤時，rollback 全部交易
				throw new Exception("增加帳戶餘額發生錯誤");
			}

			boolean modifyStatus = false;

			//檢查是否為外部帳戶轉帳
			if(outSideCheck) {

				//當轉帳是外部帳戶時
				modifyStatus =  iTransferMapper.updateOutsideByValues(
						userId, transferId, transferDate, amnt, tOutAccId, tOutsideAccName, remark);
			} else {

				//是一般轉帳
				modifyStatus = iTransferMapper.updateByValues(
						userId, transferId, transferDate, amnt, tOutAccId, tInAccId, remark);
			}

			if(modifyStatus) {

				boolean decreaseAmntStatus = iAccountMapper.decreaseAmnt(userId, tOutAccId, amnt);

				if(decreaseAmntStatus) {

					//如果是一般轉帳才會餘額才會增加，外部轉帳不會
					if(!outSideCheck) {

						boolean increaseAmntStatus = iAccountMapper.increaseAmnt(userId, tInAccId, amnt);

						if(increaseAmntStatus) {

							modifyMsgDto.setStatus(true);
							modifyMsgDto.setMsg("");

						} else {

							//當  [增加]  帳戶餘額發生錯誤時，rollback 全部交易
							throw new Exception("增加帳戶餘額發生錯誤");
						}
					} else {

						modifyMsgDto.setStatus(true);
						modifyMsgDto.setMsg("");
					}
				} else {

					//當  [減少]  帳戶餘額發生錯誤時，rollback 全部交易
					throw new Exception("減少帳戶餘額發生錯誤");
				}
			} else {

				//當修改一筆轉帳發生錯誤時，rollback 全部交易
				throw new Exception("修改一筆轉帳發生錯誤");
			}
		}

		return modifyMsgDto;
	}

	/**
	 * 移除某一筆轉帳
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
			int transferId = deletePojo.getTransferId();

			//查出要刪除的那筆轉帳資料
			OneDto oneDto = iTransferMapper.selectOneByIds(transferId, userId);

			if(null == oneDto) {

				//查詢要刪除某一筆轉帳發生錯誤時，rollback 全部交易
				throw new Exception("查詢要刪除的某一筆轉帳發生錯誤");
			}

			boolean deleteStatus = iTransferMapper.deleteByIds(userId, transferId);

			if(deleteStatus) {

				//轉出去的錢要加回來
				boolean increaseAmntStatus = iAccountMapper.increaseAmnt(userId, oneDto.getOutAccId(), oneDto.getAmount());

				if(increaseAmntStatus) {

					//如果是一般轉帳，轉進來的錢要扣回去
					if(!oneDto.isOutside()) {

						boolean decreaseAmntStatus = iAccountMapper.decreaseAmnt(userId, oneDto.getInAccId(), oneDto.getAmount());

						if(decreaseAmntStatus) {

							deleteMsgDto.setStatus(true);
							deleteMsgDto.setMsg("");

						} else {

							//當  [減少]  帳戶餘額發生錯誤時，rollback 全部交易
							throw new Exception("減少帳戶餘額發生錯誤");
						}
					} else {

						deleteMsgDto.setStatus(true);
						deleteMsgDto.setMsg("");
					}
				} else {

					//當  [增加]  帳戶餘額發生錯誤時，rollback 全部交易
					throw new Exception("增加帳戶餘額發生錯誤");
				}
			} else {

				//當刪除一筆轉帳發生錯誤時，rollback 全部交易
				throw new Exception("刪除一筆轉帳發生錯誤");
			}
		}

		return deleteMsgDto;
	}

}
