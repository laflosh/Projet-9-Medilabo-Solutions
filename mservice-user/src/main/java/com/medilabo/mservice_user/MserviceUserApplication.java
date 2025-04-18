package com.medilabo.mservice_user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MserviceUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(MserviceUserApplication.class, args);
	}

}
