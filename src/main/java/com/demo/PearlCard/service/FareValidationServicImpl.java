package com.demo.PearlCard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.PearlCard.dao.UserJourneyRepo;

import com.demo.PearlCard.dto.JourneyRequest;
import com.demo.PearlCard.model.Journey;

@Service
public class FareValidationServicImpl implements FareValidationService {

    @Autowired
    private UserJourneyRepo userJourneyStorage;

    @Autowired
    private StationServiceImpl stationService;

    /**
     * Validate journey request
     */
    public void validateJourneyRequest(JourneyRequest request) {
        if (request.getJourneys() == null || request.getJourneys().isEmpty()) {
            throw new IllegalArgumentException("At least one journey is required");
        }
        
        if (request.getJourneys().size() > 20) {
            throw new IllegalArgumentException("Maximum 20 journeys allowed per day");
        }
        if (userJourneyStorage.getAllJourneysForUser(request.getUserId()).size() >= 20) {
            throw new IllegalArgumentException("Maximum 20 journeys allowed per day");
        }
        
        for (int i = 0; i < request.getJourneys().size(); i++) {
            Journey journey = request.getJourneys().get(i);
            
            if (journey.getFromStation() == null || journey.getFromStation().trim().isEmpty()) {
                throw new IllegalArgumentException(String.format("Journey %d: From station is required", i + 1));
            }
            
            if (journey.getToStation() == null || journey.getToStation().trim().isEmpty()) {
                throw new IllegalArgumentException(String.format("Journey %d: To station is required", i + 1));
            }
            
            // Validate stations exist
            if (!stationService.stationExists(journey.getFromStation())) {
                throw new IllegalArgumentException(String.format("Journey %d: From station '%s' not found", i + 1, journey.getFromStation()));
            }
            
            if (!stationService.stationExists(journey.getToStation())) {
                throw new IllegalArgumentException(String.format("Journey %d: To station '%s' not found", i + 1, journey.getToStation()));
            }
        }
    }
    

}
