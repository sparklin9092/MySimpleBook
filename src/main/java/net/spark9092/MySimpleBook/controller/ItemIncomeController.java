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

import net.spark9092.MySimpleBook.dto.ItemIncomeCreateMsgDto;
import net.spark9092.MySimpleBook.dto.ItemIncomeDeleteMsgDto;
import net.spark9092.MySimpleBook.dto.ItemIncomeModifyMsgDto;
import net.spark9092.MySimpleBook.dto.ItemIncomeOneMsgDto;
import net.spark9092.MySimpleBook.entity.UserInfoEntity;
import net.spark9092.MySimpleBook.enums.SessinNameEnum;
import net.spark9092.MySimpleBook.pojo.ItemIncomeCreatePojo;
import net.spark9092.MySimpleBook.pojo.ItemIncomeDeletePojo;
import net.spark9092.MySimpleBook.pojo.ItemIncomeModifyPojo;
import net.spark9092.MySimpleBook.service.ItemIncomeService;
import net.spark9092.MySimpleBook.service.UserLoginService;

@RequestMapping("/itemIncome")
@RestController
public class ItemIncomeController {

	private static final Logger logger = LoggerFactory.getLogger(UserLoginService.class);

	@Autowired
	private ItemIncomeService itemIncomeService;

	@PostMapping("/list")
    @ResponseBody
	public List<List<String>> list(HttpSession session) {

		List<List<String>> dataMap = new ArrayList<>();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if(null != userInfoEntity) {

			logger.debug(userInfoEntity.toString());

			dataMap = itemIncomeService.getListByUserId(userInfoEntity.getId());
		}

		return dataMap;
	}

	@PostMapping("/one/{itemId}")
    @ResponseBody
	public ItemIncomeOneMsgDto one(HttpSession session, @PathVariable("itemId") int itemId) {

		ItemIncomeOneMsgDto itemIncomeOneMsgDto = new ItemIncomeOneMsgDto();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if(null == userInfoEntity) {

			itemIncomeOneMsgDto.setStatus(false);
			itemIncomeOneMsgDto.setMsg("使用者未登入");

		} else {

			logger.debug(userInfoEntity.toString());

			int userId = userInfoEntity.getId();

			itemIncomeOneMsgDto = itemIncomeService.getOneByIds(userId, itemId);
		}

		return itemIncomeOneMsgDto;
	}

	@PostMapping("/create/act")
    @ResponseBody
	public ItemIncomeCreateMsgDto createAct(HttpSession session, @RequestBody ItemIncomeCreatePojo itemIncomeCreatePojo) {

		ItemIncomeCreateMsgDto itemIncomeCreateMsgDto = new ItemIncomeCreateMsgDto();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if(null == userInfoEntity) {

			itemIncomeCreateMsgDto.setStatus(false);
			itemIncomeCreateMsgDto.setMsg("使用者未登入");

		} else {

			logger.debug(userInfoEntity.toString());

			itemIncomeCreatePojo.setUserId(userInfoEntity.getId());

			boolean createStatus = itemIncomeService.createByPojo(itemIncomeCreatePojo);

			if(createStatus) {

				itemIncomeCreateMsgDto.setStatus(true);
				itemIncomeCreateMsgDto.setMsg("");

			} else {

				itemIncomeCreateMsgDto.setStatus(false);
				itemIncomeCreateMsgDto.setMsg("新增未成功");

			}
		}

		return itemIncomeCreateMsgDto;
	}

	@PostMapping("/modify/act")
    @ResponseBody
	public ItemIncomeModifyMsgDto modifyAct(HttpSession session, @RequestBody ItemIncomeModifyPojo itemIncomeModifyPojo) {

		ItemIncomeModifyMsgDto itemIncomeModifyMsgDto = new ItemIncomeModifyMsgDto();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if(null == userInfoEntity) {

			itemIncomeModifyMsgDto.setStatus(false);
			itemIncomeModifyMsgDto.setMsg("使用者未登入");

		} else {

			logger.debug(userInfoEntity.toString());

			itemIncomeModifyPojo.setUserId(userInfoEntity.getId());

			boolean modifyStatus = itemIncomeService.modifyByPojo(itemIncomeModifyPojo);

			if(modifyStatus) {

				itemIncomeModifyMsgDto.setStatus(true);
				itemIncomeModifyMsgDto.setMsg("");

			} else {

				itemIncomeModifyMsgDto.setStatus(false);
				itemIncomeModifyMsgDto.setMsg("修改未成功");

			}
		}

		return itemIncomeModifyMsgDto;
	}

	@PostMapping("/delete/act")
    @ResponseBody
	public ItemIncomeDeleteMsgDto deleteAct(HttpSession session, @RequestBody ItemIncomeDeletePojo itemIncomeDeletePojo) {

		ItemIncomeDeleteMsgDto itemIncomeDeleteMsgDto = new ItemIncomeDeleteMsgDto();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if(null == userInfoEntity) {

			itemIncomeDeleteMsgDto.setStatus(false);
			itemIncomeDeleteMsgDto.setMsg("使用者未登入");

		} else {

			logger.debug(userInfoEntity.toString());

			itemIncomeDeletePojo.setUserId(userInfoEntity.getId());

			boolean modifyStatus = itemIncomeService.deleteByPojo(itemIncomeDeletePojo);

			if(modifyStatus) {

				itemIncomeDeleteMsgDto.setStatus(true);
				itemIncomeDeleteMsgDto.setMsg("");

			} else {

				itemIncomeDeleteMsgDto.setStatus(false);
				itemIncomeDeleteMsgDto.setMsg("刪除未成功");

			}
		}

		return itemIncomeDeleteMsgDto;
	}

}
