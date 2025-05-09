package com.medilabo.mservice_risk.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.medilabo.mservice_risk.bean.PatientBean;
import com.medilabo.mservice_risk.configuration.FeignConfig;

/**
 * Proxy class to send request to the mservice-patient to get informations about one patient depending of the id
 */
@FeignClient(name = "mservice-patient", url = "localhost:8080", configuration = FeignConfig.class)
public interface MServicePatientProxy {

	@GetMapping("/api/patients/{id}")
	PatientBean getOnePatientById(@PathVariable("id") int id);

}
