package com.medilabo.mservice_clientui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 
 */
@Controller
public class HomeController {

	/**
	 * @param model
	 * @return
	 */
	@GetMapping("/")
	public String homePage(Model model) {
		
		return "home";
		
	}
	
}
