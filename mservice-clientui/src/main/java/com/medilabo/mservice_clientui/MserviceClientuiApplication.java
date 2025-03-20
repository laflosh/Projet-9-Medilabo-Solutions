package com.medilabo.mservice_clientui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients("com.medilabo.mservice_clientui")
public class MserviceClientuiApplication {

	private final static Logger log = LogManager.getLogger("MserviceClientuiApplication");
	
	public static void main(String[] args) {
		log.info("Initialize mservice-clientui");
		SpringApplication.run(MserviceClientuiApplication.class, args);
	}

}
