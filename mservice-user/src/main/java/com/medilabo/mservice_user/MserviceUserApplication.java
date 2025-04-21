package com.medilabo.mservice_user;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import com.medilabo.mservice_user.controller.UserController;

@SpringBootApplication
@EnableDiscoveryClient
public class MserviceUserApplication {

	private final static Logger log = LogManager.getLogger(UserController.class);
	
	public static void main(String[] args) {
		
		log.info("Initializing mservice-user");
		
		SpringApplication.run(MserviceUserApplication.class, args);
	}

}
