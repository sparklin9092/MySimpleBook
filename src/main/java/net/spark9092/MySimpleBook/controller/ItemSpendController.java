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

import net.spark9092.MySimpleBook.dto.ItemSpendCreateMsgDto;
import net.spark9092.MySimpleBook.dto.ItemSpendDeleteMsgDto;
import net.spark9092.MySimpleBook.dto.ItemSpendModifyMsgDto;
import net.spark9092.MySimpleBook.dto.ItemSpendOneMsgDto;
import net.spark9092.MySimpleBook.entity.UserInfoEntity;
import net.spark9092.MySimpleBook.enums.SessinNameEnum;
import net.spark9092.MySimpleBook.pojo.ItemSpendCreatePojo;
import net.spark9092.MySimpleBook.pojo.ItemSpendDeletePojo;
import net.spark9092.MySimpleBook.pojo.ItemSpendModifyPojo;
import net.spark9092.MySimpleBook.service.ItemSpendService;
import net.spark9092.MySimpleBook.service.UserLoginService;

@RequestMapping("/itemSpend")
@RestController
public class ItemSpendController {

	private static final Logger logger = LoggerFactory.getLogger(UserLoginService.class);

	@Autowired
	private ItemSpendService itemSpendService;

	@PostMapping("/list")
    @ResponseBody
	public List<List<String>> list(HttpSession session) {

		List<List<String>> dataMap = new ArrayList<>();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if(null != userInfoEntity) {

			logger.debug(userInfoEntity.toString());

			dataMap = itemSpendService.getListByUserId(userInfoEntity.getId());
		}

		return dataMap;
	}

	@PostMapping("/one/{itemId}")
    @ResponseBody
	public ItemSpendOneMsgDto one(HttpSession session, @PathVariable("itemId") int itemId) {

		ItemSpendOneMsgDto itemSpendOneMsgDto = new ItemSpendOneMsgDto();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if(null == userInfoEntity) {
			
			itemSpendOneMsgDto.setStatus(false);
			itemSpendOneMsgDto.setMsg("使用者未登入");
			
		} else {

			logger.debug(userInfoEntity.toString());

			int userId = userInfoEntity.getId();

			itemSpendOneMsgDto = itemSpendService.getOneByIds(userId, itemId);
		}

		return itemSpendOneMsgDto;
	}

	@PostMapping("/create/act")
    @ResponseBody
	public ItemSpendCreateMsgDto createAct(HttpSession session, @RequestBody ItemSpendCreatePojo itemSpendCreatePojo) {

		ItemSpendCreateMsgDto itemSpendCreateMsgDto = new ItemSpendCreateMsgDto();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if(null == userInfoEntity) {

			itemSpendCreateMsgDto.setStatus(false);
			itemSpendCreateMsgDto.setMsg("使用者未登入");

		} else {

			logger.debug(userInfoEntity.toString());

			itemSpendCreatePojo.setUserId(userInfoEntity.getId());

			boolean createStatus = itemSpendService.createByPojo(itemSpendCreatePojo);

			if(createStatus) {

				itemSpendCreateMsgDto.setStatus(true);
				itemSpendCreateMsgDto.setMsg("");

			} else {

				itemSpendCreateMsgDto.setStatus(false);
				itemSpendCreateMsgDto.setMsg("新增未成功");

			}
		}

		return itemSpendCreateMsgDto;
	}
	
	@PostMapping("/modify/act")
    @ResponseBody
	public ItemSpendModifyMsgDto modifyAct(HttpSession session, @RequestBody ItemSpendModifyPojo itemSpendModifyPojo) {
		
		ItemSpendModifyMsgDto itemSpendModifyMsgDto = new ItemSpendModifyMsgDto();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if(null == userInfoEntity) {

			itemSpendModifyMsgDto.setStatus(false);
			itemSpendModifyMsgDto.setMsg("使用者未登入");

		} else {

			logger.debug(userInfoEntity.toString());

			itemSpendModifyPojo.setUserId(userInfoEntity.getId());

			boolean modifyStatus = itemSpendService.modifyByPojo(itemSpendModifyPojo);

			if(modifyStatus) {

				itemSpendModifyMsgDto.setStatus(true);
				itemSpendModifyMsgDto.setMsg("");

			} else {

				itemSpendModifyMsgDto.setStatus(false);
				itemSpendModifyMsgDto.setMsg("修改未成功");

			}
		}
		
		return itemSpendModifyMsgDto;
	}
	
	@PostMapping("/delete/act")
    @ResponseBody
	public ItemSpendDeleteMsgDto deleteAct(HttpSession session, @RequestBody ItemSpendDeletePojo itemSpendDeletePojo) {
		
		ItemSpendDeleteMsgDto itemSpendDeleteMsgDto = new ItemSpendDeleteMsgDto();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if(null == userInfoEntity) {

			itemSpendDeleteMsgDto.setStatus(false);
			itemSpendDeleteMsgDto.setMsg("使用者未登入");

		} else {

			logger.debug(userInfoEntity.toString());

			itemSpendDeletePojo.setUserId(userInfoEntity.getId());

			boolean modifyStatus = itemSpendService.deleteByPojo(itemSpendDeletePojo);

			if(modifyStatus) {

				itemSpendDeleteMsgDto.setStatus(true);
				itemSpendDeleteMsgDto.setMsg("");

			} else {

				itemSpendDeleteMsgDto.setStatus(false);
				itemSpendDeleteMsgDto.setMsg("刪除未成功");

			}
		}
		
		return itemSpendDeleteMsgDto;
	}

}
