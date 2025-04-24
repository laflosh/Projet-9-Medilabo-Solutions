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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.medilabo.mservice_clientui.beans.UserBean;
import com.medilabo.mservice_clientui.proxys.MServiceUserProxy;

import jakarta.validation.Valid;

/**
 * Controller class for managing http request for htmltemplates and crud operations for user domain
 */
@Controller
@RequestMapping("/ui")
public class UserController {

	private final static Logger log = LogManager.getLogger(UserController.class);
	
	@Autowired
	MServiceUserProxy userProxy;
	
	/**
	 * Show the list page with all users in the database
	 * 
	 * @param model
	 * @return users list template
	 */
	@GetMapping("/users")
	public String showListOfAllUsers(Model model) {
		
		log.info("Access to the users list page");
		
		List<UserBean> users = userProxy.getAllUsers();
		
		model.addAttribute("users", users);
		
		return "users/list";
		
	}
	
	/**
	 * Show the formular page for saving a new user in the database
	 * 
	 * @param model
	 * @param New user
	 * @return user add template
	 */
	@GetMapping("/users/add")
	public String showAddForm(Model model, UserBean user) {
		
		log.info("Access to the user add page for saving a new user in the database");
		
		UserBean newUser = new UserBean();
		
		model.addAttribute("user", newUser);
		
		return "users/add";
		
	}
	
	/**
	 * Checking the model the data of the model and if theire is no error
	 * saving the user in the database and return the resume page
	 * 
	 * @param New user
	 * @param result
	 * @param model
	 * @return resume action page
	 */
	@PostMapping("/users/validate")
	public String postNewUserInDatabase(@Valid @ModelAttribute("user") UserBean user, BindingResult result, Model model) {
		
		if(result.hasErrors()) {
			
			log.info("Error in the user model : {} ", result.getAllErrors());
			
			model.addAttribute("user", user);
			return "users/add";
			
		}
		
		try {
			
			log.info("Saving a new user in the database : {} ", user);
			
			UserBean addedUser = userProxy.addNewUser(user);
			
			model.addAttribute("message", "Résumé de l'utilisateur que vous venez d'ajouter");
			model.addAttribute("user", addedUser);
			
			return "resume";
			
		} catch (Exception e) {
			
			log.info("Error during save the user : {} ", e);
			
			model.addAttribute("user", user);
			return "users/add";
			
		}
		
	}
	
	/**
	 * Show the user update page with all the informations of the user
	 * 
	 * @param id of the user
	 * @param model
	 * @return user update template
	 */
	@GetMapping("/users/update/{id}")
	public String showFormForUpdateUser(@PathVariable("id") int id, Model model) {
		
		log.info("Access to the user update page with the id : {} ", id);
		
		UserBean user = userProxy.getOneUserById(id);
		
		model.addAttribute("user", user);
		
		return "users/update";
		
	}
	
	/**
	 * Checking the model the data of the model and if theire is no error
	 * updating the user in the database and return the resume page
	 * 
	 * @param id of the user
	 * @param update user
	 * @param result
	 * @param model
	 * @return resume page
	 */
	@PostMapping("/users/update/{id}")
	public String updateExistingUserInDatabase(@PathVariable("id") int id, @Valid @ModelAttribute("user") UserBean user, BindingResult result, Model model) {
		
		if(result.hasErrors()) {
			
			log.info("Error in the model user : {} ", result.getAllErrors());
			
			model.addAttribute("user", user);
			return "users/update";
			
		}
		
		try {
			
			log.info("Updating an existing user in the database with the id : {} ", id);
			
			UserBean updatedUser = userProxy.updateExistingUser(user);
			
			model.addAttribute("message", "Résumé de l'utilisateur mis à jour");
			model.addAttribute("user", updatedUser);
			
			return "resume";
			
		} catch(Exception e) {
			
			log.info("Error during update the user : {} ", e);
			
			model.addAttribute("user", user);
			return "users/update";
			
		}
		
	}
	
	/**
	 * Show the confirmation pager before delete an existing user in the database
	 * 
	 * @param id of the user
	 * @param model
	 * @return confirmation page
	 */
	@GetMapping("/users/confirmation/{id}")
	public String showConfirmationPageBeforeDelete(@PathVariable("id") int id, Model model) {
		
		log.info("Access to the confirmation page for the user with id : {} ", id);
		
		UserBean user = userProxy.getOneUserById(id);
		
		model.addAttribute("user", user);
		
		return "confirmation";
		
	}
	
	/**
	 * After confirmation, delete an existing user in the database 
	 * and return to the user list page
	 * 
	 * @param id of the user
	 * @param model
	 * @return user list template
	 */
	@GetMapping("/users/delete/{id}")
	public String deleteExistingUserInDatabase(@PathVariable("id") int id, Model model) {
		
		try {
			
			log.info("Delete an existing user in the database with id : {} ", id);
			
			userProxy.deleteExistingUserById(id);
			
			model.addAttribute("users", userProxy.getAllUsers());
			
			return"users/list";
			
		} catch (Exception e) {
			
			log.info("Error during delete user : {} ", e);
			
			model.addAttribute("user", userProxy.getOneUserById(id));
			
			return "confirmation";
			
		}
		
	}
	
}
