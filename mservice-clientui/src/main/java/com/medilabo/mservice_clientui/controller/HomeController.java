package com.medilabo.mservice_clientui.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller class to managing the http requests about showing home page
 */
@Controller
public class HomeController {

	private final static Logger log = LogManager.getLogger(HomeController.class);
	
	/**
	 * Showing the home page
	 * 
	 * @param model
	 * @return home template
	 */
	@GetMapping("/ui")
	public String homePage(Model model) {
		
		log.info("Access to the home page");
		
		return "home";
		
	}
	
}
