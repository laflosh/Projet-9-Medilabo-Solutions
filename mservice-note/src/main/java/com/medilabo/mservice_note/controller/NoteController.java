package com.medilabo.mservice_note.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medilabo.mservice_note.model.Note;
import com.medilabo.mservice_note.service.NoteService;

/**
 * 
 */
@RestController
@RequestMapping("/api")
public class NoteController {

	private final static Logger log = LogManager.getLogger(NoteController.class);
	
	@Autowired
	NoteService noteService;
	
	/**
	 * @return
	 */
	@GetMapping("/notes")
	public ResponseEntity<?> getAllNotes(){
		
		List<Note> notes = noteService.getAllNotes();
		
		if(notes != null) {
			
			return ResponseEntity.ok().body(notes);
			
		} else {
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Notes not founds");
			
		}
		
	}
	
}
