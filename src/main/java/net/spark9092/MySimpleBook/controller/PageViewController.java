package net.spark9092.MySimpleBook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

/**
 * HTTP 的 Get 方法都寫這個Controller
 * @author spark.9092
 */
@Controller
public class PageViewController {

	/**
	 * 登入頁
	 */
	@GetMapping({"", "/", "/index"})
	public ModelAndView index() {

		ModelAndView mv = new ModelAndView();
		String pageName = "views/index";
		mv.setViewName(pageName);

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
		String pageName = "views/spend/spend";
		mv.setViewName(pageName);

		return mv;
	}

	/**
	 * 支出項目管理-列表
	 */
	@GetMapping("/spend/items")
	public ModelAndView spendItems() {

		ModelAndView mv = new ModelAndView();
		String pageName = "views/items/spend/itemSpend";
		mv.setViewName(pageName);

		return mv;
	}

	/**
	 * 支出項目管理-新增
	 */
	@GetMapping("/spend/items/create")
	public ModelAndView spendItemsCreate() {

		ModelAndView mv = new ModelAndView();
		String pageName = "views/items/spend/itemSpendCreate";
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
		String pageName = "views/items/spend/itemSpendModify";
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
		String pageName = "views/income/income";
		mv.setViewName(pageName);

		return mv;
	}

	/**
	 * 收入項目管理-列表
	 */
	@GetMapping("/income/items")
	public ModelAndView incomeItems() {

		ModelAndView mv = new ModelAndView();
		String pageName = "views/items/income/itemIncome";
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
		String pageName = "views/items/income/itemIncomeCreate";
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
		String pageName = "views/items/income/itemIncomeModify";
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
		String pageName = "views/transfer/transfer";
		mv.setViewName(pageName);

		return mv;
	}

	/**
	 * 帳戶管理-列表
	 */
	@GetMapping("/account")
	public ModelAndView account() {

		ModelAndView mv = new ModelAndView();
		String pageName = "views/account/account";
		mv.setViewName(pageName);

		return mv;
	}

	/**
	 * 帳戶管理-新增
	 */
	@GetMapping("/account/create")
	public ModelAndView accountCreate() {

		ModelAndView mv = new ModelAndView();
		String pageName = "views/account/accountCreate";
		mv.setViewName(pageName);

		return mv;
	}

	/**
	 * 帳戶管理-修改
	 */
	@GetMapping("/account/modify/{accountId}")
	public ModelAndView accountModify(@PathVariable("accountId") int accountId) {

		ModelAndView mv = new ModelAndView();
		String pageName = "views/account/accountModify";
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
		String pageName = "views/items/accountType/itemAccountType";
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
		String pageName = "views/items/accountType/itemAccountTypeCreate";
		mv.setViewName(pageName);

		return mv;
	}

	/**
	 * 帳戶類型項目管理-修改
	 * @return
	 */
	@GetMapping("/account/types/modify/{itemId}")
	public ModelAndView accountTypesModify(@PathVariable("itemId") int itemId) {

		ModelAndView mv = new ModelAndView();
		String pageName = "views/items/accountType/itemAccountTypeModify";
		mv.setViewName(pageName);
		mv.addObject("itemId", itemId);

		return mv;
	}
}
