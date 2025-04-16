package com.medilabo.mservice_clientui.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.medilabo.mservice_clientui.beans.NoteBean;
import com.medilabo.mservice_clientui.beans.PatientBean;
import com.medilabo.mservice_clientui.proxys.MServiceNoteProxy;
import com.medilabo.mservice_clientui.proxys.MServicePatientProxy;

import jakarta.validation.Valid;

/**
 * Controller class to managing the http requests about html template and data 
 * for patient domain
 */
@Controller
@RequestMapping("/ui")
public class PatientController {

	private final static Logger log = LogManager.getLogger(PatientController.class);
	
	@Autowired
	MServicePatientProxy patientProxy;
	
	@Autowired
	MServiceNoteProxy noteProxy;
	
	/**
	 * Show the patient list page with all the patients in the database from mservice-patient
	 * 
	 * @param model
	 * @return patient list template
	 */
	@GetMapping("/patients")
	public String listOfAllPatients(Model model) {
		
		log.info("Access to the patient list page");
		
		List<PatientBean> patients = patientProxy.getAllPatients();
		
		model.addAttribute("patients", patients);
		
		return "patients/list";
		
	}
	
	/**
	 * Show the page of one patient with all the informations concerning the patient and all the notes
	 * 
	 * @param id of the patient
	 * @param model
	 * @return patient information template
	 */
	@GetMapping("/patients/{id}")
	public String showInformationsOfOnePatient(@PathVariable int id, Model model) {
		
		log.info("Access to the informations page of patient with id : {}", id);
		
		PatientBean patient = patientProxy.getOnePatientById(id);
		List<NoteBean> notesPatient = noteProxy.getAllNotesDependingOfPatientName(patient.getName());
		
		model.addAttribute("patient", patient);
		model.addAttribute("notes", notesPatient);
		
		return "patients/informations";
		
	}
	
	/**
	 * Show the page to add a new patient in the database of mservice-patient
	 * 
	 * @param model
	 * @param patient
	 * @return patient add template
	 */
	@GetMapping("/patients/add")
	public String showAddFormNewPatient(Model model, PatientBean patient) {
		
		log.info("Access to the adding page for new patient");
		
		PatientBean newPatient = new PatientBean();
		
		model.addAttribute("patient", newPatient);
		
		return "patients/add";
		
	}
	
	/**
	 * When trying to add new patient from the formular in the add page, check the data in the patient's object
	 * and add in the database, return to the resume action page
	 * 
	 * @param new patient
	 * @param result
	 * @param model
	 * @return resume template
	 */
	@PostMapping("/patients/validate")
	public String addNewPatient(@Valid @ModelAttribute("patient") PatientBean patient, BindingResult result, Model model) {
		
		log.info("Add a new patient in the database : {}", patient);
		
		if(result.hasErrors()) {
			
			log.info("Error during validate the data : {}", result.getAllErrors());
			
			model.addAttribute("patient", patient);
			return"patients/add";
			
		}
		
		try {
			
			PatientBean newPatient = patientProxy.addNewPatient(patient);
			
			model.addAttribute("message", "Résumé du dossier ajouter à la base de données");
			model.addAttribute("patient", newPatient);
			
			return "resume";
			
		} catch (Exception e) {
			
			log.info("Error during adding new patient : {}", e);
			
			model.addAttribute("patient", patient);
			return"patients/add";
			
		}
		
	}
	
	/**
	 * Show the formular to update the informations of a patient
	 * 
	 * @param id of the patient
	 * @param model
	 * @return patient update template
	 */
	@GetMapping("/patients/update/{id}")
	public String showUpdatePatientForm(@PathVariable int id, Model model) {
		
		log.info("Access to the update patient page with id : {}", id);
		
		PatientBean patient = patientProxy.getOnePatientById(id);
		
		model.addAttribute("patient", patient);
		
		return "patients/update";
		
	}
	
	/**
	 * When trying to update a existing patient in the database, check the data in the patient's object
	 * and update the patient in the database, return to the resume action page
	 * 
	 * @param id of the patient
	 * @param patient
	 * @param result
	 * @param model
	 * @return resume template
	 */
	@PostMapping("/patients/update/{id}")
	public String updatePatient(@PathVariable("id") int id, @Valid @ModelAttribute("patient") PatientBean patient, BindingResult result, Model model) {
		
		log.info("Update an existing patient in the database with id : {}", id);
		
		if(result.hasErrors()) {
			
			log.info("Error during validate the data : {}", result.getAllErrors());
			
			model.addAttribute("patient", patient);
			return "patients/update";
			
		}
		
		try {
			
			PatientBean updatePatient = patientProxy.updateExistingPatient(patient);
			
			model.addAttribute("patient", updatePatient);
			model.addAttribute("message", "Voici le résumé des modifications faites au dossier du patient " + updatePatient.getName());
			
			return "resume";
			
		} catch(Exception e) {
			
			log.info("Error during updating an existing patient : {}", e);
			
			model.addAttribute("patient", patient);
			return "patients/update";
			
		}
		
	}
	
	/**
	 * Call the confirmation page when a patient is about to be deleted
	 * 
	 * @param id of the patient
	 * @param model
	 * @return confirmation template
	 */
	@GetMapping("/patients/confirmation/{id}")
	public String showConfirmationPageBeforeDelete(@PathVariable int id, Model model) {
		
		log.info("Access to the confirmation page before delete for the patient with id : {}", id);
		
		PatientBean patient = patientProxy.getOnePatientById(id);
		
		model.addAttribute("patient", patient);
		
		return "confirmation";
		
	}
	
	/**
	 * After confirmation of suppression of patient, call the proxy method to delete a patient
	 * 
	 * @param id of the patient
	 * @param model
	 * @return patient list template
	 */
	@GetMapping("/patients/delete/{id}")
	public String deletePatientInDatabase(@PathVariable int id, Model model) {
		
		log.info("Delete an existing patient in the database with the id : {} ", id);
		
		try {
			
			patientProxy.deleteExistingPatientById(id);
			
			model.addAttribute("patients", patientProxy.getAllPatients());
			
			return "patients/list";
			
		} catch(Exception e) {
			
			log.info("Error during delete a patient : {}", e);
			
			model.addAttribute("patient",patientProxy.getOnePatientById(id));
			
			return "confirmation";
			
		}
		
	}
	
}
