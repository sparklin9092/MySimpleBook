package net.spark9092.MySimpleBook.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LogoutController {

	@GetMapping("/logout")
	public ModelAndView logout(HttpSession session) {

		ModelAndView mv = new ModelAndView();
		String pageName = "redirect:/";
		mv.setViewName(pageName);

		session.invalidate();

		return mv;
	}

}
