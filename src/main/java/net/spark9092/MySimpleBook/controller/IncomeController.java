package net.spark9092.MySimpleBook.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.spark9092.MySimpleBook.dto.income.CreateMsgDto;
import net.spark9092.MySimpleBook.dto.income.DeleteMsgDto;
import net.spark9092.MySimpleBook.dto.income.ModifyMsgDto;
import net.spark9092.MySimpleBook.dto.income.OneMsgDto;
import net.spark9092.MySimpleBook.dto.income.RecListMsgDto;
import net.spark9092.MySimpleBook.dto.income.SelectAccountMsgDto;
import net.spark9092.MySimpleBook.dto.income.SelectItemMsgDto;
import net.spark9092.MySimpleBook.entity.UserInfoEntity;
import net.spark9092.MySimpleBook.enums.SessinNameEnum;
import net.spark9092.MySimpleBook.pojo.income.CreatePojo;
import net.spark9092.MySimpleBook.pojo.income.DeletePojo;
import net.spark9092.MySimpleBook.pojo.income.ModifyPojo;
import net.spark9092.MySimpleBook.pojo.income.RecordPojo;
import net.spark9092.MySimpleBook.service.IncomeService;

@RequestMapping("/income")
@RestController
public class IncomeController {

	private static final Logger logger = LoggerFactory.getLogger(IncomeController.class);

	@Autowired
	private IncomeService incomeService;

	@PostMapping("/itemList")
	public SelectItemMsgDto incomeItemList(HttpSession session) {

		logger.debug("取得支出項目的下拉選單");

		SelectItemMsgDto selectItemMsgDto = new SelectItemMsgDto();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if(null == userInfoEntity) {

			selectItemMsgDto.setStatus(false);
			selectItemMsgDto.setMsg("使用者未登入");

		} else {

			selectItemMsgDto = incomeService.getIncomeListByUserId(userInfoEntity.getId());
		}

		return selectItemMsgDto;
	}

	@PostMapping("/accountList")
	public SelectAccountMsgDto accountList(HttpSession session) {

		logger.debug("取得帳戶的下拉選單");

		SelectAccountMsgDto selectAccountMsgDto = new SelectAccountMsgDto();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if(null == userInfoEntity) {

			selectAccountMsgDto.setStatus(false);
			selectAccountMsgDto.setMsg("使用者未登入");

		} else {

			selectAccountMsgDto = incomeService.getAccountListByUserId(userInfoEntity.getId());
		}

		return selectAccountMsgDto;
	}

	@PostMapping("/records")
	public RecListMsgDto records(HttpSession session, @RequestBody RecordPojo recordPojo) {

		logger.debug("根據使用者索引(User ID)、日期範圍取得收入紀錄");

		RecListMsgDto recListMsgDto = new RecListMsgDto();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if(null != userInfoEntity) {

			recordPojo.setUserId(userInfoEntity.getId());

			recListMsgDto = incomeService.getRecordsByUserId(recordPojo);
		}

		return recListMsgDto;
	}

	@PostMapping("/one/{incomeId}")
	public OneMsgDto one(HttpSession session, @PathVariable("incomeId") int incomeId) {
		
		logger.debug("取得某一筆收入資料");

		OneMsgDto oneMsgDto = new OneMsgDto();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if(null == userInfoEntity) {

			oneMsgDto.setStatus(false);
			oneMsgDto.setMsg("使用者未登入");

		} else {

			oneMsgDto = incomeService.getOneByIds(userInfoEntity.getId(), incomeId);
		}

		return oneMsgDto;
	}

	@PostMapping("/create/act")
	public CreateMsgDto createAct(HttpSession session, @RequestBody CreatePojo createPojo) {

		logger.debug("新增一筆收入");

		CreateMsgDto createMsgDto = new CreateMsgDto();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if(null == userInfoEntity) {

			createMsgDto.setStatus(false);
			createMsgDto.setMsg("使用者未登入");

		} else {

			createPojo.setUserId(userInfoEntity.getId());

			try {

				createMsgDto = incomeService.createByPojo(createPojo);

			} catch (Exception ex) {

				createMsgDto.setStatus(false);
				createMsgDto.setMsg("新增一筆支出發生錯誤，請再重新操作一次。");
			}
		}

		return createMsgDto;
	}

	@PostMapping("/modify/act")
	public ModifyMsgDto modifyAct(HttpSession session, @RequestBody ModifyPojo modifyPojo) {

		logger.debug("更新一筆收入");

		ModifyMsgDto modifyMsgDto = new ModifyMsgDto();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if(null == userInfoEntity) {

			modifyMsgDto.setStatus(false);
			modifyMsgDto.setMsg("使用者未登入");

		} else {

			modifyPojo.setUserId(userInfoEntity.getId());

			try {

				modifyMsgDto = incomeService.modifyByPojo(modifyPojo);

			} catch (Exception ex) {

				ex.printStackTrace();

				modifyMsgDto.setStatus(false);
				modifyMsgDto.setMsg("修改一筆收入發生錯誤，請再重新操作一次。");
			}
		}

		return modifyMsgDto;
	}

	@PostMapping("/delete/act")
	public DeleteMsgDto deleteAct(HttpSession session, @RequestBody DeletePojo deletePojo) {

		logger.debug("刪除一筆收入");

		DeleteMsgDto deleteMsgDto = new DeleteMsgDto();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if(null == userInfoEntity) {

			deleteMsgDto.setStatus(false);
			deleteMsgDto.setMsg("使用者未登入");

		} else {

			deletePojo.setUserId(userInfoEntity.getId());

			try {

				deleteMsgDto = incomeService.deleteByPojo(deletePojo);

			} catch (Exception ex) {

				ex.printStackTrace();

				deleteMsgDto.setStatus(false);
				deleteMsgDto.setMsg("刪除一筆收入發生錯誤，請再重新操作一次。");
			}
		}

		return deleteMsgDto;
	}

}
