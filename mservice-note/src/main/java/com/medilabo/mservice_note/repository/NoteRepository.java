package com.medilabo.mservice_note.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.medilabo.mservice_note.model.Note;

/**
 * 
 */
public interface NoteRepository extends MongoRepository<Note, String> {

}
