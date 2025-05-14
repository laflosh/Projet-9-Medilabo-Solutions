package com.medilabo.mservice_risk.proxy;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.medilabo.mservice_risk.bean.NoteBean;
import com.medilabo.mservice_risk.configuration.FeignConfig;

/**
 *Proxy class to send request to the mservice-note to get all note depending of the patient name
 */
@FeignClient(name = "mservice-note", url = "${gateway.url}", configuration = FeignConfig.class)
public interface MServiceNoteProxy {

	@GetMapping("/api/notes/patient/{patientName}")
	List<NoteBean> getAllNotesDependingOfPatientName(@PathVariable("patientName") String patientName);

}
