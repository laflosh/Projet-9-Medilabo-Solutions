package com.medilabo.mservice_clientui.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.medilabo.mservice_clientui.beans.PatientBean;
import com.medilabo.mservice_clientui.proxys.MServicePatientProxy;

import jakarta.validation.Valid;

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
	
	/**
	 * @param model
	 * @param patient
	 * @return
	 */
	@GetMapping("/patients/add")
	public String showAddFormNewPatient(Model model, PatientBean patient) {
		
		model.addAttribute("patient", new PatientBean());
		
		return "patients/add";
		
	}
	
	/**
	 * @param patient
	 * @param result
	 * @param model
	 * @return
	 */
	@PostMapping("/patients/validate")
	public String addNewPatient(@Valid PatientBean patient, BindingResult result, Model model) {
		
		if(result.hasErrors()) {
			
			return"patients/add";
			
		}
		
		try {
			
			PatientBean newPatient = patientProxy.addNewPatient(patient);
			
			model.addAttribute("message", "Résumé du dossier ajouter à la base de données");
			model.addAttribute("patient", newPatient);
			
			return "patients/resume";
			
		} catch (Exception e) {
			
			return"patients/add";
			
		}
		
	}
	
	/**
	 * @param id
	 * @param model
	 * @return
	 */
	@GetMapping("/patients/update/{id}")
	public String showUpdatePatientForm(@PathVariable int id, Model model) {
		
		PatientBean patient = patientProxy.getOnePatientById(id);
		
		model.addAttribute("patient", patient);
		
		return "patients/update";
		
	}
	
	/**
	 * @param id
	 * @param patient
	 * @param result
	 * @param model
	 * @return
	 */
	@PostMapping("/patients/update/{id}")
	public String updatePatient(@PathVariable int id, @Valid PatientBean patient, BindingResult result, Model model) {
		
		if(result.hasErrors()) {
			
			return "patients/update";
			
		}
		
		try {
			
			PatientBean updatePatient = patientProxy.updateExistingPatient(patient);
			
			model.addAttribute("patient", updatePatient);
			model.addAttribute("message", "Voici le résumé des modifications faites au dossier du patient " + updatePatient.getName());
			
			return "patients/resume";
			
		} catch(Exception e) {
			
			return "patients/update";
			
		}
		
	}
	
}
