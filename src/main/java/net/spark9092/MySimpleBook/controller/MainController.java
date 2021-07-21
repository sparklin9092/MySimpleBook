package net.spark9092.MySimpleBook.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import net.spark9092.MySimpleBook.dto.main.AccountListMsgDto;
import net.spark9092.MySimpleBook.dto.main.IncomeListMsgDto;
import net.spark9092.MySimpleBook.dto.main.SpendListMsgDto;
import net.spark9092.MySimpleBook.dto.main.TransferListMsgDto;
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

	@SuppressWarnings("unchecked")
	@PostMapping("/richCode/list")
    @ResponseBody
    public ListMsgDto getRishCodeList(HttpSession session) {

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
    @ResponseBody
    public TransferListMsgDto getTransferList(HttpSession session) {
		
		TransferListMsgDto transferListMsgDto = new TransferListMsgDto();

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
    @ResponseBody
    public IncomeListMsgDto getIncomeList(HttpSession session) {
		
		IncomeListMsgDto incomeListMsgDto = new IncomeListMsgDto();

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
    @ResponseBody
    public SpendListMsgDto getSpendList(HttpSession session) {
		
		SpendListMsgDto spendListMsgDto = new SpendListMsgDto();

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
    @ResponseBody
    public AccountListMsgDto getAccountList(HttpSession session) {
		
		AccountListMsgDto accountListMsgDto = new AccountListMsgDto();

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
