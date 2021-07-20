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
			boolean outSideAccCheck = createPojo.isOutSideAccCheck();
			int tInAccId = createPojo.gettInAccId();
			BigDecimal tInAmnt = tOutAmnt;
			String tOutsideAccName = createPojo.gettOutsideAccName();
			String remark = createPojo.getRemark();

			if(!outSideAccCheck && tOutAccId == tInAccId) return false;
			
			if(!checkCommon.checkAmnt(tOutAmnt)) return false;
			
			boolean createTransferStatus = false;
			
			//檢查是否為外部帳戶轉帳
			if(outSideAccCheck) {
				
				//當轉帳是外部帳戶時
				createTransferStatus =  iTransferMapper.createOutsideByValues(
					userId, transferDate, tOutAccId, tOutAmnt,
					tInAmnt, outSideAccCheck, tOutsideAccName, remark);
			} else {
				
				//是一般轉帳
				createTransferStatus = iTransferMapper.createByValues(
					userId, transferDate, tOutAccId, tOutAmnt,
					tInAccId, tInAmnt, remark);
			}
			
			if(createTransferStatus) {
				
				boolean decreaseAmntStatus = iAccountMapper.decreaseAmnt(userId, tOutAccId, tOutAmnt);
				
				if(decreaseAmntStatus) {
					
					//如果是一般轉帳才會餘額才會增加，外部轉帳不會
					if(!outSideAccCheck) {
						
						boolean increaseAmntStatus = iAccountMapper.increaseAmnt(userId, tInAccId, tInAmnt);
						
						if(increaseAmntStatus) {
							
							return true;
							
						} else {
							
							//當  [增加]  帳戶餘額發生錯誤時，rollback 全部交易
							throw new Exception("增加帳戶餘額發生錯誤");
						}
					}
					
					return true;
					
				} else {
					
					//當  [減少]  帳戶餘額發生錯誤時，rollback 全部交易
					throw new Exception("減少帳戶餘額發生錯誤");
				}
			} else {
				
				//當新增一筆轉帳發生錯誤時，rollback 全部交易
				throw new Exception("新增一筆轉帳發生錯誤");
			}
		}
	}

}
