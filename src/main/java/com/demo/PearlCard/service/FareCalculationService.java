package com.demo.PearlCard.service;

import com.demo.PearlCard.dto.FareCalculationResponse;
import com.demo.PearlCard.dto.JourneyRequest;

public interface FareCalculationService {

    /**
     * Calculates the fare for a single journey based on station names.
     * @param fromStation The name of the starting station.
     * @param toStation The name of the destination station.
     * @return The calculated fare as an Integer.
     */
    Integer calculateFareByStations(String fromStation, String toStation);

    /**
     * Calculates the fare for a single journey based on zone numbers.
     * @param fromZone The zone number of the starting station.
     * @param toZone The zone number of the destination station.
     * @return The calculated fare as an Integer.
     */
    Integer calculateFareByZones(Integer fromZone, Integer toZone);

    /**
     * Calculates fares for a list of journeys, persists them, and returns a summary.
     * @param request A JourneyRequest containing a list of journeys and a user ID.
     * @return A FareCalculationResponse object with journey details and the total fare.
     */
    FareCalculationResponse calculateTotalJourneyFaresForUser(JourneyRequest request);

    

}
