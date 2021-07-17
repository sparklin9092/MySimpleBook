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
		String pageName = "index";
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
		String pageName = "main";
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
		String pageName = "spend";
		mv.setViewName(pageName);

		return mv;
	}

	/**
	 * 帳戶管理
	 * @return
	 */
	@GetMapping("account")
	public ModelAndView account() {

		ModelAndView mv = new ModelAndView();
		String pageName = "account";
		mv.setViewName(pageName);

		return mv;
	}

	/**
	 * 支出項目管理-列表
	 * @return
	 */
	@GetMapping("itemSpend")
	public ModelAndView spendItem() {

		ModelAndView mv = new ModelAndView();
		String pageName = "itemSpend";
		mv.setViewName(pageName);

		return mv;
	}

	/**
	 * 支出項目管理-新增
	 * @return
	 */
	@GetMapping("itemSpend/create")
	public ModelAndView spendItemCreate() {

		ModelAndView mv = new ModelAndView();
		String pageName = "itemSpendCreate";
		mv.setViewName(pageName);

		return mv;
	}

	/**
	 * 支出項目管理-修改
	 * @return
	 */
	@GetMapping("itemSpend/modify/{itemId}")
	public ModelAndView spendItemCreate(@PathVariable("itemId") int itemId) {

		ModelAndView mv = new ModelAndView();
		String pageName = "itemSpendModify";
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
		String pageName = "itemIncome";
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
		String pageName = "itemIncomeCreate";
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
		String pageName = "itemIncomeModify";
		mv.setViewName(pageName);
		mv.addObject("itemId", itemId);

		return mv;
	}

	/**
	 * 帳戶類型項目管理-列表
	 * @return
	 */
	@GetMapping("itemAccountType")
	public ModelAndView itemAccountType() {

		ModelAndView mv = new ModelAndView();
		String pageName = "itemAccountType";
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
		String pageName = "itemAccountTypeCreate";
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
		String pageName = "itemAccountTypeModify";
		mv.setViewName(pageName);
		mv.addObject("itemId", itemId);

		return mv;
	}
}
