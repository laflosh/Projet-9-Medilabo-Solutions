package com.medilabo.mservice_note.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medilabo.mservice_note.model.Note;
import com.medilabo.mservice_note.repository.NoteRepository;

/**
 * Service's class to managing all the method about logic on note domain
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
		
		log.info("Fetching all notes in the database");
		
		List<Note> notes = noteRepository.findAll();
		
		return notes;
		
	}

	/**
	 * @param id
	 * @return
	 */
	public Note getOneNoteById(String id) {
		
		log.info("Fetching one note with id : {}", id);
		
		Note note = noteRepository.findById(id)
					.orElseThrow(() -> new RuntimeException("Note not found"));
		
		return note;
		
	}

	/**
	 * @param newNote
	 * @return
	 */
	public Note addNewNote(Note newNote) {
		
		log.info("Adding a new note in the database : {}", newNote);
		
		Note addedNote = noteRepository.insert(newNote);
		
		return addedNote;
		
	}

	/**
	 * @param id
	 * @return
	 */
	public Note updateExistingNote(Note updateNote) {
		
		log.info("Updating an existing note in the database : {}", updateNote);
		
		Note existingNote = getOneNoteById(updateNote.getId());
		
		if(existingNote.getPatId() != updateNote.getPatId()) {
			existingNote.setPatId(updateNote.getPatId());
		}
		
		if(existingNote.getPatient() != updateNote.getPatient()) {
			existingNote.setPatient(updateNote.getPatient());
		}
		
		if(existingNote.getNote() != updateNote.getNote()){
			existingNote.setNote(updateNote.getNote());
		}
		
		return noteRepository.save(existingNote);
		
	}

	/**
	 * @param id
	 * @return
	 */
	public boolean deleteExistingNote(String id) {
		
		log.info("Delete an existing note in the database with id : {}", id);
		
		if(noteRepository.existsById(id)) {
			
			noteRepository.deleteById(id);
			
			return true;
			
		}
		
		return false;
		
	}

	/**
	 * @param patientName
	 * @return
	 */
	public List<Note> getAllNotesDependingOfPatientName(String patientName) {
		
		log.info("Fetching all the notes for a patient with the name : {}", patientName);
		
		List<Note> patientNotes = noteRepository.findByPatient(patientName);
		
		return patientNotes;
		
	}
	
}
