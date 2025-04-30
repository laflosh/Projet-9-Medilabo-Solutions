package com.medilabo.mservice_clientui.proxys;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.medilabo.mservice_clientui.configuration.FeignConfig;
import com.medilabo.mservice_clientui.model.LoginRequest;

/**
 * 
 */
@FeignClient(name = "gateway", url = "localhost:8080", configuration = FeignConfig.class)
public interface GatewayProxy {

	@PostMapping("/auth/login")
	Object login(@RequestBody LoginRequest request);
	
}
