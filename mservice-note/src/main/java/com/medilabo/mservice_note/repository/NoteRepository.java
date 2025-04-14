package com.medilabo.mservice_note.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.medilabo.mservice_note.model.Note;

/**
 * Interface ton interact with the MangoDB database
 */
public interface NoteRepository extends MongoRepository<Note, String> {

	public List<Note> findByPatient(String patientName);
	
}
