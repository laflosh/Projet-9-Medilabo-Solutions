package com.medilabo.mservice_clientui.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.medilabo.mservice_clientui.model.LoginRequest;
import com.medilabo.mservice_clientui.proxys.GatewayProxy;

/**
 * 
 */
@Controller
@RequestMapping("/ui")
public class LoginController {

	private final static Logger log = LogManager.getLogger(LoginController.class);
	
	@Autowired
	GatewayProxy gatewayProxy;
	
	/**
	 * @param model
	 * @param request
	 * @return
	 */
	@GetMapping("/login")
	public String showLoginForm(Model model, LoginRequest request) {
		
		log.info("Access to the login page");
		
		model.addAttribute("request", new LoginRequest());
		
		return"login";
		
	}
	
	/**
	 * @param request
	 */
	@PostMapping("/login/auth")
	public void postLogin(@ModelAttribute LoginRequest request) {
		
		Object response = gatewayProxy.login(request);
		
		log.info("{}", response);
		
	}
	
}
