package net.spark9092.MySimpleBook.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.spark9092.MySimpleBook.common.CheckCommon;
import net.spark9092.MySimpleBook.dto.account.CreateMsgDto;
import net.spark9092.MySimpleBook.dto.account.DeleteMsgDto;
import net.spark9092.MySimpleBook.dto.account.ListDto;
import net.spark9092.MySimpleBook.dto.account.ModifyMsgDto;
import net.spark9092.MySimpleBook.dto.account.OneDto;
import net.spark9092.MySimpleBook.dto.account.OneMsgDto;
import net.spark9092.MySimpleBook.dto.account.TypeListDto;
import net.spark9092.MySimpleBook.dto.account.TypeListMsgDto;
import net.spark9092.MySimpleBook.dto.main.AccountListDto;
import net.spark9092.MySimpleBook.dto.main.AccountListMsgDto;
import net.spark9092.MySimpleBook.mapper.IAccountMapper;
import net.spark9092.MySimpleBook.pojo.account.CreatePojo;
import net.spark9092.MySimpleBook.pojo.account.DeletePojo;
import net.spark9092.MySimpleBook.pojo.account.ModifyPojo;

@Service
public class AccountService {

	private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

	private static final String limitDateRegex = "[0-9]{4}-[0-9]{2}-[0-9]{2}";

	@Autowired
	private CheckCommon checkCommon;

	@Autowired
	private IAccountMapper iAccountMapper;

	public List<List<String>> getListByUserId(int userId) {

		logger.debug("使用者ID: " + userId);

		List<List<String>> dataList = new ArrayList<>();

		List<ListDto> listDtos = iAccountMapper.selectListByUserId(userId);

		if(listDtos.size() != 0) {

			listDtos.stream().forEach(dto -> {

				List<String> itemList = new ArrayList<>();
				itemList.add(String.valueOf(dto.getAccountId()));
				itemList.add(dto.getAccountName());
				itemList.add(dto.getAccountAmnt().toString());
				itemList.add("");

				dataList.add(itemList);
			});
		}
		return dataList;
	}

	public TypeListMsgDto getTypeListByUserId(int userId) {

		logger.debug("使用者ID: {}", userId);
		
		TypeListMsgDto typeListMsgDto = new TypeListMsgDto();
		
		List<TypeListDto> typeListDtos = iAccountMapper.selectTypeListByUserId(userId);

		if(typeListDtos.size() == 0) {

			typeListMsgDto.setStatus(false);
			typeListMsgDto.setMsg("沒有找到帳戶類型，請先新增帳戶類型。");

		} else {

			typeListMsgDto.setStatus(true);
			typeListMsgDto.setMsg("");
			typeListMsgDto.setAccountTypeListDto(typeListDtos);
		}

		return typeListMsgDto;
	}

	public OneMsgDto getOneByIds(int userId, int accountId) {

		OneMsgDto oneMsgDto = new OneMsgDto();

		OneDto oneDto = iAccountMapper.selectOneByIds(accountId, userId);

		if(null == oneDto) {

			oneMsgDto.setStatus(false);
			oneMsgDto.setMsg("找不到資料");

			logger.error(String.format("查詢某一筆帳戶時，找不到資料。User ID: %d、Account ID: %d",
					userId, accountId));

		} else {

			oneMsgDto.setStatus(true);
			oneMsgDto.setMsg("");
			oneMsgDto.setAccountOneDto(oneDto);

		}

		return oneMsgDto;
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

	public CreateMsgDto createByPojo(CreatePojo createPojo) {
		
		CreateMsgDto createMsgDto = new CreateMsgDto();

		if(null == createPojo) {

			createMsgDto.setStatus(false);
			createMsgDto.setMsg("沒有可以新增的資料");

		} else {

			int userId = createPojo.getUserId();
			int accountType = createPojo.getAccountType();
			String accountName = createPojo.getAccountName();
			BigDecimal initAmnt = createPojo.getInitAmnt();
			String accountDefaultStr = createPojo.getAccountDefault();
			boolean enableLimitDate = createPojo.isEnableLimitDate();
			String limitYear = createPojo.getLimitYear();
			String limitMonth = createPojo.getLimitMonth();
			String limitDate = null;
			
			if(!checkCommon.checkAmnt(initAmnt)) {

				createMsgDto.setStatus(false);
				createMsgDto.setMsg("輸入的金額格式不正確");
				return createMsgDto;
				
			}

			boolean accountDefault = false;
			if(accountDefaultStr.equals("1")) {
				accountDefault = true;
			}

			limitDate = this.formatLimitDate(enableLimitDate, limitMonth, limitYear);

			boolean createStatus = iAccountMapper.insertByValues(userId, accountType, accountName,
					initAmnt, accountDefault, limitDate);


			if(createStatus) {

				createMsgDto.setStatus(true);
				createMsgDto.setMsg("");

			} else {

				createMsgDto.setStatus(false);
				createMsgDto.setMsg("新增未成功");

			}
		}
		
		return createMsgDto;
	}

	public ModifyMsgDto modifyByPojo(ModifyPojo modifyPojo) {

		ModifyMsgDto modifyMsgDto = new ModifyMsgDto();
		
		if(null == modifyPojo) {

			modifyMsgDto.setStatus(false);
			modifyMsgDto.setMsg("沒有可以修改的資料");

		} else {

			int userId = modifyPojo.getUserId();
			int accountId = modifyPojo.getAccountId();
			String accountName = modifyPojo.getAccountName();
			String accountDefaultStr = modifyPojo.getAccountDefault();
			String accountActiveStr = modifyPojo.getAccountActive();
			boolean enableLimitDate = modifyPojo.isEnableLimitDate();
			String limitYear = modifyPojo.getLimitYear();
			String limitMonth = modifyPojo.getLimitMonth();
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

			boolean modifyStatus = iAccountMapper.updateByValues(
					userId, accountId, accountName, limitDate, accountDefault, accountActive);

			if(modifyStatus) {

				modifyMsgDto.setStatus(true);
				modifyMsgDto.setMsg("");

			} else {

				modifyMsgDto.setStatus(false);
				modifyMsgDto.setMsg("修改未成功");

			}
		}
		
		return modifyMsgDto;
	}

	public DeleteMsgDto deleteByPojo(DeletePojo deletePojo) {

		DeleteMsgDto deleteMsgDto = new DeleteMsgDto();
		
		if(null == deletePojo) {

			deleteMsgDto.setStatus(false);
			deleteMsgDto.setMsg("沒有可以刪除的資料");

		} else {

			int userId = deletePojo.getUserId();
			int accountId = deletePojo.getAccountId();

			boolean deleteStatus = iAccountMapper.deleteByIds(userId, accountId);

			if(deleteStatus) {

				deleteMsgDto.setStatus(true);
				deleteMsgDto.setMsg("");

			} else {

				deleteMsgDto.setStatus(false);
				deleteMsgDto.setMsg("刪除未成功");

			}
		}
		
		return deleteMsgDto;
	}

	public AccountListMsgDto getTodayListForMain(int userId) {

		AccountListMsgDto accountListMsgDto = new AccountListMsgDto();

		List<AccountListDto> listDtos = iAccountMapper.selectTodayListForMain(userId);

		if(listDtos.size() == 0) {

			accountListMsgDto.setStatus(false);
			accountListMsgDto.setMsg("還沒有帳戶嗎？去新增一個吧！");

		} else {

			accountListMsgDto.setListDtos(listDtos);
			accountListMsgDto.setStatus(true);
			accountListMsgDto.setMsg("");

		}

		return accountListMsgDto;
	}

}
