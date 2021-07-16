package net.spark9092.MySimpleBook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PageViewController {

	@GetMapping({"", "/"})
	public ModelAndView index() {

		ModelAndView mv = new ModelAndView();
		String pageName = "index";
		mv.setViewName(pageName);

		return mv;
	}

	@GetMapping("main")
	public ModelAndView main() {

		ModelAndView mv = new ModelAndView();
		String pageName = "main";
		mv.setViewName(pageName);

		return mv;
	}

	@GetMapping("spend")
	public ModelAndView spend() {

		ModelAndView mv = new ModelAndView();
		String pageName = "spend";
		mv.setViewName(pageName);

		return mv;
	}
}
