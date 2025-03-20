package com.medilabo.mservice_clientui.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.medilabo.mservice_clientui.beans.PatientBean;
import com.medilabo.mservice_clientui.proxys.MServicePatientProxy;

@Controller
public class PatientController {

	@Autowired
	MServicePatientProxy patientProxy;
	
	@GetMapping("/patients")
	public String listOfAllPatients(Model model) {
		
		List<PatientBean> patients = patientProxy.getAllPatients();
		
		model.addAttribute("patients", patients);
		
		return "patients/list";
		
	}
	
}
