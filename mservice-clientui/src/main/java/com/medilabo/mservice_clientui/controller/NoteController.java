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
 * 
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
	 * @param patId
	 * @param model
	 * @param note
	 * @return
	 */
	@GetMapping("/notes/add/{patId}")
	public String showFormForAddNewNoteInPatientFolder(@PathVariable("patId") int patId, Model model, NoteBean note) {
		
		NoteBean newNote = new NoteBean();
		PatientBean patient = patientProxy.getOnePatientById(patId);
		
		newNote.setPatId(patId);
		newNote.setPatient(patient.getName());
		
		model.addAttribute("note", newNote);
		
		return "notes/add";
		
	}
	
	/**
	 * @param note
	 * @param result
	 * @param model
	 * @return
	 */
	@PostMapping("/notes/validate")
	public String addNewNote(@Valid  @ModelAttribute("note") NoteBean note, BindingResult result, Model model) {
		
		try {
			
			if(result.hasErrors()) {
				
				model.addAttribute("note", note);
				
				return "notes/add";
				
			}
			
			NoteBean addedNote = noteProxy.addNewNote(note);
			
			return "redirect:/ui/patients/" + note.getPatId();
			
		} catch(Exception e) {
			
			model.addAttribute("note", note);
			
			return "notes/add";
			
		}
		
	}
	
}
