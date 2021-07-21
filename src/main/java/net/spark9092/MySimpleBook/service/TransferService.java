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
import net.spark9092.MySimpleBook.dto.main.TransferListDto;
import net.spark9092.MySimpleBook.dto.main.TransferListMsgDto;
import net.spark9092.MySimpleBook.dto.transfer.CreateMsgDto;
import net.spark9092.MySimpleBook.dto.transfer.SelectAccountListDto;
import net.spark9092.MySimpleBook.dto.transfer.SelectAccountMsgDto;
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

	public SelectAccountMsgDto getAccountListByUserId(int userId) {

		logger.debug("使用者ID: {}", userId);

		SelectAccountMsgDto selectAccountMsgDto = new SelectAccountMsgDto();

		List<SelectAccountListDto> selectAccountListDtos = iTransferMapper.selectAccountListByUserId(userId);

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
			String transferDate = createPojo.getTransferDate();
			BigDecimal amnt = createPojo.getAmount();
			int tOutAccId = createPojo.gettOutAccId();
			int tInAccId = createPojo.gettInAccId();
			boolean outSideCheck = createPojo.isOutSideCheck();
			String tOutsideAccName = createPojo.gettOutsideAccName();
			String remark = createPojo.getRemark();

			if(!outSideCheck && tOutAccId == tInAccId) {

				createMsgDto.setStatus(false);
				createMsgDto.setMsg("無法在相同帳戶轉帳");
				return createMsgDto;
				
			}
			
			if(!checkCommon.checkAmnt(amnt)) {

				createMsgDto.setStatus(false);
				createMsgDto.setMsg("輸入的金額格式不正確");
				return createMsgDto;
				
			}
			
			boolean createTransferStatus = false;
			
			//檢查是否為外部帳戶轉帳
			if(outSideCheck) {
				
				//當轉帳是外部帳戶時
				createTransferStatus =  iTransferMapper.createOutsideByValues(
						userId, transferDate, amnt, tOutAccId, tOutsideAccName, remark);
			} else {
				
				//是一般轉帳
				createTransferStatus = iTransferMapper.createByValues(
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
					}

					createMsgDto.setStatus(true);
					createMsgDto.setMsg("");
					
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
	
	public TransferListMsgDto getTodayListForMain(int userId) {
		
		TransferListMsgDto transferListMsgDto = new TransferListMsgDto();
		
		List<TransferListDto> listDtos = iTransferMapper.getTodayListForMain(userId);
		
		if(listDtos.size() == 0) {
			
			transferListMsgDto.setStatus(false);
			transferListMsgDto.setMsg("今天沒有轉帳");
			
		} else {
			
			transferListMsgDto.setListDtos(listDtos);
			transferListMsgDto.setStatus(true);
			transferListMsgDto.setMsg("");
			
		}
		
		return transferListMsgDto;
	}

}
