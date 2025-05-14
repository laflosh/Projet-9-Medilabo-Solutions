package com.medilabo.mservice_clientui.proxys;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.medilabo.mservice_clientui.beans.UserBean;
import com.medilabo.mservice_clientui.configuration.FeignConfig;

/**
 * Proxy interface for http request to mservice-user
 */
@FeignClient(name = "mservice-user", url = "${gateway.url}", configuration = FeignConfig.class)
public interface MServiceUserProxy { 
	
	@GetMapping("/api/users")
	List<UserBean> getAllUsers();
	
	@GetMapping("/api/users/{id}")
	UserBean getOneUserById(@PathVariable("id") int id);
	
	@PostMapping("/api/users")
	UserBean addNewUser(@RequestBody UserBean user);
	
	@PutMapping("/api/users")
	UserBean updateExistingUser(@RequestBody UserBean user);
	
	@DeleteMapping("/api/users/{id}")
	void deleteExistingUserById(@PathVariable("id")int id);
	
}
