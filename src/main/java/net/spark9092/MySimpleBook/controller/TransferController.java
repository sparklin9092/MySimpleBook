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

import net.spark9092.MySimpleBook.dto.transfer.CreateMsgDto;
import net.spark9092.MySimpleBook.dto.transfer.DeleteMsgDto;
import net.spark9092.MySimpleBook.dto.transfer.ModifyMsgDto;
import net.spark9092.MySimpleBook.dto.transfer.OneMsgDto;
import net.spark9092.MySimpleBook.dto.transfer.RecListMsgDto;
import net.spark9092.MySimpleBook.dto.transfer.SelectAccountMsgDto;
import net.spark9092.MySimpleBook.entity.UserInfoEntity;
import net.spark9092.MySimpleBook.enums.SessinNameEnum;
import net.spark9092.MySimpleBook.pojo.transfer.CreatePojo;
import net.spark9092.MySimpleBook.pojo.transfer.DeletePojo;
import net.spark9092.MySimpleBook.pojo.transfer.ModifyPojo;
import net.spark9092.MySimpleBook.pojo.transfer.RecordPojo;
import net.spark9092.MySimpleBook.service.TransferService;

@RequestMapping("/transfer")
@RestController
public class TransferController {

	private static final Logger logger = LoggerFactory.getLogger(TransferController.class);

	@Autowired
	private TransferService transferService;

	@PostMapping("/accountList")
	public SelectAccountMsgDto accountList(HttpSession session) {

		logger.debug("取得帳戶的下拉選單");

		SelectAccountMsgDto selectAccountMsgDto = new SelectAccountMsgDto();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if(null == userInfoEntity) {

			selectAccountMsgDto.setStatus(false);
			selectAccountMsgDto.setMsg("使用者未登入");

		} else {

			selectAccountMsgDto = transferService.getAccountListByUserId(userInfoEntity.getId());
		}

		return selectAccountMsgDto;
	}

	@PostMapping("/records")
	public RecListMsgDto records(HttpSession session, @RequestBody RecordPojo recordPojo) {

		logger.debug("根據使用者索引(User ID)、日期範圍取得轉帳紀錄");

		RecListMsgDto recListMsgDto = new RecListMsgDto();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if(null != userInfoEntity) {

			recordPojo.setUserId(userInfoEntity.getId());

			recListMsgDto = transferService.getRecordsByUserId(recordPojo);
		}

		return recListMsgDto;
	}

	@PostMapping("/one/{transferId}")
	public OneMsgDto one(HttpSession session, @PathVariable("transferId") int transferId) {
		
		logger.debug("取得某一筆轉帳資料");

		OneMsgDto oneMsgDto = new OneMsgDto();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if(null == userInfoEntity) {

			oneMsgDto.setStatus(false);
			oneMsgDto.setMsg("使用者未登入");

		} else {

			oneMsgDto = transferService.getOneByIds(userInfoEntity.getId(), transferId);
		}

		return oneMsgDto;
	}

	@PostMapping("/create/act")
	public CreateMsgDto createAct(HttpSession session, @RequestBody CreatePojo createPojo) {

		logger.debug("新增一筆轉帳");

		CreateMsgDto createMsgDto = new CreateMsgDto();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if(null == userInfoEntity) {

			createMsgDto.setStatus(false);
			createMsgDto.setMsg("使用者未登入");

		} else {

			createPojo.setUserId(userInfoEntity.getId());

			try {

				createMsgDto = transferService.createByPojo(createPojo);

			} catch (Exception ex) {

				ex.printStackTrace();

				createMsgDto.setStatus(false);
				createMsgDto.setMsg("新增一筆轉帳發生錯誤，請再重新操作一次。");
			}
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

			try {

				modifyMsgDto = transferService.modifyByPojo(modifyPojo);

			} catch (Exception ex) {

				ex.printStackTrace();

				modifyMsgDto.setStatus(false);
				modifyMsgDto.setMsg("修改一筆轉帳發生錯誤，請再重新操作一次。");
			}
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

			try {

				deleteMsgDto = transferService.deleteByPojo(deletePojo);

			} catch (Exception ex) {

				ex.printStackTrace();

				deleteMsgDto.setStatus(false);
				deleteMsgDto.setMsg("刪除一筆轉帳發生錯誤，請再重新操作一次。");
			}
		}

		return deleteMsgDto;
	}

}
