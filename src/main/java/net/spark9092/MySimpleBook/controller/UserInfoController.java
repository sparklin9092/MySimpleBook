package net.spark9092.MySimpleBook.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.spark9092.MySimpleBook.dto.user.DeleteMsgDto;
import net.spark9092.MySimpleBook.dto.user.InfoModifyMsgDto;
import net.spark9092.MySimpleBook.dto.user.InfoMsgDto;
import net.spark9092.MySimpleBook.dto.user.PwdChangeMsgDto;
import net.spark9092.MySimpleBook.entity.UserInfoEntity;
import net.spark9092.MySimpleBook.enums.SessinNameEnum;
import net.spark9092.MySimpleBook.pojo.user.ChangePwdPojo;
import net.spark9092.MySimpleBook.pojo.user.ModifyPojo;
import net.spark9092.MySimpleBook.service.UserInfoService;

@RequestMapping("/user/info")
@RestController
public class UserInfoController {

//	private static final Logger logger = LoggerFactory.getLogger(UserInfoController.class);

	@Autowired
	private UserInfoService userInfoService;

	@PostMapping("")
	public InfoMsgDto userInfo(HttpSession session) {

		InfoMsgDto userInfoMsgDto = new InfoMsgDto();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if(null == userInfoEntity) {

			userInfoMsgDto.setStatus(false);
			userInfoMsgDto.setMsg("使用者未登入");

		} else {

			userInfoMsgDto = userInfoService.getUserInfoByEntity(userInfoEntity);
		}

		return userInfoMsgDto;
	}

	@PostMapping("/pwd/change")
	public PwdChangeMsgDto changePwd(HttpSession session, @RequestBody ChangePwdPojo changePwdPojo) {

		PwdChangeMsgDto userPwdChangeMsgDto = new PwdChangeMsgDto();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if (null == userInfoEntity) {

			userPwdChangeMsgDto.setStatus(false);
			userPwdChangeMsgDto.setMsg("使用者未登入");

		} else {

			changePwdPojo.setUserId(userInfoEntity.getId());

			userPwdChangeMsgDto = userInfoService.modifyPwd(changePwdPojo, userInfoEntity.getUserPwd());

			//把session註銷掉，強制讓使用者重新登入
			if(userPwdChangeMsgDto.isStatus()) session.invalidate();
		}

		return userPwdChangeMsgDto;
	}

	@PostMapping("/modify")
	public InfoModifyMsgDto modifyAct(HttpSession session, @RequestBody ModifyPojo modifyPojo) {

		InfoModifyMsgDto userInfoModifyMsgDto = new InfoModifyMsgDto();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if (null == userInfoEntity) {

			userInfoModifyMsgDto.setStatus(false);
			userInfoModifyMsgDto.setMsg("使用者未登入");

		} else {

			modifyPojo.setUserId(userInfoEntity.getId());
			modifyPojo.setEntity(userInfoEntity);

			userInfoModifyMsgDto = userInfoService.modifyByPojo(modifyPojo);

			//如果使用者基本資料更新成功之後，就更新 session 裡的資料
			if(userInfoModifyMsgDto.isStatus()) {

				session.removeAttribute(SessinNameEnum.USER_INFO.getName());
				session.setAttribute(SessinNameEnum.USER_INFO.getName(), userInfoModifyMsgDto.getEntity());
			}
			
			//把Entity設為null，不然使用者資料就全部回傳給前端了
			userInfoModifyMsgDto.setEntity(null);
		}

		return userInfoModifyMsgDto;
	}

	@PostMapping("/delete")
	public DeleteMsgDto deleteAct(HttpSession session) {

		DeleteMsgDto userDeleteMsgDto = new DeleteMsgDto();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if (null == userInfoEntity) {

			userDeleteMsgDto.setStatus(false);
			userDeleteMsgDto.setMsg("使用者未登入");

		} else {

			try {
				userDeleteMsgDto = userInfoService.deleteUserById(userInfoEntity.getId());

				//如果使用者資料都刪除成功，就註銷 session
				if(userDeleteMsgDto.isStatus()) {

					session.invalidate();
				}
			} catch(Exception ex) {

				ex.printStackTrace();

				userDeleteMsgDto.setStatus(false);
				userDeleteMsgDto.setMsg("目前無法刪除您的所有資料，已將您的需求提報，請稍後再試試看。");
			}
		}

		return userDeleteMsgDto;
	}

}
