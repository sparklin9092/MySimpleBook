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
import net.spark9092.MySimpleBook.dto.main.SpendListDto;
import net.spark9092.MySimpleBook.dto.main.SpendListMsgDto;
import net.spark9092.MySimpleBook.dto.spend.CreateMsgDto;
import net.spark9092.MySimpleBook.dto.spend.SelectAccountListDto;
import net.spark9092.MySimpleBook.dto.spend.SelectAccountMsgDto;
import net.spark9092.MySimpleBook.dto.spend.SelectItemListDto;
import net.spark9092.MySimpleBook.dto.spend.SelectItemMsgDto;
import net.spark9092.MySimpleBook.mapper.IAccountMapper;
import net.spark9092.MySimpleBook.mapper.ISpendMapper;
import net.spark9092.MySimpleBook.pojo.spend.CreatePojo;

@Service
public class SpendService {

	private static final Logger logger = LoggerFactory.getLogger(UserLoginService.class);

	@Autowired
	private CheckCommon checkCommon;

	@Autowired
	private ISpendMapper iSpendMapper;

	@Autowired
	private IAccountMapper iAccountMapper;

	public SelectItemMsgDto getSpendListByUserId(int userId) {

		logger.debug("使用者ID: {}", userId);

		SelectItemMsgDto selectItemMsgDto = new SelectItemMsgDto();

		List<SelectItemListDto> spendItemListDtos = iSpendMapper.selectItemListByUserId(userId);

		if(spendItemListDtos.size() == 0) {

			selectItemMsgDto.setStatus(false);
			selectItemMsgDto.setMsg("沒有可以使用的支出項目，請先新增或啟用支出項目。");

		} else {

			selectItemMsgDto.setStatus(true);
			selectItemMsgDto.setMsg("");
			selectItemMsgDto.setItemList(spendItemListDtos);
		}
		
		return selectItemMsgDto;
	}

	public SelectAccountMsgDto getAccountListByUserId(int userId) {

		logger.debug("使用者ID: {}", userId);

		SelectAccountMsgDto selectAccountMsgDto = new SelectAccountMsgDto();

		List<SelectAccountListDto> selectAccountListDtos = iSpendMapper.selectAccountListByUserId(userId);

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
			int spendItemId = createPojo.getSpendItemId();
			int accountItemId = createPojo.getAccountItemId();
			String spendDate = createPojo.getSpendDate();
			BigDecimal amount = createPojo.getAmount();
			String remark = createPojo.getRemark();

			if(!checkCommon.checkAmnt(amount)) {

				createMsgDto.setStatus(false);
				createMsgDto.setMsg("輸入的金額格式不正確");
				return createMsgDto;
				
			}
			
			boolean createSpendStatus =  iSpendMapper.createByValues(
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

	public SpendListMsgDto getTodayListForMain(int userId) {

		SpendListMsgDto spendListMsgDto = new SpendListMsgDto();

		List<SpendListDto> listDtos = iSpendMapper.getTodayListForMain(userId);

		if(listDtos.size() == 0) {

			spendListMsgDto.setStatus(false);
			spendListMsgDto.setMsg("很棒！今天還沒有支出！繼續保持！");

		} else {

			spendListMsgDto.setListDtos(listDtos);
			spendListMsgDto.setStatus(true);
			spendListMsgDto.setMsg("");

		}

		return spendListMsgDto;
	}

}
