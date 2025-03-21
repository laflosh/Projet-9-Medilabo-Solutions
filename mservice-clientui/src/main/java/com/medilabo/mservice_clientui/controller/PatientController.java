package com.medilabo.mservice_clientui.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.medilabo.mservice_clientui.beans.PatientBean;
import com.medilabo.mservice_clientui.proxys.MServicePatientProxy;

@Controller
public class PatientController {

	private final static Logger log = LogManager.getLogger(PatientController.class);
	
	@Autowired
	MServicePatientProxy patientProxy;
	
	/**
	 * @param model
	 * @return
	 */
	@GetMapping("/patients")
	public String listOfAllPatients(Model model) {
		
		List<PatientBean> patients = patientProxy.getAllPatients();
		
		model.addAttribute("patients", patients);
		
		return "patients/list";
		
	}
	
	/**
	 * @param id
	 * @param model
	 * @return
	 */
	@GetMapping("/patients/{id}")
	public String showInformationsOfOnePatient(@PathVariable int id, Model model) {
		
		PatientBean patient = patientProxy.getOnePatientById(id);
		
		model.addAttribute("patient", patient);
		
		return "patients/informations";
		
	}
	
}
