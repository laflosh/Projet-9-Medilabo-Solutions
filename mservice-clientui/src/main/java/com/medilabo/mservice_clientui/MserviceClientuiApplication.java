package com.medilabo.mservice_clientui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MserviceClientuiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MserviceClientuiApplication.class, args);
	}

}
