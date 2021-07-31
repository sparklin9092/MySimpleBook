package net.spark9092.MySimpleBook.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import net.spark9092.MySimpleBook.entity.UserInfoEntity;
import net.spark9092.MySimpleBook.enums.SessinNameEnum;

/**
 * HTTP 的 Get 方法都寫這個Controller
 * @author spark.9092
 */
@Controller
public class PageViewController {

//	private static final Logger logger = LoggerFactory.getLogger(PageViewController.class);

	/**
	 * 給搜尋引擎的爬蟲做SEO
	 * @param response
	 */
	@GetMapping("/robots.txt")
	public void robots(HttpServletResponse response) {
	    try {
			response.getWriter().write("User-agent: *\n"
					+ "Disallow:\n"
					+ "Sitemap: https://richnote.net/sitemap.txt\n");
		} catch (IOException e) {}
	}

	/**
	 * 給搜尋引擎的爬蟲做SEO
	 * @param response
	 */
	@GetMapping("/sitemap.txt")
	public void serveSiteMapFile(HttpServletResponse response) {
	    try {
			response.getWriter().write("https://richnote.net");
		} catch (IOException e) {}
	}

	/**
	 * 登入頁
	 */
	@GetMapping({"", "/", "/index"})
	public ModelAndView index(HttpSession session) {

		ModelAndView mv = new ModelAndView();
		String pageName = "views/index";
		mv.setViewName(pageName);

		String userMail = (String) session.getAttribute(SessinNameEnum.USER_MAIL.getName());
		String userAccount = (String) session.getAttribute(SessinNameEnum.USER_ACC.getName());

		String user = "";
		if(null != userMail) user = userMail;
		else if(null != userAccount) user = userAccount;

		mv.addObject("user", user);

		return mv;
	}

	/**
	 * 首頁
	 */
	@GetMapping("/main")
	public ModelAndView main() {

		ModelAndView mv = new ModelAndView();
		String pageName = "views/main";
		mv.setViewName(pageName);

		return mv;
	}

	/**
	 * 新增一筆支出
	 */
	@GetMapping("/spend")
	public ModelAndView spend() {

		ModelAndView mv = new ModelAndView();
		String pageName = "views/spend/create";
		mv.setViewName(pageName);

		return mv;
	}

	/**
	 * 支出紀錄
	 */
	@GetMapping("/spend/records")
	public ModelAndView spendRecords() {

		ModelAndView mv = new ModelAndView();
		String pageName = "views/spend/records";
		mv.setViewName(pageName);

		return mv;
	}

	/**
	 * 修改一筆支出
	 */
	@GetMapping("/spend/modify/{spendId}")
	public ModelAndView spendModify(@PathVariable("spendId") int spendId) {

		ModelAndView mv = new ModelAndView();
		String pageName = "views/spend/modify";
		mv.setViewName(pageName);
		mv.addObject("spendId", spendId);

		return mv;
	}

	/**
	 * 支出項目管理-列表
	 */
	@GetMapping("/spend/items")
	public ModelAndView spendItems() {

		ModelAndView mv = new ModelAndView();
		String pageName = "views/spend/items/list";
		mv.setViewName(pageName);

		return mv;
	}

	/**
	 * 支出項目管理-新增
	 */
	@GetMapping("/spend/items/create")
	public ModelAndView spendItemsCreate() {

		ModelAndView mv = new ModelAndView();
		String pageName = "views/spend/items/create";
		mv.setViewName(pageName);

		return mv;
	}

	/**
	 * 支出項目管理-修改
	 * @return
	 */
	@GetMapping("/spend/items/modify/{itemId}")
	public ModelAndView spendItemsModify(@PathVariable("itemId") int itemId) {

		ModelAndView mv = new ModelAndView();
		String pageName = "views/spend/items/modify";
		mv.setViewName(pageName);
		mv.addObject("itemId", itemId);

		return mv;
	}

	/**
	 * 新增一筆收入
	 */
	@GetMapping("/income")
	public ModelAndView income() {

		ModelAndView mv = new ModelAndView();
		String pageName = "views/income/create";
		mv.setViewName(pageName);

		return mv;
	}

	/**
	 * 收入紀錄
	 */
	@GetMapping("/income/records")
	public ModelAndView incomeRecords() {

		ModelAndView mv = new ModelAndView();
		String pageName = "views/income/records";
		mv.setViewName(pageName);

		return mv;
	}

	/**
	 * 修改一筆收入
	 */
	@GetMapping("/income/modify/{incomeId}")
	public ModelAndView incomeModify(@PathVariable("incomeId") int incomeId) {

		ModelAndView mv = new ModelAndView();
		String pageName = "views/income/modify";
		mv.setViewName(pageName);
		mv.addObject("incomeId", incomeId);

		return mv;
	}

	/**
	 * 收入項目管理-列表
	 */
	@GetMapping("/income/items")
	public ModelAndView incomeItems() {

		ModelAndView mv = new ModelAndView();
		String pageName = "views/income/items/list";
		mv.setViewName(pageName);

		return mv;
	}

	/**
	 * 收入項目管理-新增
	 * @return
	 */
	@GetMapping("/income/items/create")
	public ModelAndView incomeItemsCreate() {

		ModelAndView mv = new ModelAndView();
		String pageName = "views/income/items/create";
		mv.setViewName(pageName);

		return mv;
	}

	/**
	 * 收入項目管理-修改
	 * @return
	 */
	@GetMapping("/income/items/modify/{itemId}")
	public ModelAndView incomeItemsModify(@PathVariable("itemId") int itemId) {

		ModelAndView mv = new ModelAndView();
		String pageName = "views/income/items/modify";
		mv.setViewName(pageName);
		mv.addObject("itemId", itemId);

		return mv;
	}

	/**
	 * 新增一筆轉帳
	 */
	@GetMapping("/transfer")
	public ModelAndView transfer() {

		ModelAndView mv = new ModelAndView();
		String pageName = "views/transfer/create";
		mv.setViewName(pageName);

		return mv;
	}

	/**
	 * 轉帳紀錄
	 */
	@GetMapping("/transfer/records")
	public ModelAndView transferRecords() {

		ModelAndView mv = new ModelAndView();
		String pageName = "views/transfer/records";
		mv.setViewName(pageName);

		return mv;
	}

	/**
	 * 修改一筆轉帳
	 */
	@GetMapping("/transfer/modify/{transferId}")
	public ModelAndView transferModify(@PathVariable("transferId") int transferId) {

		ModelAndView mv = new ModelAndView();
		String pageName = "views/transfer/modify";
		mv.setViewName(pageName);
		mv.addObject("transferId", transferId);

		return mv;
	}

	/**
	 * 帳戶管理-列表
	 */
	@GetMapping("/account")
	public ModelAndView account() {

		ModelAndView mv = new ModelAndView();
		String pageName = "views/account/list";
		mv.setViewName(pageName);

		return mv;
	}

	/**
	 * 帳戶管理-新增
	 */
	@GetMapping("/account/create")
	public ModelAndView accountCreate() {

		ModelAndView mv = new ModelAndView();
		String pageName = "views/account/create";
		mv.setViewName(pageName);

		return mv;
	}

	/**
	 * 帳戶管理-修改
	 */
	@GetMapping("/account/modify/{accountId}")
	public ModelAndView accountModify(@PathVariable("accountId") int accountId) {

		ModelAndView mv = new ModelAndView();
		String pageName = "views/account/modify";
		mv.setViewName(pageName);
		mv.addObject("accountId", accountId);

		return mv;
	}

	/**
	 * 帳戶類型項目管理-列表
	 */
	@GetMapping("/account/types")
	public ModelAndView accountTypes() {

		ModelAndView mv = new ModelAndView();
		String pageName = "views/account/types/list";
		mv.setViewName(pageName);

		return mv;
	}

	/**
	 * 帳戶類型項目管理-新增
	 * @return
	 */
	@GetMapping("/account/types/create")
	public ModelAndView accountTypesCreate() {

		ModelAndView mv = new ModelAndView();
		String pageName = "views/account/types/create";
		mv.setViewName(pageName);

		return mv;
	}

	/**
	 * 帳戶類型項目管理-修改
	 * @return
	 */
	@GetMapping("/account/types/modify/{typeId}")
	public ModelAndView accountTypesModify(@PathVariable("typeId") int typeId) {

		ModelAndView mv = new ModelAndView();
		String pageName = "views/account/types/modify";
		mv.setViewName(pageName);
		mv.addObject("typeId", typeId);

		return mv;
	}

	/**
	 * 使用者基本資料
	 * @return
	 */
	@GetMapping("/user/info")
	public ModelAndView userInfo(HttpSession session) {

		ModelAndView mv = new ModelAndView();
		String pageName = "views/user/info";

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());
		if(userInfoEntity.isGuest()) {

			pageName = "redirect:/user/guest";
		}

		mv.setViewName(pageName);

		return mv;
	}

	/**
	 * 給訪客綁定使用者資料，讓訪客可以升級為使用者
	 * @return
	 */
	@GetMapping("/user/guest")
	public ModelAndView userGuest(HttpSession session) {

		ModelAndView mv = new ModelAndView();
		String pageName = "views/user/guest";

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());
		if(!userInfoEntity.isGuest()) {

			pageName = "redirect:/user/info";
		}

		mv.setViewName(pageName);

		return mv;
	}

	/**
	 * 使用者修改密碼
	 * @return
	 */
	@GetMapping("/user/info/pwd")
	public ModelAndView userInfoPed() {

		ModelAndView mv = new ModelAndView();
		String pageName = "views/user/pwd";
		mv.setViewName(pageName);

		return mv;
	}

	/**
	 * 電子信箱(Email)認證碼輸入畫面
	 * @return
	 */
	@GetMapping("/verify/mail/{base64UserAcc}")
	public ModelAndView verifyMail(@PathVariable("base64UserAcc") String base64UserAcc) {

		ModelAndView mv = new ModelAndView();
		String pageName = "views/verify/mail";
		mv.setViewName(pageName);
		mv.addObject("buAcc", base64UserAcc);

		return mv;
	}
}
