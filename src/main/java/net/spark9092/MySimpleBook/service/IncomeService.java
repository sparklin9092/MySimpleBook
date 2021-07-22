package net.spark9092.MySimpleBook.service;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import net.spark9092.MySimpleBook.common.CheckCommon;
import net.spark9092.MySimpleBook.dto.income.CreateMsgDto;
import net.spark9092.MySimpleBook.dto.income.SelectAccountListDto;
import net.spark9092.MySimpleBook.dto.income.SelectAccountMsgDto;
import net.spark9092.MySimpleBook.dto.income.SelectItemListDto;
import net.spark9092.MySimpleBook.dto.income.SelectItemMsgDto;
import net.spark9092.MySimpleBook.dto.main.IncomeListDto;
import net.spark9092.MySimpleBook.dto.main.IncomeListMsgDto;
import net.spark9092.MySimpleBook.mapper.IAccountMapper;
import net.spark9092.MySimpleBook.mapper.IIncomeMapper;
import net.spark9092.MySimpleBook.pojo.income.CreatePojo;

@Service
public class IncomeService {

	private static final Logger logger = LoggerFactory.getLogger(IncomeService.class);

	@Autowired
	private CheckCommon checkCommon;

	@Autowired
	private IIncomeMapper iIncomeMapper;

	@Autowired
	private IAccountMapper iAccountMapper;

	public SelectItemMsgDto getIncomeListByUserId(int userId) {

		logger.debug("使用者ID: {}", userId);
		
		SelectItemMsgDto selectItemMsgDto = new SelectItemMsgDto();
		
		List<SelectItemListDto> selectItemListDtos = iIncomeMapper.selectItemListByUserId(userId);

		if(selectItemListDtos.size() == 0) {

			selectItemMsgDto.setStatus(false);
			selectItemMsgDto.setMsg("沒有可以使用的收入項目，請先新增或啟用收入項目。");

		} else {

			selectItemMsgDto.setStatus(true);
			selectItemMsgDto.setMsg("");
			selectItemMsgDto.setItemList(selectItemListDtos);
		}
		
		return selectItemMsgDto;
	}

	public SelectAccountMsgDto getAccountListByUserId(int userId) {

		logger.debug("使用者ID: {}", userId);
		
		SelectAccountMsgDto selectAccountMsgDto = new SelectAccountMsgDto();

		List<SelectAccountListDto> selectAccountListDtos = iIncomeMapper.selectAccountListByUserId(userId);

		if(selectAccountListDtos.size() == 0) {

			selectAccountMsgDto.setStatus(false);
			selectAccountMsgDto.setMsg("沒有可以使用的帳戶，請先新增或啟用帳戶。");

		} else {

			selectAccountMsgDto.setStatus(true);
			selectAccountMsgDto.setMsg("");
			selectAccountMsgDto.setAccountList(selectAccountListDtos);
		}
		
		return selectAccountMsgDto;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public CreateMsgDto createByPojo(CreatePojo createPojo) throws Exception {
		
		CreateMsgDto createMsgDto = new CreateMsgDto();
		
		if(null == createPojo) {

			createMsgDto.setStatus(false);
			createMsgDto.setMsg("沒有可以新增的資料");

		} else {

			int userId = createPojo.getUserId();
			int incomeItemId = createPojo.getIncomeItemId();
			int accountItemId = createPojo.getAccountItemId();
			String incomeDate = createPojo.getIncomeDate();
			BigDecimal amount = createPojo.getAmount();
			String remark = createPojo.getRemark();

			if(!checkCommon.checkAmnt(amount)) {

				createMsgDto.setStatus(false);
				createMsgDto.setMsg("輸入的金額格式不正確");
				return createMsgDto;
				
			}
			
			boolean createIncomeStatus =  iIncomeMapper.createByValues(
					userId, incomeItemId, accountItemId, incomeDate, amount, remark);
			
			if(createIncomeStatus) {
				
				boolean increaseAmntStatus = iAccountMapper.increaseAmnt(userId, accountItemId, amount);
				
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

	public IncomeListMsgDto getTodayListForMain(int userId) {

		IncomeListMsgDto incomeListMsgDto = new IncomeListMsgDto();

		List<IncomeListDto> listDtos = iIncomeMapper.getTodayListForMain(userId);

		if(listDtos.size() == 0) {

			incomeListMsgDto.setStatus(false);
			incomeListMsgDto.setMsg("今天還沒有收入，再加把勁！");

		} else {

			incomeListMsgDto.setListDtos(listDtos);
			incomeListMsgDto.setStatus(true);
			incomeListMsgDto.setMsg("");

		}

		return incomeListMsgDto;
	}

}
