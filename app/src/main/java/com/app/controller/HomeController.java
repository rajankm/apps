package com.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(path="/")
public class HomeController {

	@GetMapping(path = { "/", "/home"})
	public ModelAndView home() {
		ModelAndView mv = new ModelAndView("home"); 
		return mv;
	}
	
	
}
