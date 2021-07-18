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
import net.spark9092.MySimpleBook.dto.income.SelectAccountListDto;
import net.spark9092.MySimpleBook.dto.income.SelectItemListDto;
import net.spark9092.MySimpleBook.mapper.IAccountMapper;
import net.spark9092.MySimpleBook.mapper.IIncomeMapper;
import net.spark9092.MySimpleBook.pojo.income.CreatePojo;

@Service
public class IncomeService {

	private static final Logger logger = LoggerFactory.getLogger(UserLoginService.class);

	@Autowired
	private CheckCommon checkCommon;

	@Autowired
	private IIncomeMapper iIncomeMapper;

	@Autowired
	private IAccountMapper iAccountMapper;

	public List<SelectItemListDto> getIncomeListByUserId(int userId) {

		logger.debug("使用者ID: {}", userId);

		return iIncomeMapper.selectItemListByUserId(userId);
	}

	public List<SelectAccountListDto> getAccountListByUserId(int userId) {

		logger.debug("使用者ID: {}", userId);

		return iIncomeMapper.selectAccountListByUserId(userId);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean createByPojo(CreatePojo createPojo) throws Exception {
		
		if(null == createPojo) {

			return false;

		} else {

			int userId = createPojo.getUserId();
			int incomeItemId = createPojo.getAccountItemId();
			int accountItemId = createPojo.getAccountItemId();
			String incomeDate = createPojo.getIncomeDate();
			BigDecimal amount = createPojo.getAmount();
			String remark = createPojo.getRemark();

			if(!checkCommon.checkAmnt(amount)) return false;
			
			boolean createIncomeStatus =  iIncomeMapper.createByValues(
					userId, incomeItemId, accountItemId, incomeDate, amount, remark);
			
			if(createIncomeStatus) {
				
				boolean increaseAmntStatus = iAccountMapper.increaseAmnt(userId, accountItemId, amount);
				
				if(increaseAmntStatus) {
					
					return true;
					
				} else {
					
					//當增加帳戶餘額發生錯誤時，rollback 全部交易
					throw new Exception("增加帳戶餘額發生錯誤");
				}
				
			} else {
				
				//新增一筆收入發生錯誤，rollback 全部交易
				throw new Exception("新增一筆收入發生錯誤");
			}
		}
	}

}
