package net.spark9092.MySimpleBook.service;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import net.spark9092.MySimpleBook.dto.spend.SelectAccountListDto;
import net.spark9092.MySimpleBook.dto.spend.SelectItemListDto;
import net.spark9092.MySimpleBook.mapper.IAccountMapper;
import net.spark9092.MySimpleBook.mapper.ISpendMapper;
import net.spark9092.MySimpleBook.pojo.spend.CreatePojo;

@Service
public class SpendService {

	private static final Logger logger = LoggerFactory.getLogger(UserLoginService.class);

	@Autowired
	private ISpendMapper iSpendMapper;

	@Autowired
	private IAccountMapper iAccountMapper;

	public List<SelectItemListDto> getSpendListByUserId(int userId) {

		logger.debug("使用者ID: {}", userId);

		return iSpendMapper.selectItemListByUserId(userId);
	}

	public List<SelectAccountListDto> getAccountListByUserId(int userId) {

		logger.debug("使用者ID: {}", userId);

		return iSpendMapper.selectAccountListByUserId(userId);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean createByPojo(CreatePojo createPojo) throws Exception {
		
		if(null == createPojo) {

			return false;

		} else {

			int userId = createPojo.getUserId();
			int spendItemId = createPojo.getSpendItemId();
			int accountItemId = createPojo.getAccountItemId();
			String spendDate = createPojo.getSpendDate();
			BigDecimal amount = createPojo.getAmount();
			String remark = createPojo.getRemark();

			boolean createSpendStatus =  iSpendMapper.createByValues(
					userId, spendItemId, accountItemId, spendDate, amount, remark);
			
			if(createSpendStatus) {
				
				boolean decreaseAmntStatus = iAccountMapper.decreaseAmnt(userId, accountItemId, amount);
				
				if(decreaseAmntStatus) {
					
					return true;
					
				} else {
					
					//當減少帳戶餘額發生錯誤時，rollback 全部交易
					throw new Exception("減少帳戶餘額發生錯誤");
				}
				
			} else {
				
				//新增一筆支出發生錯誤，rollback 全部交易
				throw new Exception("新增一筆支出發生錯誤");
			}
		}
	}

}
