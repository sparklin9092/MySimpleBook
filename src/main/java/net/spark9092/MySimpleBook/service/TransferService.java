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
import net.spark9092.MySimpleBook.dto.transfer.SelectAccountListDto;
import net.spark9092.MySimpleBook.mapper.IAccountMapper;
import net.spark9092.MySimpleBook.mapper.ITransferMapper;
import net.spark9092.MySimpleBook.pojo.transfer.CreatePojo;

@Service
public class TransferService {

	private static final Logger logger = LoggerFactory.getLogger(UserLoginService.class);

	@Autowired
	private CheckCommon checkCommon;

	@Autowired
	private ITransferMapper iTransferMapper;

	@Autowired
	private IAccountMapper iAccountMapper;

	public List<SelectAccountListDto> getAccountListByUserId(int userId) {

		logger.debug("使用者ID: {}", userId);

		return iTransferMapper.selectAccountListByUserId(userId);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean createByPojo(CreatePojo createPojo) throws Exception {
		
		if(null == createPojo) {

			return false;

		} else {

			int userId = createPojo.getUserId();
			String transferDate = createPojo.getTransferDate();
			int tOutAccId = createPojo.gettOutAccId();
			BigDecimal tOutAmnt = createPojo.gettOutAmnt();
			int tInAccId = createPojo.gettInAccId();
			BigDecimal tInAmnt = tOutAmnt;
			String remark = createPojo.getRemark();

			if(tOutAccId == tInAccId) return false;
			
			if(!checkCommon.checkAmnt(tOutAmnt)) return false;
			
			boolean createTransferStatus =  iTransferMapper.createByValues(
					userId, transferDate, tInAccId, tInAmnt, tOutAccId, tOutAmnt, remark);
			
			if(createTransferStatus) {
				
				boolean decreaseAmntStatus = iAccountMapper.decreaseAmnt(userId, tOutAccId, tOutAmnt);
				
				if(decreaseAmntStatus) {
					
					boolean increaseAmntStatus = iAccountMapper.increaseAmnt(userId, tInAccId, tInAmnt);
					
					if(increaseAmntStatus) {
						
						return true;
						
					} else {
						
						//當  [增加]  帳戶餘額發生錯誤時，rollback 全部交易
						throw new Exception("增加帳戶餘額發生錯誤");
					}
				} else {
					
					//當  [減少]  帳戶餘額發生錯誤時，rollback 全部交易
					throw new Exception("減少帳戶餘額發生錯誤");
				}
			} else {
				
				//新增一筆轉帳發生錯誤，rollback 全部交易
				throw new Exception("新增一筆轉帳發生錯誤");
			}
		}
	}

}
