package com.medilabo.gateway_server.proxys;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.medilabo.gateway_server.dtos.UserDTO;

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
