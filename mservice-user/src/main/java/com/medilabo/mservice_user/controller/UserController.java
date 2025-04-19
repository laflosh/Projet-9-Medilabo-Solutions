package com.medilabo.mservice_user.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	 * @return
	 */
	@GetMapping("/users")
	public ResponseEntity<?> getAllUsers(){
		
		List<User> users  = userService.getAllUsers();
		
		if(users.isEmpty()) {
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Users not found in the database");
			
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(users);
		
	}
	
	/**
	 * @param id
	 * @return
	 */
	@GetMapping("/users/{id}")
	public ResponseEntity<?> getOneUserById(@PathVariable("id") int id){
		
		User user = userService.getOneUserById(id);
		
		if(user != null) {
			
			return ResponseEntity.status(HttpStatus.OK).body(user);
			
		} else {
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
			
		}
		
	}
	
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
	
	/**
	 * @param user
	 * @return
	 */
	@PutMapping("/users")
	public ResponseEntity<?> updateExistingUser(@RequestBody User user){
		
		try {
			
			User updateUser = userService.updateExistingUser(user);
			
			return ResponseEntity.status(HttpStatus.CREATED).body(updateUser);
			
		} catch(Exception e) {
			
			e.printStackTrace();
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error during update the user in database");
			
		}
		
	}
	
}
