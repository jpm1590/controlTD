package com.jpm1590.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.jpm1590.model.DateModel;
import com.jpm1590.service.ControlVentasService;

@Controller
@RequestMapping()
public class ControlVentasController {

	private static final String CONTROLVENTAS_VIEW = "ControlVentas";

	private static final Log LOG = LogFactory.getLog(ControlVentasController.class);

	@Autowired
	@Qualifier("controlVentasServiceImp")
	private ControlVentasService controlVentasService;

	@GetMapping("/")
	public String request1() {
		LOG.info("Call: " + "otherDate()" + " --PARAM: " + "No params");
		LocalDate today = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String formattedString = today.format(formatter);
		return "redirect:/date/" + formattedString;
	}

	@GetMapping
	public String request2() {
		return "redirect:/";
	}

	@GetMapping("/date/{date}")
	public ModelAndView request3(@PathVariable("date") String date) {

		LOG.info("Call: " + "getTitles()");
		LOG.info("Call: " + "getAllProcess()");

		ModelAndView mav = new ModelAndView(CONTROLVENTAS_VIEW);

		mav.addObject("new_date", new DateModel());
		mav.addObject("title_in_model", "Control Interfaces TD");
		mav.addObject("title", controlVentasService.getTitles());
		mav.addObject("processes", controlVentasService.getErrorsDesc(date));
		mav.addObject("ver_app", controlVentasService.versionApp());
		mav.addObject("user_dir", System.getProperty("user.dir"));		
		mav.addObject("date", date);

		return mav;
	}

	@PostMapping("/otherdate")
	public String otherDate(@ModelAttribute("new_date") DateModel new_date) {
		LOG.info("Call: " + "otherDate()" + " --PARAM: " + new_date.toString());
		return "redirect:/date/" + new_date.getNewDate();
	}
}
