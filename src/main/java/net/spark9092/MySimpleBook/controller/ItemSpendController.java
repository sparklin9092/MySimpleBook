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

import net.spark9092.MySimpleBook.dto.items.spend.CreateMsgDto;
import net.spark9092.MySimpleBook.dto.items.spend.DeleteMsgDto;
import net.spark9092.MySimpleBook.dto.items.spend.ModifyMsgDto;
import net.spark9092.MySimpleBook.dto.items.spend.OneMsgDto;
import net.spark9092.MySimpleBook.entity.UserInfoEntity;
import net.spark9092.MySimpleBook.enums.SessinNameEnum;
import net.spark9092.MySimpleBook.pojo.items.spend.CreatePojo;
import net.spark9092.MySimpleBook.pojo.items.spend.DeletePojo;
import net.spark9092.MySimpleBook.pojo.items.spend.ModifyPojo;
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
	public OneMsgDto one(HttpSession session, @PathVariable("itemId") int itemId) {

		OneMsgDto oneMsgDto = new OneMsgDto();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if(null == userInfoEntity) {
			
			oneMsgDto.setStatus(false);
			oneMsgDto.setMsg("使用者未登入");
			
		} else {

			logger.debug(userInfoEntity.toString());

			int userId = userInfoEntity.getId();

			oneMsgDto = itemSpendService.getOneByIds(userId, itemId);
		}

		return oneMsgDto;
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

			logger.debug(userInfoEntity.toString());

			createPojo.setUserId(userInfoEntity.getId());

			boolean createStatus = itemSpendService.createByPojo(createPojo);

			if(createStatus) {

				createMsgDto.setStatus(true);
				createMsgDto.setMsg("");

			} else {

				createMsgDto.setStatus(false);
				createMsgDto.setMsg("新增未成功");

			}
		}

		return createMsgDto;
	}
	
	@PostMapping("/modify/act")
    @ResponseBody
	public ModifyMsgDto modifyAct(HttpSession session, @RequestBody ModifyPojo modifyPojo) {
		
		ModifyMsgDto modifyMsgDto = new ModifyMsgDto();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if(null == userInfoEntity) {

			modifyMsgDto.setStatus(false);
			modifyMsgDto.setMsg("使用者未登入");

		} else {

			logger.debug(userInfoEntity.toString());

			modifyPojo.setUserId(userInfoEntity.getId());

			boolean modifyStatus = itemSpendService.modifyByPojo(modifyPojo);

			if(modifyStatus) {

				modifyMsgDto.setStatus(true);
				modifyMsgDto.setMsg("");

			} else {

				modifyMsgDto.setStatus(false);
				modifyMsgDto.setMsg("修改未成功");

			}
		}
		
		return modifyMsgDto;
	}
	
	@PostMapping("/delete/act")
    @ResponseBody
	public DeleteMsgDto deleteAct(HttpSession session, @RequestBody DeletePojo deletePojo) {
		
		DeleteMsgDto deleteMsgDto = new DeleteMsgDto();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if(null == userInfoEntity) {

			deleteMsgDto.setStatus(false);
			deleteMsgDto.setMsg("使用者未登入");

		} else {

			logger.debug(userInfoEntity.toString());

			deletePojo.setUserId(userInfoEntity.getId());

			boolean modifyStatus = itemSpendService.deleteByPojo(deletePojo);

			if(modifyStatus) {

				deleteMsgDto.setStatus(true);
				deleteMsgDto.setMsg("");

			} else {

				deleteMsgDto.setStatus(false);
				deleteMsgDto.setMsg("刪除未成功");

			}
		}
		
		return deleteMsgDto;
	}

}
