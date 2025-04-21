package com.medilabo.mservice_user.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.medilabo.mservice_user.model.User;
import com.medilabo.mservice_user.repository.UserRepository;

/**
 * Service for managing all the request to the database and logic for user domain
 */
@Service
public class UserService {

	private final static Logger log = LogManager.getLogger(UserService.class);
	
	@Autowired
	UserRepository userRepository;
	
	/**
	 * @return List Users
	 */
	public List<User> getAllUsers() {

		log.info("Fetching all users in the database");
		
		Iterable<User> users = userRepository.findAll();
		
		return StreamSupport.stream(users.spliterator(), false).collect(Collectors.toList());
		
	}
	
	/**
	 * @param id of the user
	 * @return User
	 */
	public User getOneUserById(int id) {

		log.info("Fetching one user in the database with id : {} ", id);
		
		User user = userRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("User not found"));
		
		return user;
		
	}
	
	/**
	 * @param New user
	 * @return added user
	 */
	public User addNewUser(User user) {

		log.info("Saving a new user in the database : {} ", user);
		
		if(user != null) {
		
			user.setCreationDate(new Date());
			user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
			
		}
		
		User newUser = userRepository.save(user);
		
		return newUser;
		
	}

	/**
	 * @param Update user
	 * @return updated user
	 */
	public User updateExistingUser(User user) {

		log.info("Updating an existing user in the database : {} ", user);
		
		User existingUser = getOneUserById(user.getId());
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		if(existingUser.getUsername() != user.getUsername()) {
			existingUser.setUsername(user.getUsername());
		}
		
		if(existingUser.getName() != user.getName()) {
			existingUser.setName(user.getName());
		}
		
		if(existingUser.getFirstName() != user.getFirstName()) {
			existingUser.setFirstName(user.getFirstName());
		}
		
		if(existingUser.getMail() != user.getMail()) {
			existingUser.setMail(user.getMail());
		}
		
		if(existingUser.getPassword() != encoder.encode(user.getPassword())) {
			existingUser.setPassword(encoder.encode(user.getPassword()));
		}
		
		if(existingUser.getBirthDate() != user.getBirthDate()) {
			existingUser.setBirthDate(user.getBirthDate());
		}
		
		if(existingUser.getRole() != user.getRole()) {
			existingUser.setRole(user.getRole());
		}
		
		User updateUser = userRepository.save(existingUser);
		
		return updateUser;
		
	}

	/**
	 * @param id of the user
	 * @return true if deleted
	 */
	public boolean deleteExistingUserById(int id) {

		log.info("Delete an existing user in the database with id : {}", id);
		
		if(userRepository.existsById(id)) {
			
			userRepository.deleteById(id);
			
			return true;
			
		}
		
		return false;
		
	}

	/**
	 * @param username of the user
	 * @return User
	 */
	public User getOneUserByUsername(String username) {
		
		log.info("Fetching one user ine the database with username : {} ", username);
		
		User user = userRepository.findUserByUsername(username);
		
		return user;
		
	}

	/**
	 * @param mail of the user
	 * @return User
	 */
	public User getOneUserByMail(String mail) {
		
		log.info("Fetching one user in the database with mail : {} ", mail);
		
		User user = userRepository.findUserByMail(mail);
		
		return user;
		
	}
	
}
