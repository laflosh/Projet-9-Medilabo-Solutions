package com.medilabo.mservice_clientui.controller;

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
 * Controller for managing http request and html templates for the note domain
 */
@Controller
@RequestMapping("/ui")
public class NoteController {

	private final static Logger log = LogManager.getLogger(NoteController.class);
	
	@Autowired
	MServiceNoteProxy noteProxy;
	
	@Autowired
	MServicePatientProxy patientProxy;
	
	/**
	 * Show the add form to insert a new note in the database
	 * 
	 * @param patId
	 * @param model
	 * @param note
	 * @return add template
	 */
	@GetMapping("/notes/add/{patId}")
	public String showFormForAddNewNoteInPatientFolder(@PathVariable("patId") int patId, Model model, NoteBean note) {
		
		log.info("Access to the adding page for new note");
		
		NoteBean newNote = new NoteBean();
		PatientBean patient = patientProxy.getOnePatientById(patId);
		
		newNote.setPatId(patId);
		newNote.setPatient(patient.getName());
		
		model.addAttribute("note", newNote);
		
		return "notes/add";
		
	}
	
	/**
	 * Checking the data in the note object and insert it in the database 
	 * 
	 * @param new note
	 * @param result
	 * @param model
	 * @return redirect to patient folder
	 */
	@PostMapping("/notes/validate")
	public String addNewNote(@Valid  @ModelAttribute("note") NoteBean note, BindingResult result, Model model) {
		
		log.info("Insert a new note in the database : {}", note);
		
		if(result.hasErrors()) {
			
			model.addAttribute("note", note);
			
			return "notes/add";
			
		}
		
		try {
			
			NoteBean addedNote = noteProxy.addNewNote(note);
			
			model.addAttribute("message", "Resumer de la note qui vient d'etre ajouter au dossier du patient " + note.getPatient());
			model.addAttribute("note", addedNote);
			
			return "resume";
			
		} catch(Exception e) {
			
			model.addAttribute("note", note);
			
			return "notes/add";
			
		}
		
	}
	
	/**
	 * Show the form for updating an existing note
	 * 
	 * @param id of the note
	 * @param model
	 * @return update note template
	 */
	@GetMapping("/notes/update/{id}")
	public String showUpdateForm(@PathVariable("id") String id, Model model) {
		
		log.info("Access to the update page for the note with id : {}", id);
		
		NoteBean note = noteProxy.getOneNoteById(id);
		
		model.addAttribute("note", note);
		
		return "notes/update";
		
	}
	
	/**
	 * Checking the data in the update note object and save it in the database
	 * 
	 * @param update note
	 * @param result
	 * @param model
	 * @return redirect to the patient folder
	 */
	@PostMapping("/notes/update/{id}")
	public String updateExistingNote(@PathVariable("id") String id, @Valid @ModelAttribute("note") NoteBean note, BindingResult result, Model model) {
		
		log.info("Update an existing note in the database with id : {}", id);
		
		if(result.hasErrors()) {
			
			model.addAttribute("note", note);
			
			return "notes/update";
			
		}
		
		try {
			
			NoteBean updateNote = noteProxy.updateExistingNote(note);
			
			model.addAttribute("message", "Resumer de la note qui vient d'etre modifier dans le dossier du patient " + note.getPatient());
			model.addAttribute("note", updateNote);
			
			return "resume";
			
		} catch (Exception e) {
			
			model.addAttribute("note", note);
			
			return "notes/update";
			
		}
		
	}
	
	/**
	 * @param id
	 * @param model
	 * @return
	 */
	@GetMapping("/notes/confirmation/{id}")
	public String showConfirmationPageBeforeDelete(@PathVariable("id") String id, Model model) {
		
		log.info("Access to the confirmation page before delete for the note with id : {}", id);
		
		NoteBean note = noteProxy.getOneNoteById(id);
		
		model.addAttribute("note", note);
		
		return "confirmation";
		
	}
	
	/**
	 * Delete an existing note in the database and redirect to the patient folder
	 * 
	 * @param id of the note
	 * @param model
	 * @return redirect to the patient folder
	 */
	@GetMapping("/notes/delete/{id}")
	public String deleteExistingNote(@PathVariable("id") String id, Model model) {
		
		NoteBean note = noteProxy.getOneNoteById(id);
		
		try {
			
			noteProxy.deleteExistingNote(id);
			
			model.addAttribute("patients", patientProxy.getAllPatients());
			
			return "patients/list";
			
		} catch (Exception e) {
			
			model.addAttribute("note", note);
			
			return "confirmation";
			
		}
		
	}
	
}
