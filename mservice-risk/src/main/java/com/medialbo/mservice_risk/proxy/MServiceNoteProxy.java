package com.medialbo.mservice_risk.proxy;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.medialbo.mservice_risk.bean.NoteBean;
import com.medialbo.mservice_risk.configuration.FeignConfig;

/**
 * 
 */
@FeignClient(name = "mservice-patient", url = "localhost:8080", configuration = FeignConfig.class)
public interface MServiceNoteProxy {

	@GetMapping("/api/notes/patient/{patientName}")
	List<NoteBean> getAllNotesDependingOfPatientName(@PathVariable("patientName") String patientName);
	
}
