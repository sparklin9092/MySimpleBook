package net.spark9092.MySimpleBook.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
import net.spark9092.MySimpleBook.service.SpendItemsService;

@RequestMapping("/spend/items")
@RestController
public class SpendItemsController {

//	private static final Logger logger = LoggerFactory.getLogger(SpendItemsController.class);

	@Autowired
	private SpendItemsService itemSpendService;

	@PostMapping("/list")
	public List<List<String>> list(HttpSession session) {

		List<List<String>> dataMap = new ArrayList<>();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if(null != userInfoEntity) {

			dataMap = itemSpendService.getListByUserId(userInfoEntity.getId());
		}

		return dataMap;
	}

	@PostMapping("/one/{itemId}")
	public OneMsgDto one(HttpSession session, @PathVariable("itemId") int itemId) {

		OneMsgDto oneMsgDto = new OneMsgDto();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if(null == userInfoEntity) {

			oneMsgDto.setStatus(false);
			oneMsgDto.setMsg("使用者未登入");

		} else {

			int userId = userInfoEntity.getId();

			oneMsgDto = itemSpendService.getOneByIds(userId, itemId);
		}

		return oneMsgDto;
	}

	@PostMapping("/create/act")
	public CreateMsgDto createAct(HttpSession session, @RequestBody CreatePojo createPojo) {

		CreateMsgDto createMsgDto = new CreateMsgDto();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if(null == userInfoEntity) {

			createMsgDto.setStatus(false);
			createMsgDto.setMsg("使用者未登入");

		} else {

			createPojo.setUserId(userInfoEntity.getId());

			createMsgDto = itemSpendService.createByPojo(createPojo);
		}

		return createMsgDto;
	}

	@PostMapping("/modify/act")
	public ModifyMsgDto modifyAct(HttpSession session, @RequestBody ModifyPojo modifyPojo) {

		ModifyMsgDto modifyMsgDto = new ModifyMsgDto();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if(null == userInfoEntity) {

			modifyMsgDto.setStatus(false);
			modifyMsgDto.setMsg("使用者未登入");

		} else {

			modifyPojo.setUserId(userInfoEntity.getId());

			modifyMsgDto = itemSpendService.modifyByPojo(modifyPojo);
		}

		return modifyMsgDto;
	}

	@PostMapping("/delete/act")
	public DeleteMsgDto deleteAct(HttpSession session, @RequestBody DeletePojo deletePojo) {

		DeleteMsgDto deleteMsgDto = new DeleteMsgDto();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if(null == userInfoEntity) {

			deleteMsgDto.setStatus(false);
			deleteMsgDto.setMsg("使用者未登入");

		} else {

			deletePojo.setUserId(userInfoEntity.getId());

			deleteMsgDto = itemSpendService.deleteByPojo(deletePojo);
		}

		return deleteMsgDto;
	}

}
