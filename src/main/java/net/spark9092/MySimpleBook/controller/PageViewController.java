package net.spark9092.MySimpleBook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
}
