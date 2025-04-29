package com.medilabo.gateway_server.proxys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.medilabo.gateway_server.dtos.UserDTO;

import reactor.core.publisher.Mono;

/**
 * Proxy class to define methods for calling mservice-user to get one user depending od the username or the mail address
 */
@Service
public class MServiceUserProxy {

    private final WebClient webClient;

    @Autowired
    public MServiceUserProxy(WebClient webClient) {
    	
        this.webClient = webClient;
        
    }
	
    /**
     * Fetching one user in mservice-user depending of the username
     * 
     * @param username of the user
     * @return One User
     */
    public Mono<UserDTO> getOneUserByUsername(String username) {
    	
        return webClient.get()
                .uri("/api/users/username/{username}", username)
                .retrieve()
                .bodyToMono(UserDTO.class);
        
    }

    /**
     * Fetching one user in mservice-user depending of the mail address
     * 
     * @param mail of the user
     * @return One User
     */
    public Mono<UserDTO> getOneUserByMail(String mail) {
    	
        return webClient.get()
                .uri("/api/users/mail/{mail}", mail)
                .retrieve()
                .bodyToMono(UserDTO.class);
        
    }
	
}
