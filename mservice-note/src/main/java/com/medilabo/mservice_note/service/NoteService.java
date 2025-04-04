package com.medilabo.mservice_note.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medilabo.mservice_note.model.Note;
import com.medilabo.mservice_note.repository.NoteRepository;

/**
 * 
 */
@Service
public class NoteService {

	private final static Logger log = LogManager.getLogger(NoteService.class);
	
	@Autowired
	NoteRepository noteRepository;

	/**
	 * @return
	 */
	public List<Note> getAllNotes() {
		
		List<Note> notes = noteRepository.findAll();
		
		return notes;
		
	}

	/**
	 * @param id
	 * @return
	 */
	public Note getOneNoteById(String id) {
		
		Note note = noteRepository.findById(id)
					.orElseThrow(() -> new RuntimeException("Note not found"));
		
		return note;
		
	}
	
}
