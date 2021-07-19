package net.spark9092.MySimpleBook.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import net.spark9092.MySimpleBook.dto.transfer.CreateMsgDto;
import net.spark9092.MySimpleBook.dto.transfer.SelectAccountListDto;
import net.spark9092.MySimpleBook.dto.transfer.SelectAccountMsgDto;
import net.spark9092.MySimpleBook.entity.UserInfoEntity;
import net.spark9092.MySimpleBook.enums.SessinNameEnum;
import net.spark9092.MySimpleBook.pojo.transfer.CreatePojo;
import net.spark9092.MySimpleBook.service.TransferService;
import net.spark9092.MySimpleBook.service.UserLoginService;

@RequestMapping("/transfer")
@RestController
public class TransferController {

	private static final Logger logger = LoggerFactory.getLogger(UserLoginService.class);

	@Autowired
	private TransferService transferService;

	@PostMapping("/accountList")
    @ResponseBody
	public SelectAccountMsgDto accountList(HttpSession session) {
		
		logger.debug("取得帳戶的下拉選單");

		SelectAccountMsgDto selectAccountMsgDto = new SelectAccountMsgDto();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if(null == userInfoEntity) {

			selectAccountMsgDto.setStatus(false);
			selectAccountMsgDto.setMsg("使用者未登入");

		} else {

			List<SelectAccountListDto> selectAccountListDtos = transferService.getAccountListByUserId(userInfoEntity.getId());

			if(selectAccountListDtos.size() == 0) {

				selectAccountMsgDto.setStatus(false);
				selectAccountMsgDto.setMsg("沒有可以使用的帳戶，請先新增或啟用帳戶。");

			} else {

				selectAccountMsgDto.setStatus(true);
				selectAccountMsgDto.setMsg("");
				selectAccountMsgDto.setAccountList(selectAccountListDtos);
			}
		}

		return selectAccountMsgDto;
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

			createPojo.setUserId(userInfoEntity.getId());

			try {
				boolean createStatus = transferService.createByPojo(createPojo);

				if(createStatus) {

					createMsgDto.setStatus(true);
					createMsgDto.setMsg("");

				} else {

					createMsgDto.setStatus(false);
					createMsgDto.setMsg("新增未成功");

				}
			} catch (Exception ex) {

				createMsgDto.setStatus(false);
				createMsgDto.setMsg("新增一筆轉帳發生錯誤，請再重新操作一次。");
			}
		}

		return createMsgDto;
	}

}
