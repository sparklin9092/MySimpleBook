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

import net.spark9092.MySimpleBook.dto.spend.CreateMsgDto;
import net.spark9092.MySimpleBook.dto.spend.DeleteMsgDto;
import net.spark9092.MySimpleBook.dto.spend.ModifyMsgDto;
import net.spark9092.MySimpleBook.dto.spend.OneMsgDto;
import net.spark9092.MySimpleBook.dto.spend.RecListMsgDto;
import net.spark9092.MySimpleBook.dto.spend.SelectAccountMsgDto;
import net.spark9092.MySimpleBook.dto.spend.SelectItemMsgDto;
import net.spark9092.MySimpleBook.entity.UserInfoEntity;
import net.spark9092.MySimpleBook.enums.SessinNameEnum;
import net.spark9092.MySimpleBook.pojo.spend.CreatePojo;
import net.spark9092.MySimpleBook.pojo.spend.DeletePojo;
import net.spark9092.MySimpleBook.pojo.spend.ModifyPojo;
import net.spark9092.MySimpleBook.pojo.spend.RecordPojo;
import net.spark9092.MySimpleBook.service.SpendService;

@RequestMapping("/spend")
@RestController
public class SpendController {

	private static final Logger logger = LoggerFactory.getLogger(SpendController.class);

	@Autowired
	private SpendService spendService;

	@PostMapping("/itemList")
	public SelectItemMsgDto spendItemList(HttpSession session) {

		logger.debug("取得支出項目的下拉選單");

		SelectItemMsgDto selectItemMsgDto = new SelectItemMsgDto();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if(null == userInfoEntity) {

			selectItemMsgDto.setStatus(false);
			selectItemMsgDto.setMsg("使用者未登入");

		} else {

			selectItemMsgDto = spendService.getSpendListByUserId(userInfoEntity.getId());
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

			selectAccountMsgDto = spendService.getAccountListByUserId(userInfoEntity.getId());
		}

		return selectAccountMsgDto;
	}

	@PostMapping("/records")
	public RecListMsgDto records(HttpSession session, @RequestBody RecordPojo recordPojo) {

		logger.debug("根據使用者索引(User ID)、日期範圍取得支出紀錄");

		RecListMsgDto recListMsgDto = new RecListMsgDto();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if(null != userInfoEntity) {

			recordPojo.setUserId(userInfoEntity.getId());

			recListMsgDto = spendService.getRecordsByUserId(recordPojo);
		}

		return recListMsgDto;
	}

	@PostMapping("/one/{spendId}")
	public OneMsgDto one(HttpSession session, @PathVariable("spendId") int spendId) {
		
		logger.debug("取得某一筆支出資料");

		OneMsgDto oneMsgDto = new OneMsgDto();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if(null == userInfoEntity) {

			oneMsgDto.setStatus(false);
			oneMsgDto.setMsg("使用者未登入");

		} else {

			oneMsgDto = spendService.getOneByIds(userInfoEntity.getId(), spendId);
		}

		return oneMsgDto;
	}

	@PostMapping("/create/act")
	public CreateMsgDto createAct(HttpSession session, @RequestBody CreatePojo createPojo) {

		logger.debug("新增一筆支出");

		CreateMsgDto createMsgDto = new CreateMsgDto();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if(null == userInfoEntity) {

			createMsgDto.setStatus(false);
			createMsgDto.setMsg("使用者未登入");

		} else {

			logger.debug(userInfoEntity.toString());

			createPojo.setUserId(userInfoEntity.getId());

			try {

				createMsgDto = spendService.createByPojo(createPojo);

			} catch (Exception ex) {

				createMsgDto.setStatus(false);
				createMsgDto.setMsg("新增一筆支出時發生錯誤，已將您的問題提報，請稍後再重新新增一次。");
			}
		}

		return createMsgDto;
	}

	@PostMapping("/modify/act")
	public ModifyMsgDto modifyAct(HttpSession session, @RequestBody ModifyPojo modifyPojo) {

		logger.debug("更新一筆支出");

		ModifyMsgDto modifyMsgDto = new ModifyMsgDto();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if(null == userInfoEntity) {

			modifyMsgDto.setStatus(false);
			modifyMsgDto.setMsg("使用者未登入");

		} else {

			modifyPojo.setUserId(userInfoEntity.getId());

			try {

				modifyMsgDto = spendService.modifyByPojo(modifyPojo);

			} catch (Exception ex) {

				ex.printStackTrace();

				modifyMsgDto.setStatus(false);
				modifyMsgDto.setMsg("修改一筆支出時發生錯誤，已將您的問題提報，請稍後再重新修改一次。");
			}
		}

		return modifyMsgDto;
	}

	@PostMapping("/delete/act")
	public DeleteMsgDto deleteAct(HttpSession session, @RequestBody DeletePojo deletePojo) {

		logger.debug("刪除一筆支出");

		DeleteMsgDto deleteMsgDto = new DeleteMsgDto();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if(null == userInfoEntity) {

			deleteMsgDto.setStatus(false);
			deleteMsgDto.setMsg("使用者未登入");

		} else {

			deletePojo.setUserId(userInfoEntity.getId());

			try {

				deleteMsgDto = spendService.deleteByPojo(deletePojo);

			} catch (Exception ex) {

				ex.printStackTrace();

				deleteMsgDto.setStatus(false);
				deleteMsgDto.setMsg("刪除一筆支出時發生錯誤，已將您的問題提報，請稍後再重新刪除一次。");
			}
		}

		return deleteMsgDto;
	}

}
