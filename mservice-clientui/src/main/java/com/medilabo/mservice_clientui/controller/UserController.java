package com.medilabo.mservice_clientui.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.medilabo.mservice_clientui.beans.UserBean;
import com.medilabo.mservice_clientui.proxys.MServiceUserProxy;

/**
 * 
 */
@Controller
@RequestMapping("/ui")
public class UserController {

	private final static Logger log = LogManager.getLogger(UserController.class);
	
	@Autowired
	MServiceUserProxy userProxy;
	
	/**
	 * @param model
	 * @return
	 */
	@GetMapping("/users")
	public String showListOfAllUsers(Model model) {
		
		List<UserBean> users = userProxy.getAllUsers();
		
		model.addAttribute("users", users);
		
		return "users/list";
		
	}
	
	
	
}
