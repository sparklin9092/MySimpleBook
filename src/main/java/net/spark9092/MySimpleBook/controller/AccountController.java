package net.spark9092.MySimpleBook.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import net.spark9092.MySimpleBook.dto.AccountCreateMsgDto;
import net.spark9092.MySimpleBook.dto.AccountDeleteMsgDto;
import net.spark9092.MySimpleBook.dto.AccountModifyMsgDto;
import net.spark9092.MySimpleBook.dto.AccountOneMsgDto;
import net.spark9092.MySimpleBook.dto.AccountTypeListDto;
import net.spark9092.MySimpleBook.dto.AccountTypeListMsgDto;
import net.spark9092.MySimpleBook.entity.UserInfoEntity;
import net.spark9092.MySimpleBook.enums.SessinNameEnum;
import net.spark9092.MySimpleBook.pojo.AccountCreatePojo;
import net.spark9092.MySimpleBook.pojo.AccountDeletePojo;
import net.spark9092.MySimpleBook.pojo.AccountModifyPojo;
import net.spark9092.MySimpleBook.service.AccountService;

@RequestMapping("/account")
@RestController
public class AccountController {

	private static final Logger logger = LoggerFactory.getLogger(UserInfoController.class);

	@Autowired
	private AccountService accountService;

	@PostMapping("/list")
    @ResponseBody
	public List<List<String>> list(HttpSession session) {

		List<List<String>> dataMap = new ArrayList<>();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if(null != userInfoEntity) {

			logger.debug(userInfoEntity.toString());

			dataMap = accountService.getListByUserId(userInfoEntity.getId());
		}

		return dataMap;
	}

	@PostMapping("/typeList")
    @ResponseBody
	public AccountTypeListMsgDto typeList(HttpSession session) {

		AccountTypeListMsgDto accountTypeListMsgDto = new AccountTypeListMsgDto();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if(null == userInfoEntity) {

			accountTypeListMsgDto.setStatus(false);
			accountTypeListMsgDto.setMsg("使用者未登入");

		} else {

			logger.debug(userInfoEntity.toString());

			List<AccountTypeListDto> accountTypeListDto = accountService.getTypeListByUserId(userInfoEntity.getId());

			if(accountTypeListDto.size() == 0) {

				accountTypeListMsgDto.setStatus(false);
				accountTypeListMsgDto.setMsg("沒有找到帳戶類型，請先新增帳戶類型。");

			} else {

				accountTypeListMsgDto.setStatus(true);
				accountTypeListMsgDto.setMsg("");
				accountTypeListMsgDto.setAccountTypeListDto(accountTypeListDto);
			}
		}

		return accountTypeListMsgDto;
	}

	@PostMapping("/one/{accountId}")
    @ResponseBody
	public AccountOneMsgDto one(HttpSession session, @PathVariable("accountId") int accountId) {

		AccountOneMsgDto accountOneMsgDto = new AccountOneMsgDto();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if(null == userInfoEntity) {

			accountOneMsgDto.setStatus(false);
			accountOneMsgDto.setMsg("使用者未登入");

		} else {

			logger.debug(userInfoEntity.toString());

			int userId = userInfoEntity.getId();

			accountOneMsgDto = accountService.getOneByIds(userId, accountId);
		}

		return accountOneMsgDto;
	}

	@PostMapping("/create/act")
    @ResponseBody
	public AccountCreateMsgDto createAct(HttpSession session, @RequestBody AccountCreatePojo accountCreatePojo) {

		AccountCreateMsgDto accountCreateMsgDto = new AccountCreateMsgDto();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if(null == userInfoEntity) {

			accountCreateMsgDto.setStatus(false);
			accountCreateMsgDto.setMsg("使用者未登入");

		} else {

			logger.debug(userInfoEntity.toString());

			accountCreatePojo.setUserId(userInfoEntity.getId());

			boolean createStatus = accountService.createByPojo(accountCreatePojo);

			if(createStatus) {

				accountCreateMsgDto.setStatus(true);
				accountCreateMsgDto.setMsg("");

			} else {

				accountCreateMsgDto.setStatus(false);
				accountCreateMsgDto.setMsg("新增未成功");

			}
		}

		return accountCreateMsgDto;
	}

	@PostMapping("/modify/act")
    @ResponseBody
	public AccountModifyMsgDto modifyAct(HttpSession session, @RequestBody AccountModifyPojo accountModifyPojo) {

		AccountModifyMsgDto accountModifyMsgDto = new AccountModifyMsgDto();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if(null == userInfoEntity) {

			accountModifyMsgDto.setStatus(false);
			accountModifyMsgDto.setMsg("使用者未登入");

		} else {

			logger.debug(userInfoEntity.toString());

			accountModifyPojo.setUserId(userInfoEntity.getId());

			boolean modifyStatus = accountService.modifyByPojo(accountModifyPojo);

			if(modifyStatus) {

				accountModifyMsgDto.setStatus(true);
				accountModifyMsgDto.setMsg("");

			} else {

				accountModifyMsgDto.setStatus(false);
				accountModifyMsgDto.setMsg("修改未成功");

			}
		}

		return accountModifyMsgDto;
	}

	@PostMapping("/delete/act")
    @ResponseBody
	public AccountDeleteMsgDto deleteAct(HttpSession session, @RequestBody AccountDeletePojo accountDeletePojo) {

		AccountDeleteMsgDto accountDeleteMsgDto = new AccountDeleteMsgDto();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if(null == userInfoEntity) {

			accountDeleteMsgDto.setStatus(false);
			accountDeleteMsgDto.setMsg("使用者未登入");

		} else {

			logger.debug(userInfoEntity.toString());

			accountDeletePojo.setUserId(userInfoEntity.getId());

			boolean modifyStatus = accountService.deleteByPojo(accountDeletePojo);

			if(modifyStatus) {

				accountDeleteMsgDto.setStatus(true);
				accountDeleteMsgDto.setMsg("");

			} else {

				accountDeleteMsgDto.setStatus(false);
				accountDeleteMsgDto.setMsg("刪除未成功");

			}
		}

		return accountDeleteMsgDto;
	}

}
