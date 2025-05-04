package com.medilabo.mservice_risk;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients("com.medilabo.mservice_risk")
@ComponentScan(basePackages = {
	    "com.medilabo.mservice_risk",
	    "com.medilabo.mservice_risk.configuration"
	})
public class MserviceRiskApplication {

	private final static Logger log = LogManager.getLogger("MserviceRiskApplication");

	public static void main(String[] args) {
		log.info("Initialize mservice-risk");
		SpringApplication.run(MserviceRiskApplication.class, args);
	}

}
