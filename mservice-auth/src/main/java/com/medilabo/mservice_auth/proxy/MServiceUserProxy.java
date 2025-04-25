package com.medilabo.mservice_auth.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.medilabo.mservice_auth.dto.UserDTO;

/**
 * 
 */
@FeignClient(name = "mservice-user", url = "localhost:8080")
public interface MServiceUserProxy {

	@GetMapping("/api/users/username/{username}")
	UserDTO getOneUserByUsername(@PathVariable("username") String username);
	
	@GetMapping("/api/users/mail/{mail}")
	UserDTO getOneUserByMail(@PathVariable("mail") String mail);
	
}
