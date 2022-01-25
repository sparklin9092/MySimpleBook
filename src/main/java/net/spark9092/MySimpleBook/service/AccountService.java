package net.spark9092.MySimpleBook.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.spark9092.MySimpleBook.common.CheckCommon;
import net.spark9092.MySimpleBook.common.GetCommon;
import net.spark9092.MySimpleBook.dto.account.CreateMsgDto;
import net.spark9092.MySimpleBook.dto.account.DeleteMsgDto;
import net.spark9092.MySimpleBook.dto.account.DetailListDto;
import net.spark9092.MySimpleBook.dto.account.DetailMsgDto;
import net.spark9092.MySimpleBook.dto.account.ListDto;
import net.spark9092.MySimpleBook.dto.account.ModifyMsgDto;
import net.spark9092.MySimpleBook.dto.account.OneDto;
import net.spark9092.MySimpleBook.dto.account.OneMsgDto;
import net.spark9092.MySimpleBook.dto.account.TypeListDto;
import net.spark9092.MySimpleBook.dto.account.TypeListMsgDto;
import net.spark9092.MySimpleBook.dto.main.AccRecordDto;
import net.spark9092.MySimpleBook.dto.main.AccRecMsgDto;
import net.spark9092.MySimpleBook.mapper.IAccountMapper;
import net.spark9092.MySimpleBook.pojo.account.CreatePojo;
import net.spark9092.MySimpleBook.pojo.account.DeletePojo;
import net.spark9092.MySimpleBook.pojo.account.DetailPojo;
import net.spark9092.MySimpleBook.pojo.account.ModifyPojo;

@Service
public class AccountService extends BaseService {

	private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

	@Autowired
	private IAccountMapper iAccountMapper;

	@Autowired
	private CheckCommon checkCommon;

	@Autowired
	private GetCommon getCommon;

	public AccountService() {
		logger.info("");
	}
	
	/**
	 * 根據使用者ID，取得帳戶的清單
	 * @param userId
	 * @return
	 */
	public List<List<String>> getListByUserId(int userId) {

		List<List<String>> dataList = new ArrayList<>();

		List<ListDto> listDtos = iAccountMapper.selectListByUserId(userId);

		if(listDtos.size() != 0) {

			listDtos.stream().forEach(dto -> {

				List<String> itemList = new ArrayList<>();
				itemList.add(String.valueOf(dto.getAccountId()));
				itemList.add(dto.getAccountName());
				itemList.add(getCommon.getNoZeroAmnt(decimalFormat.format(dto.getAccountAmnt())));
				itemList.add("");

				dataList.add(itemList);
			});
		}
		return dataList;
	}

	/**
	 * 根據使用者ID，取得帳戶類型的清單，用於下拉選單
	 * @param userId
	 * @return
	 */
	public TypeListMsgDto getTypeListByUserId(int userId) {

		TypeListMsgDto typeListMsgDto = new TypeListMsgDto();

		List<TypeListDto> typeListDtos = iAccountMapper.selectTypeListByUserId(userId);

		if(typeListDtos.size() == 0) {

			typeListMsgDto.setStatus(false);
			typeListMsgDto.setMsg("您似乎還沒有帳戶類型，先新增一個帳戶類型吧！");

		} else {

			typeListMsgDto.setStatus(true);
			typeListMsgDto.setMsg("");
			typeListMsgDto.setAccountTypeListDto(typeListDtos);
		}

		return typeListMsgDto;
	}

	/**
	 * 根據帳戶索引、使用者ID，取得某一筆帳戶資料
	 * @param userId
	 * @param accountId
	 * @return
	 */
	public OneMsgDto getOneByIds(int userId, int accountId) {

		OneMsgDto oneMsgDto = new OneMsgDto();

		OneDto oneDto = iAccountMapper.selectOneByIds(accountId, userId);

		if(null == oneDto) {

			oneMsgDto.setStatus(false);
			oneMsgDto.setMsg("似乎找不到您的資料，已將您的問題提報，請稍後再試試看。");

		} else {

			String fCreateDate = getCommon.getFormatDate(oneDto.getCreateDateTime());

			oneMsgDto.setStatus(true);
			oneMsgDto.setMsg("");
			oneMsgDto.setAccTypeName(oneDto.getAccountTypeName());
			oneMsgDto.setAccName(oneDto.getAccountName());
			oneMsgDto.setAccAmnt(getCommon.getNoZeroAmnt(decimalFormat.format(oneDto.getAccountAmnt())));
			oneMsgDto.setAccDefault(oneDto.isAccountDefault());
			oneMsgDto.setAccActive(oneDto.isAccountActive());
			oneMsgDto.setEnableLimitDate(oneDto.isEnableLimitDate());
			oneMsgDto.setLimitYear(oneDto.getLimitYear());
			oneMsgDto.setLimitMonth(oneDto.getLimitMonth());
			oneMsgDto.setCreateDateTime(fCreateDate);

		}

		return oneMsgDto;
	}

	/**
	 * 根據使用者ID，取得今天的帳戶資料
	 * @param userId
	 * @return
	 */
	public AccRecMsgDto getTodayListForMain(int userId) {

		AccRecMsgDto accountListMsgDto = new AccRecMsgDto();

		List<AccRecordDto> listDtos = iAccountMapper.selectTodayListForMain(userId);

		if(listDtos.size() == 0) {

			accountListMsgDto.setStatus(false);
			accountListMsgDto.setMsg("還沒有帳戶嗎？去新增一個吧！");

		} else {
			//處理金額格式化
			listDtos.stream().forEach(dto -> {
				dto.setAmnt(getCommon.getNoZeroAmnt(decimalFormat.format(new BigDecimal(dto.getAmnt()))));
			});

			accountListMsgDto.setListDtos(listDtos);
			accountListMsgDto.setStatus(true);
			accountListMsgDto.setMsg("");

		}

		return accountListMsgDto;
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
			if(!checkCommon.checkData(limitDate)) limitDate = null;
		}

		return limitDate;
	}

	/**
	 * 增加一筆帳戶
	 * @param createPojo
	 * @return
	 */
	public CreateMsgDto createByPojo(CreatePojo createPojo) {

		CreateMsgDto createMsgDto = new CreateMsgDto();

		if(null == createPojo) {

			createMsgDto.setStatus(false);
			createMsgDto.setMsg("您似乎沒有資料可以新增，再檢查看看還有什麼欄位沒有填寫的。");

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
				createMsgDto.setMsg("您輸入的金額格式不正確，再檢查看看。");
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
				createMsgDto.setMsg("沒有成功新增帳戶，已將您的問題提報，請稍後再試試看。");

			}
		}

		return createMsgDto;
	}

	/**
	 * 修改一筆帳戶
	 * @param modifyPojo
	 * @return
	 */
	public ModifyMsgDto modifyByPojo(ModifyPojo modifyPojo) {

		ModifyMsgDto modifyMsgDto = new ModifyMsgDto();

		if(null == modifyPojo) {

			modifyMsgDto.setStatus(false);
			modifyMsgDto.setMsg("您似乎沒有可以修改的資料，再檢查看看還有什麼欄位沒有填寫的。");

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
				modifyMsgDto.setMsg("沒有成功修改帳戶，已將您的問題提報，請稍後再試試看。");

			}
		}

		return modifyMsgDto;
	}

	/**
	 * 刪除某一筆帳戶資料
	 * @param deletePojo
	 * @return
	 */
	public DeleteMsgDto deleteByPojo(DeletePojo deletePojo) {

		DeleteMsgDto deleteMsgDto = new DeleteMsgDto();

		if(null == deletePojo) {

			deleteMsgDto.setStatus(false);
			deleteMsgDto.setMsg("您似乎沒有可以刪除的資料，再檢查看看還有什麼欄位沒有填寫的。");

		} else {

			int userId = deletePojo.getUserId();
			int accountId = deletePojo.getAccountId();

			boolean deleteStatus = iAccountMapper.deleteByIds(userId, accountId);

			if(deleteStatus) {

				deleteMsgDto.setStatus(true);
				deleteMsgDto.setMsg("");

			} else {

				deleteMsgDto.setStatus(false);
				deleteMsgDto.setMsg("沒有成功刪除帳戶，已將您的問題提報，請稍後再試試看。");

			}
		}

		return deleteMsgDto;
	}

	/**
	 * 查詢帳戶的收支轉明細
	 * @param detailPojo
	 * @return
	 */
	public DetailMsgDto getDetailListByUserId(DetailPojo detailPojo) {
		DetailMsgDto msgDto = new DetailMsgDto();
		OneDto oneDto = iAccountMapper.selectOneByIds(detailPojo.getAccountId(), detailPojo.getUserId());
		if(null == oneDto) {
			msgDto.setStatus(false);
			msgDto.setMsg("找不到帳戶");
		} else {
			msgDto.setStatus(true);
			msgDto.setMsg("");
			msgDto.setAccName(oneDto.getAccountName());
			msgDto.setTypeName(oneDto.getAccountTypeName());
			msgDto.setAccAmnt(getCommon.getNoZeroAmnt(decimalFormat.format(oneDto.getAccountAmnt())));
			
			List<List<String>> dataList = new ArrayList<>();
			List<DetailListDto> listDtos = iAccountMapper.selectDetailListByIds(detailPojo.getUserId(), detailPojo.getAccountId(), detailPojo.getStartDate(), detailPojo.getEndDate());
			if(listDtos.size() != 0) {
				listDtos.stream().forEach(dto -> {
					List<String> datail = new ArrayList<>();
					datail.add(dto.getItemId()); //明細 ID，可能是收支轉任一個的索引
					datail.add(""); //功能按鈕
					datail.add(dto.getDate()); //發生日期
					datail.add(dto.getItemName()); //項目名稱
					datail.add(getCommon.getNoZeroAmnt(decimalFormat.format(dto.getAmnt()))); //金額
					datail.add(dto.getRemark()); //備註
					datail.add(dto.getType()); //項目類型，s: 支出，i: 收入，t: 轉帳，用於顯示金額顏色
					dataList.add(datail);
				});
			}
			msgDto.setDetails(dataList);
		}
		return msgDto;
	}

}
