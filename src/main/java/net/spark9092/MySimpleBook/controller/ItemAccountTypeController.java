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

import net.spark9092.MySimpleBook.dto.ItemAccountTypeCreateMsgDto;
import net.spark9092.MySimpleBook.dto.ItemAccountTypeDeleteMsgDto;
import net.spark9092.MySimpleBook.dto.ItemAccountTypeModifyMsgDto;
import net.spark9092.MySimpleBook.dto.ItemAccountTypeOneMsgDto;
import net.spark9092.MySimpleBook.entity.UserInfoEntity;
import net.spark9092.MySimpleBook.enums.SessinNameEnum;
import net.spark9092.MySimpleBook.pojo.ItemAccountTypeCreatePojo;
import net.spark9092.MySimpleBook.pojo.ItemAccountTypeDeletePojo;
import net.spark9092.MySimpleBook.pojo.ItemAccountTypeModifyPojo;
import net.spark9092.MySimpleBook.service.ItemAccountTypeService;
import net.spark9092.MySimpleBook.service.UserLoginService;

@RequestMapping("/itemAccountType")
@RestController
public class ItemAccountTypeController {

	private static final Logger logger = LoggerFactory.getLogger(UserLoginService.class);

	@Autowired
	private ItemAccountTypeService itemAccountTypeService;

	@PostMapping("/list")
    @ResponseBody
	public List<List<String>> list(HttpSession session) {

		List<List<String>> dataMap = new ArrayList<>();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if(null != userInfoEntity) {

			logger.debug(userInfoEntity.toString());

			dataMap = itemAccountTypeService.getListByUserId(userInfoEntity.getId());
		}

		return dataMap;
	}

	@PostMapping("/one/{itemId}")
    @ResponseBody
	public ItemAccountTypeOneMsgDto one(HttpSession session, @PathVariable("itemId") int itemId) {

		ItemAccountTypeOneMsgDto itemAccountTypeOneMsgDto = new ItemAccountTypeOneMsgDto();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if(null == userInfoEntity) {

			itemAccountTypeOneMsgDto.setStatus(false);
			itemAccountTypeOneMsgDto.setMsg("使用者未登入");

		} else {

			logger.debug(userInfoEntity.toString());

			int userId = userInfoEntity.getId();

			itemAccountTypeOneMsgDto = itemAccountTypeService.getOneByIds(userId, itemId);
		}

		return itemAccountTypeOneMsgDto;
	}

	@PostMapping("/create/act")
    @ResponseBody
	public ItemAccountTypeCreateMsgDto createAct(HttpSession session, @RequestBody ItemAccountTypeCreatePojo itemAccountTypeCreatePojo) {

		ItemAccountTypeCreateMsgDto itemAccountTypeCreateMsgDto = new ItemAccountTypeCreateMsgDto();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if(null == userInfoEntity) {

			itemAccountTypeCreateMsgDto.setStatus(false);
			itemAccountTypeCreateMsgDto.setMsg("使用者未登入");

		} else {

			logger.debug(userInfoEntity.toString());

			itemAccountTypeCreatePojo.setUserId(userInfoEntity.getId());

			boolean createStatus = itemAccountTypeService.createByPojo(itemAccountTypeCreatePojo);

			if(createStatus) {

				itemAccountTypeCreateMsgDto.setStatus(true);
				itemAccountTypeCreateMsgDto.setMsg("");

			} else {

				itemAccountTypeCreateMsgDto.setStatus(false);
				itemAccountTypeCreateMsgDto.setMsg("新增未成功");

			}
		}

		return itemAccountTypeCreateMsgDto;
	}

	@PostMapping("/modify/act")
    @ResponseBody
	public ItemAccountTypeModifyMsgDto modifyAct(HttpSession session, @RequestBody ItemAccountTypeModifyPojo itemAccountTypeModifyPojo) {

		ItemAccountTypeModifyMsgDto itemAccountTypeModifyMsgDto = new ItemAccountTypeModifyMsgDto();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if(null == userInfoEntity) {

			itemAccountTypeModifyMsgDto.setStatus(false);
			itemAccountTypeModifyMsgDto.setMsg("使用者未登入");

		} else {

			logger.debug(userInfoEntity.toString());

			itemAccountTypeModifyPojo.setUserId(userInfoEntity.getId());

			boolean modifyStatus = itemAccountTypeService.modifyByPojo(itemAccountTypeModifyPojo);

			if(modifyStatus) {

				itemAccountTypeModifyMsgDto.setStatus(true);
				itemAccountTypeModifyMsgDto.setMsg("");

			} else {

				itemAccountTypeModifyMsgDto.setStatus(false);
				itemAccountTypeModifyMsgDto.setMsg("修改未成功");

			}
		}

		return itemAccountTypeModifyMsgDto;
	}

	@PostMapping("/delete/act")
    @ResponseBody
	public ItemAccountTypeDeleteMsgDto deleteAct(HttpSession session, @RequestBody ItemAccountTypeDeletePojo itemAccountTypeDeletePojo) {

		ItemAccountTypeDeleteMsgDto itemAccountTypeDeleteMsgDto = new ItemAccountTypeDeleteMsgDto();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if(null == userInfoEntity) {

			itemAccountTypeDeleteMsgDto.setStatus(false);
			itemAccountTypeDeleteMsgDto.setMsg("使用者未登入");

		} else {

			logger.debug(userInfoEntity.toString());

			itemAccountTypeDeletePojo.setUserId(userInfoEntity.getId());

			boolean modifyStatus = itemAccountTypeService.deleteByPojo(itemAccountTypeDeletePojo);

			if(modifyStatus) {

				itemAccountTypeDeleteMsgDto.setStatus(true);
				itemAccountTypeDeleteMsgDto.setMsg("");

			} else {

				itemAccountTypeDeleteMsgDto.setStatus(false);
				itemAccountTypeDeleteMsgDto.setMsg("刪除未成功");

			}
		}

		return itemAccountTypeDeleteMsgDto;
	}

}
