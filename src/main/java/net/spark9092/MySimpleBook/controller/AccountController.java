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
import org.springframework.web.bind.annotation.RestController;

import net.spark9092.MySimpleBook.dto.account.CreateMsgDto;
import net.spark9092.MySimpleBook.dto.account.DeleteMsgDto;
import net.spark9092.MySimpleBook.dto.account.ModifyMsgDto;
import net.spark9092.MySimpleBook.dto.account.OneMsgDto;
import net.spark9092.MySimpleBook.dto.account.TypeListMsgDto;
import net.spark9092.MySimpleBook.entity.UserInfoEntity;
import net.spark9092.MySimpleBook.enums.SessinNameEnum;
import net.spark9092.MySimpleBook.pojo.account.CreatePojo;
import net.spark9092.MySimpleBook.pojo.account.DeletePojo;
import net.spark9092.MySimpleBook.pojo.account.ModifyPojo;
import net.spark9092.MySimpleBook.service.AccountService;

@RequestMapping("/account")
@RestController
public class AccountController {

	private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

	@Autowired
	private AccountService accountService;

	@PostMapping("/list")
	public List<List<String>> list(HttpSession session) {

		logger.debug("取得帳戶清單");

		List<List<String>> dataMap = new ArrayList<>();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if(null != userInfoEntity) {

			dataMap = accountService.getListByUserId(userInfoEntity.getId());
		}

		return dataMap;
	}

	@PostMapping("/typeList")
	public TypeListMsgDto typeList(HttpSession session) {

		logger.debug("取得帳戶類型清單");

		TypeListMsgDto typeListMsgDto = new TypeListMsgDto();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if(null == userInfoEntity) {

			typeListMsgDto.setStatus(false);
			typeListMsgDto.setMsg("使用者未登入");

		} else {

			typeListMsgDto = accountService.getTypeListByUserId(userInfoEntity.getId());
		}

		return typeListMsgDto;
	}

	@PostMapping("/one/{accountId}")
	public OneMsgDto one(HttpSession session, @PathVariable("accountId") int accountId) {

		logger.debug("取得某一筆帳戶資料");

		OneMsgDto oneMsgDto = new OneMsgDto();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if(null == userInfoEntity) {

			oneMsgDto.setStatus(false);
			oneMsgDto.setMsg("使用者未登入");

		} else {

			oneMsgDto = accountService.getOneByIds(userInfoEntity.getId(), accountId);
		}

		return oneMsgDto;
	}

	@PostMapping("/create/act")
	public CreateMsgDto createAct(HttpSession session, @RequestBody CreatePojo createPojo) {

		logger.debug("新增一筆帳戶");

		CreateMsgDto createMsgDto = new CreateMsgDto();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if(null == userInfoEntity) {

			createMsgDto.setStatus(false);
			createMsgDto.setMsg("使用者未登入");

		} else {

			createPojo.setUserId(userInfoEntity.getId());

			createMsgDto = accountService.createByPojo(createPojo);
		}

		return createMsgDto;
	}

	@PostMapping("/modify/act")
	public ModifyMsgDto modifyAct(HttpSession session, @RequestBody ModifyPojo modifyPojo) {

		logger.debug("更新一筆帳戶");

		ModifyMsgDto modifyMsgDto = new ModifyMsgDto();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if(null == userInfoEntity) {

			modifyMsgDto.setStatus(false);
			modifyMsgDto.setMsg("使用者未登入");

		} else {

			modifyPojo.setUserId(userInfoEntity.getId());

			modifyMsgDto = accountService.modifyByPojo(modifyPojo);
		}

		return modifyMsgDto;
	}

	@PostMapping("/delete/act")
	public DeleteMsgDto deleteAct(HttpSession session, @RequestBody DeletePojo deletePojo) {

		logger.debug("刪除一筆帳戶");

		DeleteMsgDto deleteMsgDto = new DeleteMsgDto();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if(null == userInfoEntity) {

			deleteMsgDto.setStatus(false);
			deleteMsgDto.setMsg("使用者未登入");

		} else {

			deletePojo.setUserId(userInfoEntity.getId());

			deleteMsgDto = accountService.deleteByPojo(deletePojo);
		}

		return deleteMsgDto;
	}

}
