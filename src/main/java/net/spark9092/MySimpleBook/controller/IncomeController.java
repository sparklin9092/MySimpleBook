package net.spark9092.MySimpleBook.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import net.spark9092.MySimpleBook.dto.income.CreateMsgDto;
import net.spark9092.MySimpleBook.dto.income.SelectAccountListDto;
import net.spark9092.MySimpleBook.dto.income.SelectAccountMsgDto;
import net.spark9092.MySimpleBook.dto.income.SelectItemListDto;
import net.spark9092.MySimpleBook.dto.income.SelectItemMsgDto;
import net.spark9092.MySimpleBook.entity.UserInfoEntity;
import net.spark9092.MySimpleBook.enums.SessinNameEnum;
import net.spark9092.MySimpleBook.pojo.income.CreatePojo;
import net.spark9092.MySimpleBook.service.IncomeService;
import net.spark9092.MySimpleBook.service.UserLoginService;

@RequestMapping("/income")
@RestController
public class IncomeController {

	private static final Logger logger = LoggerFactory.getLogger(UserLoginService.class);

	@Autowired
	private IncomeService incomeService;

	@PostMapping("/itemList")
    @ResponseBody
	public SelectItemMsgDto incomeItemList(HttpSession session) {
		
		logger.debug("取得支出項目的下拉選單");

		SelectItemMsgDto selectItemMsgDto = new SelectItemMsgDto();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if(null == userInfoEntity) {

			selectItemMsgDto.setStatus(false);
			selectItemMsgDto.setMsg("使用者未登入");

		} else {

			List<SelectItemListDto> incomeItemListDto = incomeService.getIncomeListByUserId(userInfoEntity.getId());

			if(incomeItemListDto.size() == 0) {

				selectItemMsgDto.setStatus(false);
				selectItemMsgDto.setMsg("沒有可以使用的收入項目，請先新增或啟用收入項目。");

			} else {

				selectItemMsgDto.setStatus(true);
				selectItemMsgDto.setMsg("");
				selectItemMsgDto.setItemList(incomeItemListDto);
			}
		}

		return selectItemMsgDto;
	}

	@PostMapping("/accountList")
    @ResponseBody
	public SelectAccountMsgDto accountList(HttpSession session) {
		
		logger.debug("取得帳戶的下拉選單");

		SelectAccountMsgDto selectAccountMsgDto = new SelectAccountMsgDto();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if(null == userInfoEntity) {

			selectAccountMsgDto.setStatus(false);
			selectAccountMsgDto.setMsg("使用者未登入");

		} else {

			List<SelectAccountListDto> selectAccountListDtos = incomeService.getAccountListByUserId(userInfoEntity.getId());

			if(selectAccountListDtos.size() == 0) {

				selectAccountMsgDto.setStatus(false);
				selectAccountMsgDto.setMsg("沒有可以使用的帳戶，請先新增或啟用帳戶。");

			} else {

				selectAccountMsgDto.setStatus(true);
				selectAccountMsgDto.setMsg("");
				selectAccountMsgDto.setAccountList(selectAccountListDtos);
			}
		}

		return selectAccountMsgDto;
	}

	@PostMapping("/create/act")
    @ResponseBody
	public CreateMsgDto createAct(HttpSession session, @RequestBody CreatePojo createPojo) {

		CreateMsgDto createMsgDto = new CreateMsgDto();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if(null == userInfoEntity) {

			createMsgDto.setStatus(false);
			createMsgDto.setMsg("使用者未登入");

		} else {

			createPojo.setUserId(userInfoEntity.getId());

			try {
				boolean createStatus = incomeService.createByPojo(createPojo);

				if(createStatus) {

					createMsgDto.setStatus(true);
					createMsgDto.setMsg("");

				} else {

					createMsgDto.setStatus(false);
					createMsgDto.setMsg("新增未成功");

				}
			} catch (Exception ex) {

				createMsgDto.setStatus(false);
				createMsgDto.setMsg("新增一筆支出發生錯誤，請再重新操作一次。");
			}
		}

		return createMsgDto;
	}

}