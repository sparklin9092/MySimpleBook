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

import net.spark9092.MySimpleBook.dto.items.income.CreateMsgDto;
import net.spark9092.MySimpleBook.dto.items.income.DeleteMsgDto;
import net.spark9092.MySimpleBook.dto.items.income.ModifyMsgDto;
import net.spark9092.MySimpleBook.dto.items.income.OneMsgDto;
import net.spark9092.MySimpleBook.entity.UserInfoEntity;
import net.spark9092.MySimpleBook.enums.SessinNameEnum;
import net.spark9092.MySimpleBook.pojo.items.income.CreatePojo;
import net.spark9092.MySimpleBook.pojo.items.income.DeletePojo;
import net.spark9092.MySimpleBook.pojo.items.income.ModifyPojo;
import net.spark9092.MySimpleBook.service.IncomeItemsService;
import net.spark9092.MySimpleBook.service.UserLoginService;

@RequestMapping("/income/items")
@RestController
public class IncomeItemsController {

	private static final Logger logger = LoggerFactory.getLogger(UserLoginService.class);

	@Autowired
	private IncomeItemsService itemIncomeService;

	@PostMapping("/list")
	public List<List<String>> list(HttpSession session) {

		logger.debug("取得收入項目清單");

		List<List<String>> dataMap = new ArrayList<>();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if(null != userInfoEntity) {

			dataMap = itemIncomeService.getListByUserId(userInfoEntity.getId());
		}

		return dataMap;
	}

	@PostMapping("/one/{itemId}")
	public OneMsgDto one(HttpSession session, @PathVariable("itemId") int itemId) {

		logger.debug("取得某一筆收入項目資料");

		OneMsgDto oneMsgDto = new OneMsgDto();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if(null == userInfoEntity) {

			oneMsgDto.setStatus(false);
			oneMsgDto.setMsg("使用者未登入");

		} else {

			oneMsgDto = itemIncomeService.getOneByIds(userInfoEntity.getId(), itemId);
		}

		return oneMsgDto;
	}

	@PostMapping("/create/act")
	public CreateMsgDto createAct(HttpSession session, @RequestBody CreatePojo createPojo) {

		logger.debug("新增一筆收入項目");

		CreateMsgDto createMsgDto = new CreateMsgDto();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if(null == userInfoEntity) {

			createMsgDto.setStatus(false);
			createMsgDto.setMsg("使用者未登入");

		} else {

			createPojo.setUserId(userInfoEntity.getId());

			createMsgDto = itemIncomeService.createByPojo(createPojo);
		}

		return createMsgDto;
	}

	@PostMapping("/modify/act")
	public ModifyMsgDto modifyAct(HttpSession session, @RequestBody ModifyPojo modifyPojo) {

		logger.debug("修改一筆帳戶類型");

		ModifyMsgDto modifyMsgDto = new ModifyMsgDto();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if(null == userInfoEntity) {

			modifyMsgDto.setStatus(false);
			modifyMsgDto.setMsg("使用者未登入");

		} else {

			modifyPojo.setUserId(userInfoEntity.getId());

			modifyMsgDto = itemIncomeService.modifyByPojo(modifyPojo);
		}

		return modifyMsgDto;
	}

	@PostMapping("/delete/act")
	public DeleteMsgDto deleteAct(HttpSession session, @RequestBody DeletePojo deletePojo) {

		logger.debug("刪除一筆帳戶類型");

		DeleteMsgDto deleteMsgDto = new DeleteMsgDto();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if(null == userInfoEntity) {

			deleteMsgDto.setStatus(false);
			deleteMsgDto.setMsg("使用者未登入");

		} else {

			deletePojo.setUserId(userInfoEntity.getId());

			deleteMsgDto = itemIncomeService.deleteByPojo(deletePojo);
		}

		return deleteMsgDto;
	}

}
