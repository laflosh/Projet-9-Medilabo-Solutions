package com.medilabo.mservice_clientui.proxys;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.medilabo.mservice_clientui.configuration.FeignConfig;

/**
 * 
 */
@FeignClient(name = "mservice-risk", url = "localhost:8080", configuration = FeignConfig.class)
public interface MServiceRiskProxy {

	@GetMapping("/api/risk/{id}")
	String getRiskLevelOfOnePatient(@PathVariable("id") int id);
	
}
