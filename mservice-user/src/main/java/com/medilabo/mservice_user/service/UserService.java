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
 * 
 */
@Service
public class UserService {

	private final static Logger log = LogManager.getLogger(UserService.class);
	
	@Autowired
	UserRepository userRepository;
	
	/**
	 * @return
	 */
	public List<User> getAllUsers() {

		Iterable<User> users = userRepository.findAll();
		
		return StreamSupport.stream(users.spliterator(), false).collect(Collectors.toList());
		
	}
	
	/**
	 * @param user
	 * @return
	 */
	public User addNewUser(User user) {

		if(user != null) {
		
			user.setCreationDate(new Date());
			user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
			
		}
		
		User newUser = userRepository.save(user);
		
		return newUser;
		
	}
	
}
