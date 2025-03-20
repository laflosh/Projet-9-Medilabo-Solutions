package com.medilabo.mservice_clientui.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 
 */
@Controller
public class HomeController {

	private final static Logger log = LogManager.getLogger(HomeController.class);
	
	/**
	 * @param model
	 * @return
	 */
	@GetMapping("/")
	public String homePage(Model model) {
		
		return "home";
		
	}
	
}
