package com.jpm1590.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/ejercicio")
public class Example4Controller {
	
	private static final String VIEW = "example4form";
	private static final String RESULT = "example4result";
	
	@GetMapping("/service1")
	public ModelAndView serviceExample(){
		ModelAndView mav = new ModelAndView(VIEW);
		return mav;
	}
	
	@PostMapping("/service2")
	public ModelAndView servicePostExample(){
		ModelAndView mav = new ModelAndView(RESULT);
		return mav;
	}

}

