package com.medilabo.mservice_risk.service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medilabo.mservice_risk.bean.NoteBean;
import com.medilabo.mservice_risk.bean.PatientBean;
import com.medilabo.mservice_risk.enums.KeyWords;
import com.medilabo.mservice_risk.enums.RiskLevel;
import com.medilabo.mservice_risk.proxy.MServiceNoteProxy;
import com.medilabo.mservice_risk.proxy.MServicePatientProxy;

/**
 * 
 */
@Service
public class RiskService {

	private final static Logger log = LogManager.getLogger(RiskService.class);
	final int checkAge = 30;
	final KeyWords[] keyWords = KeyWords.values();

	@Autowired
	MServicePatientProxy patientProxy;

	@Autowired
	MServiceNoteProxy noteProxy;

	/**
	 * @param id
	 * @return
	 */
	public RiskLevel calculateRiskLevel(int id) {

		PatientBean patient = patientProxy.getOnePatientById(id);
		List<NoteBean> patientNotes = noteProxy.getAllNotesDependingOfPatientName(patient.getName());

		List<String> notes = getAllNotesForPatient(patientNotes);

		int countKeyWords = countKeyWordsInNotes(notes);

		int age = calculateAge(patient.getBirthDate());
		
		return determinateRiskLevel(age, countKeyWords, patient);

	}

	/**
	 * @param patientNotes
	 * @return
	 */
	private List<String> getAllNotesForPatient(List<NoteBean> patientNotes){
		
		List<String> notes = new ArrayList<>();
		
		for(NoteBean patientNote : patientNotes) {

			String note = patientNote.getNote();

			notes.add(note);

		}
		
		return notes;
		
	}
	
	/**
	 * @param notes
	 * @return
	 */
	private int countKeyWordsInNotes(List<String> notes) {
		
		int count = 0;
		
		for(String note : notes) {

			for(KeyWords keyWord : keyWords) {

				if(note.toLowerCase().contains(keyWord.getMot().toLowerCase())) {

					count++;

				}

			}

		}
		
		return count;
		
	}
	
	/**
	 * @param age
	 * @param countKeyWords
	 * @param patient
	 * @return
	 */
	private RiskLevel determinateRiskLevel(int age, int countKeyWords, PatientBean patient) {

		if(countKeyWords < 2) {
			
			return RiskLevel.NONE;
			
		} else {
			
			if(checkAgeUnderThirty(age)) {
				
				if(patient.getGender().equals("M")) {
				
					return levelRiskForMaleUnderThirty(countKeyWords);
					
				} else if(patient.getGender().equals("F")) {
					
					return levelRiskForFemaleUnderThirty(countKeyWords);
					
				}	
				
			} else {
				
				return levelRiskOverThirty(countKeyWords);
				
			}
			
		}
		
		return null;
		
	}
	
	/**
	 * @param age
	 * @return
	 */
	private boolean checkAgeUnderThirty(int age) {
		
		if(age <= checkAge) {
			
			return true;
			
		}
		
		return false;
	}

	/**
	 * @param countKeyWords
	 * @return
	 */
	private RiskLevel levelRiskForFemaleUnderThirty(int countKeyWords) {

		if(countKeyWords >= 3 && countKeyWords < 7) {
			
			return RiskLevel.IN_DANGER;
			
		} else if(countKeyWords >= 7) {
			
			return RiskLevel.EARLY_ONSET;
			
		}
		
		return null;
		
	}

	/**
	 * @param countKeyWords
	 * @return
	 */
	private RiskLevel levelRiskForMaleUnderThirty(int countKeyWords) {

		if(countKeyWords >= 3 && countKeyWords < 5) {
			
			return RiskLevel.IN_DANGER;
			
		} else if(countKeyWords >= 5) {
			
			return RiskLevel.EARLY_ONSET;
			
		}
		
		return null;
		
	}
	
	/**
	 * @param countKeyWords
	 * @return
	 */
	private RiskLevel levelRiskOverThirty(int countKeyWords) {
		
		if(countKeyWords >= 2 && countKeyWords <= 5) {
			
			return RiskLevel.BORDERLINE;
			
		} else if(countKeyWords >= 6 && countKeyWords <= 7) {
			
			return RiskLevel.IN_DANGER;
			
		} else if(countKeyWords >= 8) {
			
			return RiskLevel.EARLY_ONSET;
		
		}
		
		return null;	
		
	}

	/**
	 * @param birthdate
	 * @return
	 */
	private int calculateAge(String birthdate) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        LocalDate dateNaissance = LocalDate.parse(birthdate, formatter);
        LocalDate dateActuelle = LocalDate.now();
        
        return Period.between(dateNaissance, dateActuelle).getYears();

	}

}
