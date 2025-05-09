package com.medilabo.mservice_clientui.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.medilabo.mservice_clientui.model.LoginRequest;

/**
 * Controller class to manage the login domain
 */
@Controller
@RequestMapping("/ui")
public class LoginController {

	private final static Logger log = LogManager.getLogger(LoginController.class);
	
	/**
	 * Show the login page
	 * 
	 * @param model
	 * @param request
	 * @return login page
	 */
	@GetMapping("/login")
	public String showLoginForm(Model model, LoginRequest request) {
		
		log.info("Access to the login page");
		
		model.addAttribute("request", new LoginRequest());
		
		return"login";
		
	}
	
}
