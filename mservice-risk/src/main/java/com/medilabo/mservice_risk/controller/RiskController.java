package com.medilabo.mservice_risk.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medilabo.mservice_risk.enums.RiskLevel;
import com.medilabo.mservice_risk.service.RiskService;

/**
 * Controller for managing http request to calculate the risk level of one patient
 */
@RestController
@RequestMapping("/api")
public class RiskController {

	private final static Logger log = LogManager.getLogger(RiskController.class);

	@Autowired
	RiskService riskService;

	/**
	 * Calculate the risk level of one patient and return a message with the riskLevel
	 * 
	 * @param id of the patient
	 * @return message with riskLevel
	 */
	@GetMapping("/risk/{id}")
	public ResponseEntity<?> getRiskLevelOfOnePatient(@PathVariable("id") int id){

		log.info("Trying to calculate the risk level of one patient with the id : {} ", id);
		
		RiskLevel riskLevel = riskService.calculateRiskLevel(id);

		if(riskLevel == null) {

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error during calculate risk level");

		}

		return ResponseEntity.status(HttpStatus.OK).body(messageForRiskLevel(riskLevel));

	}
	
	/**
	 * Make a special message with the riskLevel for every riskLevel in the enumeration class
	 * 
	 * @param riskLevel
	 * @return message with riskLevel
	 */
	private String messageForRiskLevel(RiskLevel riskLevel) {
		
		switch(riskLevel) {
		
			case NONE:
				return "Aucun risque (" + riskLevel + ")";
				
			case BORDERLINE:
				return "Risque modéré (" + riskLevel + ")";
			
			case IN_DANGER:
				return "Risque élevé (" + riskLevel + ")";
				
			case EARLY_ONSET:
				return "Apparition précoce (" + riskLevel + ")";
			
			default:
				return "Niveau de risque inconnu";
				
		}
		
	}

}
