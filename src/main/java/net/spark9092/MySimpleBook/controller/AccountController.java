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

import net.spark9092.MySimpleBook.dto.account.TypeListDto;
import net.spark9092.MySimpleBook.dto.account.TypeListMsgDto;
import net.spark9092.MySimpleBook.dto.account.CreateMsgDto;
import net.spark9092.MySimpleBook.dto.account.DeleteMsgDto;
import net.spark9092.MySimpleBook.dto.account.ModifyMsgDto;
import net.spark9092.MySimpleBook.dto.account.OneMsgDto;
import net.spark9092.MySimpleBook.entity.UserInfoEntity;
import net.spark9092.MySimpleBook.enums.SessinNameEnum;
import net.spark9092.MySimpleBook.pojo.account.CreatePojo;
import net.spark9092.MySimpleBook.pojo.account.DeletePojo;
import net.spark9092.MySimpleBook.pojo.account.ModifyPojo;
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
	public TypeListMsgDto typeList(HttpSession session) {

		TypeListMsgDto typeListMsgDto = new TypeListMsgDto();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if(null == userInfoEntity) {

			typeListMsgDto.setStatus(false);
			typeListMsgDto.setMsg("使用者未登入");

		} else {

			logger.debug(userInfoEntity.toString());

			List<TypeListDto> accountTypeListDto = accountService.getTypeListByUserId(userInfoEntity.getId());

			if(accountTypeListDto.size() == 0) {

				typeListMsgDto.setStatus(false);
				typeListMsgDto.setMsg("沒有找到帳戶類型，請先新增帳戶類型。");

			} else {

				typeListMsgDto.setStatus(true);
				typeListMsgDto.setMsg("");
				typeListMsgDto.setAccountTypeListDto(accountTypeListDto);
			}
		}

		return typeListMsgDto;
	}

	@PostMapping("/one/{accountId}")
    @ResponseBody
	public OneMsgDto one(HttpSession session, @PathVariable("accountId") int accountId) {

		OneMsgDto accountOneMsgDto = new OneMsgDto();

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
	public CreateMsgDto createAct(HttpSession session, @RequestBody CreatePojo createPojo) {

		CreateMsgDto createMsgDto = new CreateMsgDto();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if(null == userInfoEntity) {

			createMsgDto.setStatus(false);
			createMsgDto.setMsg("使用者未登入");

		} else {

			logger.debug(userInfoEntity.toString());

			createPojo.setUserId(userInfoEntity.getId());

			boolean createStatus = accountService.createByPojo(createPojo);

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

			boolean modifyStatus = accountService.modifyByPojo(modifyPojo);

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

			boolean modifyStatus = accountService.deleteByPojo(deletePojo);

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
