package com.medilabo.mservice_auth;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients("com.medilabo.mservice_auth")
public class MserviceAuthApplication {

	private final static Logger log = LogManager.getLogger("MserviceAuthApplication");
	
	public static void main(String[] args) {
		SpringApplication.run(MserviceAuthApplication.class, args);
	}

}
