package net.spark9092.MySimpleBook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PageViewController {

	/**
	 * 登入頁
	 * @return
	 */
	@GetMapping({"", "/"})
	public ModelAndView index() {

		ModelAndView mv = new ModelAndView();
		String pageName = "views/index";
		mv.setViewName(pageName);

		return mv;
	}

	/**
	 * 首頁
	 * @return
	 */
	@GetMapping("main")
	public ModelAndView main() {

		ModelAndView mv = new ModelAndView();
		String pageName = "views/main";
		mv.setViewName(pageName);

		return mv;
	}

	/**
	 * 新增一筆支出
	 * @return
	 */
	@GetMapping("spend")
	public ModelAndView spend() {

		ModelAndView mv = new ModelAndView();
		String pageName = "views/spend/spend";
		mv.setViewName(pageName);

		return mv;
	}

	/**
	 * 新增一筆收入
	 * @return
	 */
	@GetMapping("income")
	public ModelAndView income() {

		ModelAndView mv = new ModelAndView();
		String pageName = "views/income/income";
		mv.setViewName(pageName);

		return mv;
	}

	/**
	 * 新增一筆轉帳
	 * @return
	 */
	@GetMapping("transfer")
	public ModelAndView transfer() {

		ModelAndView mv = new ModelAndView();
		String pageName = "views/transfer/transfer";
		mv.setViewName(pageName);

		return mv;
	}

	/**
	 * 帳戶管理-列表
	 * @return
	 */
	@GetMapping("account")
	public ModelAndView account() {

		ModelAndView mv = new ModelAndView();
		String pageName = "views/account/account";
		mv.setViewName(pageName);

		return mv;
	}

	/**
	 * 帳戶管理-新增
	 * @return
	 */
	@GetMapping("account/create")
	public ModelAndView accountCreate() {

		ModelAndView mv = new ModelAndView();
		String pageName = "views/account/accountCreate";
		mv.setViewName(pageName);

		return mv;
	}

	/**
	 * 帳戶管理-修改
	 * @return
	 */
	@GetMapping("account/modify/{accountId}")
	public ModelAndView accountModify(@PathVariable("accountId") int accountId) {

		ModelAndView mv = new ModelAndView();
		String pageName = "views/account/accountModify";
		mv.setViewName(pageName);
		mv.addObject("accountId", accountId);

		return mv;
	}

	/**
	 * 支出項目管理-列表
	 * @return
	 */
	@GetMapping("/spend/items")
	public ModelAndView spendItem() {

		ModelAndView mv = new ModelAndView();
		String pageName = "views/items/spend/itemSpend";
		mv.setViewName(pageName);

		return mv;
	}

	/**
	 * 支出項目管理-新增
	 * @return
	 */
	@GetMapping("/income/items")
	public ModelAndView spendItemCreate() {

		ModelAndView mv = new ModelAndView();
		String pageName = "views/items/spend/itemSpendCreate";
		mv.setViewName(pageName);

		return mv;
	}

	/**
	 * 支出項目管理-修改
	 * @return
	 */
	@GetMapping("itemSpend/modify/{itemId}")
	public ModelAndView spendItemModify(@PathVariable("itemId") int itemId) {

		ModelAndView mv = new ModelAndView();
		String pageName = "views/items/spend/itemSpendModify";
		mv.setViewName(pageName);
		mv.addObject("itemId", itemId);

		return mv;
	}

	/**
	 * 收入項目管理-列表
	 * @return
	 */
	@GetMapping("itemIncome")
	public ModelAndView itemIncome() {

		ModelAndView mv = new ModelAndView();
		String pageName = "views/items/income/itemIncome";
		mv.setViewName(pageName);

		return mv;
	}

	/**
	 * 收入項目管理-新增
	 * @return
	 */
	@GetMapping("itemIncome/create")
	public ModelAndView itemIncomeCreate() {

		ModelAndView mv = new ModelAndView();
		String pageName = "views/items/income/itemIncomeCreate";
		mv.setViewName(pageName);

		return mv;
	}

	/**
	 * 收入項目管理-修改
	 * @return
	 */
	@GetMapping("itemIncome/modify/{itemId}")
	public ModelAndView itemIncomeModify(@PathVariable("itemId") int itemId) {

		ModelAndView mv = new ModelAndView();
		String pageName = "views/items/income/itemIncomeModify";
		mv.setViewName(pageName);
		mv.addObject("itemId", itemId);

		return mv;
	}

	/**
	 * 帳戶類型項目管理-列表
	 * @return
	 */
	@GetMapping("/account/types")
	public ModelAndView itemAccountType() {

		ModelAndView mv = new ModelAndView();
		String pageName = "views/items/accountType/itemAccountType";
		mv.setViewName(pageName);

		return mv;
	}

	/**
	 * 帳戶類型項目管理-新增
	 * @return
	 */
	@GetMapping("itemAccountType/create")
	public ModelAndView itemAccountTypeCreate() {

		ModelAndView mv = new ModelAndView();
		String pageName = "views/items/accountType/itemAccountTypeCreate";
		mv.setViewName(pageName);

		return mv;
	}

	/**
	 * 帳戶類型項目管理-修改
	 * @return
	 */
	@GetMapping("itemAccountType/modify/{itemId}")
	public ModelAndView itemAccountTypeModify(@PathVariable("itemId") int itemId) {

		ModelAndView mv = new ModelAndView();
		String pageName = "views/items/accountType/itemAccountTypeModify";
		mv.setViewName(pageName);
		mv.addObject("itemId", itemId);

		return mv;
	}
}
