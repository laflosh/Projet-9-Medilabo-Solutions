package com.medilabo.mservice_clientui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients("com.medilabo.mservice_clientui")
public class MserviceClientuiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MserviceClientuiApplication.class, args);
	}

}
