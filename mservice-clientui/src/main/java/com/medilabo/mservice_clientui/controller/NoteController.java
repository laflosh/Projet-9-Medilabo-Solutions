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
		
		if(result.hasErrors()) {
			
			model.addAttribute("note", note);
			
			return "notes/add";
			
		}
		
		try {
			
			NoteBean addedNote = noteProxy.addNewNote(note);
			
			return "redirect:/ui/patients/" + note.getPatId();
			
		} catch(Exception e) {
			
			model.addAttribute("note", note);
			
			return "notes/add";
			
		}
		
	}
	
	/**
	 * @param id
	 * @param model
	 * @return
	 */
	@GetMapping("/notes/update/{id}")
	public String showUpdateForm(@PathVariable("id") String id, Model model) {
		
		NoteBean note = noteProxy.getOneNoteById(id);
		
		model.addAttribute("note", note);
		
		return "notes/update";
		
	}
	
	/**
	 * @param note
	 * @param result
	 * @param model
	 * @return
	 */
	@PostMapping("/notes/update/{id}")
	public String updateExistingNote(@PathVariable("id") String id, @Valid @ModelAttribute("note") NoteBean note, BindingResult result, Model model) {
		
		if(result.hasErrors()) {
			
			model.addAttribute("note", note);
			
			return "notes/update";
			
		}
		
		try {
			
			NoteBean updateNote = noteProxy.updateExistingNote(note);
			
			return "redirect:/ui/patients/" + note.getPatId();
			
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
	@GetMapping("/notes/delete/{id}")
	public String deleteExistingNote(@PathVariable("id") String id, Model model) {
		
		NoteBean note = noteProxy.getOneNoteById(id);
		
		try {
			
			noteProxy.deleteExistingNote(id);
			
			return "redirect:/ui/patients/" + note.getPatId();
			
		} catch (Exception e) {
			
			return "redirect:/ui/patients/" + note.getPatId();
			
		}
		
	}
	
}
