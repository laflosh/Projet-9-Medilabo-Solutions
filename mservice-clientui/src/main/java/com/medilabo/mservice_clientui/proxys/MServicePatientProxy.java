package com.medilabo.mservice_clientui.proxys;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.medilabo.mservice_clientui.beans.PatientBean;
import com.medilabo.mservice_clientui.configuration.FeignClientInterceptor;

/**
 * 
 */
@FeignClient(name = "mservice-patient", url = "localhost:8080", configuration = FeignClientInterceptor.class)
public interface MServicePatientProxy {

	@GetMapping("/api/patients")
	List<PatientBean> getAllPatients();
	
	@GetMapping("/api/patients/{id}")
	PatientBean getOnePatientById(@PathVariable int id);
	
	@PostMapping("/api/patients")
	PatientBean addNewPatient(@RequestBody PatientBean patient);
	
	@PutMapping("/api/patients")
	PatientBean updateExistingPatient(@RequestBody PatientBean patient);
	
	@DeleteMapping("/api/patients/{id}")
	void deleteExistingPatientById(@PathVariable int id);
	
}
