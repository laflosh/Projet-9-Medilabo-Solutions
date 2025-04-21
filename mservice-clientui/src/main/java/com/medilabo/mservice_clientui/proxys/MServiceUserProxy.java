package com.medilabo.mservice_clientui.proxys;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.medilabo.mservice_clientui.beans.UserBean;
import com.medilabo.mservice_clientui.configuration.FeignConfig;

@FeignClient(name = "mservice-user", url = "localhost:8080", configuration = FeignConfig.class)
public interface MServiceUserProxy { 
	
	@GetMapping("/api/users")
	List<UserBean> getAllUsers();
	
	@GetMapping("/api/users/{id}")
	UserBean getOneUserById(int id);
	
	@PostMapping("/api/users")
	UserBean addNewUser(UserBean user);
	
	@PutMapping("/api/users")
	UserBean updateExistingUser(UserBean user);
	
	@DeleteMapping("/api/users/{id}")
	void deleteExistingUserById(int id);
	
}
