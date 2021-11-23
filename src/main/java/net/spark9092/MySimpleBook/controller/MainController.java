package net.spark9092.MySimpleBook.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.spark9092.MySimpleBook.dto.main.AccRecMsgDto;
import net.spark9092.MySimpleBook.dto.main.CheckGuestMsgDto;
import net.spark9092.MySimpleBook.dto.main.IncomeRecMsgDto;
import net.spark9092.MySimpleBook.dto.main.SpendRecMsgDto;
import net.spark9092.MySimpleBook.dto.main.TransRecMsgDto;
import net.spark9092.MySimpleBook.dto.richCode.ListDto;
import net.spark9092.MySimpleBook.dto.richCode.ListMsgDto;
import net.spark9092.MySimpleBook.entity.UserInfoEntity;
import net.spark9092.MySimpleBook.enums.SessinNameEnum;
import net.spark9092.MySimpleBook.service.AccountService;
import net.spark9092.MySimpleBook.service.IncomeService;
import net.spark9092.MySimpleBook.service.RichCodeService;
import net.spark9092.MySimpleBook.service.SpendService;
import net.spark9092.MySimpleBook.service.TransferService;

@RequestMapping("/main")
@RestController
public class MainController {

	@Autowired
	private RichCodeService richCodeService;

	@Autowired
	private TransferService transferService;

	@Autowired
	private IncomeService incomeService;

	@Autowired
	private SpendService spendService;

	@Autowired
	private AccountService accountService;
	
	@PostMapping("/check/guest")
	public CheckGuestMsgDto checkGuest(HttpSession session) {
		
		CheckGuestMsgDto checkGuestMsgDto = new CheckGuestMsgDto();
		
		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());
		
		if(userInfoEntity.isGuest()) {
			
			checkGuestMsgDto.setStatus(true);
			
		} else {
			
			checkGuestMsgDto.setStatus(false);
			
		}
		
		return checkGuestMsgDto;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/richCode/list")
    public ListMsgDto getRishCodeList(HttpSession session) {
		
		try {
			//對前端請求進行延遲處理，避免過度刷新，導致IO量太大
			TimeUnit.MILLISECONDS.sleep(100); //延遲0.1秒
		} catch (InterruptedException e) {
			//如果延遲出錯也沒關係
		}

		ListMsgDto listMsgDto = new ListMsgDto();
		List<ListDto> dtos = new ArrayList<>();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if(null == userInfoEntity) {

			listMsgDto.setStatus(false);
			listMsgDto.setMsg("使用者未登入");

		} else {

			dtos = (List<ListDto>) session.getAttribute(SessinNameEnum.RICH_CODE.getName());

			if(null == dtos || dtos.size() == 0) {

				listMsgDto = richCodeService.getRichCodeList();

				session.setAttribute(SessinNameEnum.RICH_CODE.getName(), dtos);

			} else {

				listMsgDto.setListDtos(dtos);
				listMsgDto.setStatus(true);
			}

		}

		return listMsgDto;
	}

	@PostMapping("/transfer/list")
    public TransRecMsgDto getTransferList(HttpSession session) {
		
		try {
			//對前端請求進行延遲處理，避免過度刷新，導致IO量太大
			TimeUnit.MILLISECONDS.sleep(100); //延遲0.1秒
		} catch (InterruptedException e) {
			//如果延遲出錯也沒關係
		}

		TransRecMsgDto transferListMsgDto = new TransRecMsgDto();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if(null == userInfoEntity) {

			transferListMsgDto.setStatus(false);
			transferListMsgDto.setMsg("使用者未登入");

		} else {

			transferListMsgDto = transferService.getTodayListForMain(userInfoEntity.getId());

		}

		return transferListMsgDto;
	}

	@PostMapping("/income/list")
    public IncomeRecMsgDto getIncomeList(HttpSession session) {
		
		try {
			//對前端請求進行延遲處理，避免過度刷新，導致IO量太大
			TimeUnit.MILLISECONDS.sleep(100); //延遲0.1秒
		} catch (InterruptedException e) {
			//如果延遲出錯也沒關係
		}

		IncomeRecMsgDto incomeListMsgDto = new IncomeRecMsgDto();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if(null == userInfoEntity) {

			incomeListMsgDto.setStatus(false);
			incomeListMsgDto.setMsg("使用者未登入");

		} else {

			incomeListMsgDto = incomeService.getTodayListForMain(userInfoEntity.getId());

		}

		return incomeListMsgDto;
	}

	@PostMapping("/spend/list")
    public SpendRecMsgDto getSpendList(HttpSession session) {
		
		try {
			//對前端請求進行延遲處理，避免過度刷新，導致IO量太大
			TimeUnit.MILLISECONDS.sleep(100); //延遲0.1秒
		} catch (InterruptedException e) {
			//如果延遲出錯也沒關係
		}

		SpendRecMsgDto spendListMsgDto = new SpendRecMsgDto();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if(null == userInfoEntity) {

			spendListMsgDto.setStatus(false);
			spendListMsgDto.setMsg("使用者未登入");

		} else {

			spendListMsgDto = spendService.getTodayListForMain(userInfoEntity.getId());

		}

		return spendListMsgDto;
	}

	@PostMapping("/account/list")
    public AccRecMsgDto getAccountList(HttpSession session) {
		
		try {
			//對前端請求進行延遲處理，避免過度刷新，導致IO量太大
			TimeUnit.MILLISECONDS.sleep(300); //延遲0.3秒
		} catch (InterruptedException e) {
			//如果延遲出錯也沒關係
		}

		AccRecMsgDto accountListMsgDto = new AccRecMsgDto();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if(null == userInfoEntity) {

			accountListMsgDto.setStatus(false);
			accountListMsgDto.setMsg("使用者未登入");

		} else {

			accountListMsgDto = accountService.getTodayListForMain(userInfoEntity.getId());

		}

		return accountListMsgDto;
	}
}
