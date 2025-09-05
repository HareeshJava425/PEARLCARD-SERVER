package com.demo.PearlCard.service;

import com.demo.PearlCard.dto.JourneyRequest;


public interface FareValidationService {

    /**
     * Validate journey request
     */
    public void validateJourneyRequest(JourneyRequest request) ;

    
} 