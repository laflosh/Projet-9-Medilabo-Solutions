package com.medilabo.mservice_clientui.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.medilabo.mservice_clientui.beans.UserBean;
import com.medilabo.mservice_clientui.proxys.MServiceUserProxy;

import jakarta.validation.Valid;

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
	
	/**
	 * @param model
	 * @param user
	 * @return
	 */
	@GetMapping("/users/add")
	public String showAddForm(Model model, UserBean user) {
		
		UserBean newUser = new UserBean();
		
		model.addAttribute("user", newUser);
		
		return "users/add";
		
	}
	
	/**
	 * @param user
	 * @param result
	 * @param model
	 * @return
	 */
	@PostMapping("/users/validate")
	public String postNewUserInDatabase(@Valid @ModelAttribute("user") UserBean user, BindingResult result, Model model) {
		
		if(result.hasErrors()) {
			
			model.addAttribute("user", user);
			return "users/add";
			
		}
		
		try {
			
			UserBean addedUser = userProxy.addNewUser(user);
			
			model.addAttribute("message", "Résumé de l'utilisateur que vous venez d'ajouter");
			model.addAttribute("user", addedUser);
			
			return "resume";
			
		} catch (Exception e) {
			
			model.addAttribute("user", user);
			return "users/add";
			
		}
		
	}
	
}
