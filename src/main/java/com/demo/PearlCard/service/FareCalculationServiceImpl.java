package com.demo.PearlCard.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.demo.PearlCard.dao.UserJourneyRepoImpl;
import com.demo.PearlCard.dto.FareCalculationResponse;
import com.demo.PearlCard.dto.JourneyRequest;
import com.demo.PearlCard.model.Journey;

@Service
public class FareCalculationServiceImpl implements FareCalculationService {

    @Autowired
    @Qualifier("zoneToFareMapping")
    private Map<String,Integer> fareMap;
    @Autowired
    private StationServiceImpl stationService;
    @Autowired
    private UserJourneyRepoImpl userJourneyStorage;

    @Autowired
    private SerialIdGenerator serialIdGenerator;



     /**
     * Calculate fare by station names
     */
    public Integer calculateFareByStations(String fromStation, String toStation) {
        try {
            Integer fromZone = stationService.getZoneByStation(fromStation);
            Integer toZone = stationService.getZoneByStation(toStation);
            return calculateFareByZones(fromZone, toZone);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid station: " + e.getMessage());
        }
    }

    /**
     * Calculate fare for a single journey using zones
     */
    public Integer calculateFareByZones(Integer fromZone, Integer toZone) {
        String key = fromZone + "-" + toZone;
        Integer zoneFare = fareMap.get(key);
        
        if (zoneFare == null) {
            throw new IllegalArgumentException("Invalid zone combination: " + key);
        }
        
        return zoneFare;
    }

    /**
     * Calculate daily fares for multiple journeys
     */
    public FareCalculationResponse calculateTotalJourneyFaresForUser(JourneyRequest request) {
        List<Journey> journeys = request.getJourneys();
        List<Journey> responses = new ArrayList<>();
        
        String userId = request.getUserId();
        Integer totalFare = 0;         
        
        for (int i = 0; i < journeys.size(); i++) {

            Journey journey = journeys.get(i);
            serialIdGenerator.resetCounter();
            try {
                // Get zones for stations
                Integer fromZone = stationService.getZoneByStation(journey.getFromStation());
                Integer toZone = stationService.getZoneByStation(journey.getToStation());
                 // Calculate fare
                 Integer fare = calculateFareByZones(fromZone, toZone);
                
                // Update journey with zone information
                journey.setFromZone(fromZone);
                journey.setToZone(toZone);
                journey.setTimestamp(stationService.getCurrentTimestamp());
                journey.setFare(fare);
                journey.setJourneyId(serialIdGenerator.getNextId());
                journey.setUserId(userId);
                
                responses.add(journey);
                
                totalFare += fare;
                
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException(
                    String.format("Journey %d: %s", i + 1, e.getMessage())
                );
            }
        }

        userJourneyStorage.replaceJourneysForUser(userId, journeys);

        FareCalculationResponse fareResponse = new FareCalculationResponse(responses, totalFare,journeys.size());
        
        return fareResponse;
    }


    public ResponseEntity<Integer> getTotalFareForTheDay(List<Journey> journeys) {

        if(journeys != null) {
            journeys.stream().mapToInt(journey -> {
                String key = journey.getFromZone()+ "-"+ journey.getToZone();
                return fareMap.getOrDefault(key, 0);
            }).sum();
            return ResponseEntity.ok(1);
        }
      return ResponseEntity.badRequest().body(0);

    }
    public Integer getTripFare(int fromZone, int toZone) {
        String key = fromZone+ "-"+ toZone;
        return fareMap.get(key);
    }

}
