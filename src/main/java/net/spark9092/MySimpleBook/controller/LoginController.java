package net.spark9092.MySimpleBook.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import net.spark9092.MySimpleBook.common.CheckCommon;
import net.spark9092.MySimpleBook.common.GetCommon;
import net.spark9092.MySimpleBook.dto.BaseMsgDto;
import net.spark9092.MySimpleBook.dto.login.ResultMsgDto;
import net.spark9092.MySimpleBook.dto.richCode.ListMsgDto;
import net.spark9092.MySimpleBook.entity.UserInfoEntity;
import net.spark9092.MySimpleBook.enums.SessinNameEnum;
import net.spark9092.MySimpleBook.pojo.user.LoginPojo;
import net.spark9092.MySimpleBook.service.GuestService;
import net.spark9092.MySimpleBook.service.RichCodeService;
import net.spark9092.MySimpleBook.service.UserInfoService;

@RestController
public class LoginController {

//	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private UserInfoService userLoginService;

	@Autowired
	private GuestService guestService;

	@Autowired
	private RichCodeService richCodeService;

	@Autowired
	private CheckCommon checkCommon;

	@Autowired
	private GetCommon getCommon;

	@PostMapping("/login")
	public BaseMsgDto login(HttpServletRequest request, HttpSession session, @RequestBody LoginPojo loginPojo) {

		ResultMsgDto loginResultDto = userLoginService.userLogin(loginPojo);

		UserInfoEntity userInfoEntity = loginResultDto.getUserInfoEntity();
		boolean loginStatus = loginResultDto.isStatus();
		String loginMsg = loginResultDto.getMsg();

		if(loginStatus) {

			//使用者登入成功之後，重新給一個Session，防止XSS攻擊
			session = request.getSession(false);
			if(session != null){
				session.invalidate();
			}
			session = request.getSession(true);

			//取得隨機10組的財富密碼
			ListMsgDto listMsgDto = richCodeService.getRichCodeList();
			if(listMsgDto.isStatus()) {
				//如果取得成功，寫入session
				session.setAttribute(SessinNameEnum.RICH_CODE.getName(), listMsgDto.getListDtos());
			}

			//在 Session 寫入 User 基本資料，後續的功能基本上都要 User ID 去查資料
			session.setAttribute(SessinNameEnum.USER_INFO.getName(), userInfoEntity);

			//因為是一般使用者登入，為避免前端誤判訪客，故意把訪客資料數量寫為 0
			session.setAttribute(SessinNameEnum.GUEST_DATA_COUNT.getName(), 0);
		}

		//多用一個Dto回傳登入結果的原因，是因為不想讓前端知道有 UserInfoEntity 的存在
		BaseMsgDto msgDto = new BaseMsgDto();
		msgDto.setStatus(loginStatus);
		msgDto.setMsg(loginMsg);

		return msgDto;
	}

	@PostMapping("/login/guest")
	public BaseMsgDto loginGuest(HttpServletRequest request, HttpSession session) {

		String ip = null;
		try {
			//取不到訪客的 IP 就算了
			ip = getCommon.getIpAddress(request);
		} catch (Exception e) {}

		//簡單判別一下使用者的裝置是不是移動設備(例如：手機、平板等等)
		String device = "not mobile";
		if(checkCommon.isMobile(request.getHeader("User-Agent"))) device = "mobile";

		ResultMsgDto loginResultDto = guestService.guestLogin(ip, device);

		UserInfoEntity userInfoEntity = loginResultDto.getUserInfoEntity();
		boolean loginStatus = loginResultDto.isStatus();
		String loginMsg = loginResultDto.getMsg();

		if(loginStatus) {

			//使用者登入成功之後，重新給一個Session，防止XSS攻擊
			session = request.getSession(false);
			if(session != null){
				session.invalidate();
			}
			session = request.getSession(true);

			//取得隨機10組的財富密碼
			ListMsgDto listMsgDto = richCodeService.getRichCodeList();
			if(listMsgDto.isStatus()) {
				//如果取得成功，寫入session
				session.setAttribute(SessinNameEnum.RICH_CODE.getName(), listMsgDto.getListDtos());
			}

			//在 Session 寫入 User 基本資料，後續的功能基本上都要 User ID 去查資料
			session.setAttribute(SessinNameEnum.USER_INFO.getName(), userInfoEntity);

			//預設訪客資料數量為 0，後續如果有觸發 AfterControllerCreateActAspect 這個 AOP 會再修改
			session.setAttribute(SessinNameEnum.GUEST_DATA_COUNT.getName(), 0);
		}

		BaseMsgDto msgDto = new BaseMsgDto();
		msgDto.setStatus(loginStatus);
		msgDto.setMsg(loginMsg);

		return msgDto;
	}
	
	@PostMapping("/login/check")
	public BaseMsgDto loginCheck(HttpServletRequest request, HttpSession oldSession) {
		
		BaseMsgDto baseMsgDto = new BaseMsgDto();

		//先檢查舊的session中是否還存在使用者的資料
		UserInfoEntity userInfoEntity = (UserInfoEntity) oldSession.getAttribute(SessinNameEnum.USER_INFO.getName());

		if(null == userInfoEntity) {

			baseMsgDto.setStatus(false);
			
		} else {

			baseMsgDto.setStatus(true);
		}
		
		return baseMsgDto;
	}
}
