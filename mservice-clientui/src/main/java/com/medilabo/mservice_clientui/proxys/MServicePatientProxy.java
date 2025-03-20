package com.medilabo.mservice_clientui.proxys;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.medilabo.mservice_clientui.beans.PatientBean;

/**
 * 
 */
@Component
@FeignClient(name = "mservice-patient", url = "localhost:8080")
@RequestMapping("/api")
public interface MServicePatientProxy {

	@GetMapping("/patients")
	List<PatientBean> getAllPatients();
	
	@GetMapping("/patients/{id}")
	PatientBean getOnePatientById(@PathVariable int id);
	
	@PostMapping("/patients")
	PatientBean addNewPatient(@RequestBody PatientBean patient);
	
	@PutMapping("/patients")
	PatientBean updateExistingPatient(@RequestBody PatientBean patient);
	
	@DeleteMapping("/patients/{id}")
	void deleteExistingPatientById(@PathVariable int id);
	
}
