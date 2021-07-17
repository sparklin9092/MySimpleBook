package net.spark9092.MySimpleBook.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.spark9092.MySimpleBook.dto.AccountListDto;
import net.spark9092.MySimpleBook.dto.AccountOneDto;
import net.spark9092.MySimpleBook.dto.AccountOneMsgDto;
import net.spark9092.MySimpleBook.dto.AccountTypeListDto;
import net.spark9092.MySimpleBook.mapper.IAccountMapper;
import net.spark9092.MySimpleBook.pojo.AccountCreatePojo;
import net.spark9092.MySimpleBook.pojo.AccountDeletePojo;
import net.spark9092.MySimpleBook.pojo.AccountModifyPojo;

@Service
public class AccountService {

	private static final Logger logger = LoggerFactory.getLogger(UserLoginService.class);

	private static final String limitDateRegex = "[0-9]{4}-[0-9]{2}-[0-9]{2}";

	@Autowired
	private IAccountMapper iAccountMapper;

	public List<List<String>> getListByUserId(int userId) {

		logger.debug("使用者ID: " + userId);

		List<List<String>> dataList = new ArrayList<>();

		List<AccountListDto> accountListDto = iAccountMapper.selectListByUserId(userId);

		if(accountListDto.size() != 0) {

			accountListDto.stream().forEach(dto -> {

				String itemActive = "停用";
				if(dto.isItemActive()) {
					itemActive = "啟用";
				}

				List<String> itemList = new ArrayList<>();
				itemList.add(String.valueOf(dto.getAccountId()));
				itemList.add(dto.getTypeName());
				itemList.add(dto.getAccountName());
				itemList.add(itemActive);
				itemList.add("");

				dataList.add(itemList);
			});
		}
		return dataList;
	}

	public List<AccountTypeListDto> getTypeListByUserId(int userId) {

		logger.debug("使用者ID: {}", userId);

		return iAccountMapper.selectTypeListByUserId(userId);
	}

	public AccountOneMsgDto getOneByIds(int userId, int accountId) {

		AccountOneMsgDto accountOneMsgDto = new AccountOneMsgDto();

		AccountOneDto accountOneDto = iAccountMapper.selectOneByIds(accountId, userId);

		if(null == accountOneDto) {

			accountOneMsgDto.setStatus(false);
			accountOneMsgDto.setMsg("找不到資料");

			logger.error(String.format("查詢某一筆帳戶時，找不到資料。User ID: %d、Account ID: %d",
					userId, accountId));

		} else {

			accountOneMsgDto.setStatus(true);
			accountOneMsgDto.setMsg("");
			accountOneMsgDto.setAccountOneDto(accountOneDto);

		}

		return accountOneMsgDto;
	}
	
	private String formatLimitDate(boolean enableLimitDate, String limitMonth, String limitYear) {
		
		String limitDateEndDay = "";
		String limitDate = null;
		
		if(enableLimitDate) {

			switch (limitMonth) {
			case "02":
				int limitYearInt = Integer.valueOf(limitYear);
				//計算閏年
				if(((limitYearInt % 4) == 0 && (limitYearInt % 100) != 0) || (limitYearInt % 400) == 0) {
					limitDateEndDay = "29";
				} else {
					limitDateEndDay = "28";
				}
				break;
			case "01":
			case "03":
			case "05":
			case "07":
			case "08":
			case "10":
			case "12":
				limitDateEndDay = "31";
				break;
			default:
				limitDateEndDay = "30";
				break;
			}

			limitDate = limitYear + "-" + limitMonth + "-" + limitDateEndDay;

			//最後組合完日期，進行正規化驗證，如果不合格，那就不要寫入資料庫
			if(!limitDate.matches(limitDateRegex)) limitDate = null;
		}
		
		return limitDate;
	}

	public boolean createByPojo(AccountCreatePojo accountCreatePojo) {

		if(null == accountCreatePojo) {

			return false;

		} else {

			int userId = accountCreatePojo.getUserId();
			int accountType = accountCreatePojo.getAccountType();
			String accountName = accountCreatePojo.getAccountName();
			String accountDefaultStr = accountCreatePojo.getAccountDefault();
			boolean enableLimitDate = accountCreatePojo.isEnableLimitDate();
			String limitYear = accountCreatePojo.getLimitYear();
			String limitMonth = accountCreatePojo.getLimitMonth();
			String limitDate = null;

			boolean accountDefault = false;
			if(accountDefaultStr.equals("1")) {
				accountDefault = true;
			}

			limitDate = this.formatLimitDate(enableLimitDate, limitMonth, limitYear);

			return iAccountMapper.createByValues(userId, accountType, accountName,
					accountDefault, limitDate);
		}
	}

	public boolean modifyByPojo(AccountModifyPojo accountModifyPojo) {

		if(null == accountModifyPojo) {

			return false;

		} else {

			int userId = accountModifyPojo.getUserId();
			int accountId = accountModifyPojo.getAccountId();
			int accountType = accountModifyPojo.getAccountType();
			String accountName = accountModifyPojo.getAccountName();
			String accountDefaultStr = accountModifyPojo.getAccountDefault();
			String accountActiveStr = accountModifyPojo.getAccountActive();
			boolean enableLimitDate = accountModifyPojo.isEnableLimitDate();
			String limitYear = accountModifyPojo.getLimitYear();
			String limitMonth = accountModifyPojo.getLimitMonth();
			String limitDate = null;

			boolean accountDefault = false;
			if(accountDefaultStr.equals("1")) {
				accountDefault = true;
			}

			boolean accountActive = false;
			if(accountActiveStr.equals("1")) {
				accountActive = true;
			}

			limitDate = this.formatLimitDate(enableLimitDate, limitMonth, limitYear);

			return iAccountMapper.modifyByValues(
					userId, accountId, accountType, accountName, limitDate, 
					accountDefault, accountActive);
		}
	}

	public boolean deleteByPojo(AccountDeletePojo accountDeletePojo) {

		if(null == accountDeletePojo) {

			return false;

		} else {

			int userId = accountDeletePojo.getUserId();
			int accountId = accountDeletePojo.getAccountId();

			return iAccountMapper.deleteByIds(userId, accountId);
		}
	}

}
