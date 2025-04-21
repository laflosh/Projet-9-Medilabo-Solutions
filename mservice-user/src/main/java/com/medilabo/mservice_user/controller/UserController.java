package com.medilabo.mservice_user.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
 * Controller class for managing all http request for the user domain
 */
@RestController
@RequestMapping("/api")
public class UserController {

	private final static Logger log = LogManager.getLogger(UserController.class);
	
	@Autowired
	UserService userService;
	
	/**
	 * Fetching all users in the database
	 * 
	 * @return List of users
	 */
	@GetMapping("/users")
	public ResponseEntity<?> getAllUsers(){
		
		log.info("Trying to fetch all users in the database");
		
		List<User> users  = userService.getAllUsers();
		
		if(users.isEmpty()) {
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Users not found in the database");
			
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(users);
		
	}
	
	/**
	 * Fetching one user in the database depending of the id
	 * 
	 * @param id of the user
	 * @return  User
	 */
	@GetMapping("/users/{id}")
	public ResponseEntity<?> getOneUserById(@PathVariable("id") int id){
		
		log.info("Trying to fetch one user with id : {}", id);
		
		User user = userService.getOneUserById(id);
		
		if(user != null) {
			
			return ResponseEntity.status(HttpStatus.OK).body(user);
			
		} else {
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
			
		}
		
	}
	
	/**
	 * Saving a new user in the database
	 * 
	 * @param New user
	 * @return Added User
	 */
	@PostMapping("/users")
	public ResponseEntity<?> addNewUser(@RequestBody User user){
		
		log.info("Trying to add a new user in the database : {}", user);
		
		try {
			
			User newUser = userService.addNewUser(user);
			
			return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error during add new user");
			
		}
		
	}
	
	/**
	 * Updating an existing user in the database 
	 * 
	 * @param Update user
	 * @return Updated user
	 */
	@PutMapping("/users")
	public ResponseEntity<?> updateExistingUser(@RequestBody User user){
		
		log.info("Trying to update an existing user in the database : {} ", user);
		
		try {
			
			User updateUser = userService.updateExistingUser(user);
			
			return ResponseEntity.status(HttpStatus.CREATED).body(updateUser);
			
		} catch(Exception e) {
			
			e.printStackTrace();
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error during update the user in database");
			
		}
		
	}
	
	/**
	 * Delete an existing user in the database depending of the id
	 * 
	 * @param id of the user
	 * @return true id deleted
	 */
	@DeleteMapping("/users/{id}")
	public ResponseEntity<?> deleteExistingUserById(@PathVariable("id") int id){
		
		log.info("Trying to delete an existing user with the id : {} ", id);
		
		try {
			
			boolean isDeleted = userService.deleteExistingUserById(id);
			
			if(isDeleted) {
				
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
				
			} else {
				
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
				
			}
			
		} catch (Exception e) {
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error during delete th user with id : " + id);
			
		}
		
	}
	
	/**
	 * Fetching one user in the database depending of his username
	 * 
	 * @param username of the user
	 * @return User
	 */
	@GetMapping("/users/username/{username}")
	public ResponseEntity<?> getOneUserByUsername(@PathVariable("username") String username){
		
		log.info("Trying to fetch one user in the database with the username : {}", username);
		
		User user = userService.getOneUserByUsername(username);
		
		if(user != null) {
			
			return ResponseEntity.status(HttpStatus.OK).body(user);
			
		} else {
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with the username : " + username);
			
		}
		
	}
	
	/**
	 * Fetching one user in the database depending of his mail
	 * 
	 * @param mail of the user
	 * @return User
	 */
	@GetMapping("/users/mail/{mail}")
	public ResponseEntity<?> getOneUserByMail(@PathVariable("mail") String mail){
		
		log.info("Trying to fetch one user in the database with the mail : {}", mail);
		
		User user = userService.getOneUserByMail(mail);
		
		if(user != null) {
			
			return ResponseEntity.status(HttpStatus.OK).body(user);
			
		} else {
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with the mail : " + mail);
			
		}
		
	}
	
}
