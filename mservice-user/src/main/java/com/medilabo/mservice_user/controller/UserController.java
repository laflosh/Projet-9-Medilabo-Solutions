package com.medilabo.mservice_user.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medilabo.mservice_user.model.User;
import com.medilabo.mservice_user.service.UserService;

/**
 * 
 */
@RestController
@RequestMapping("/api")
public class UserController {

	private final static Logger log = LogManager.getLogger(UserController.class);
	
	@Autowired
	UserService userService;
	
	/**
	 * @param user
	 * @return
	 */
	@PostMapping("/users")
	public ResponseEntity<?> addNewUser(@RequestBody User user){
		
		try {
			
			User newUser = userService.addNewUser(user);
			
			return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error during add new user");
			
		}
		
	}
	
}
