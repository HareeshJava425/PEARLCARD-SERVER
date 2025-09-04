package com.demo.PearlCard.service;

import java.util.Set;

public interface StationService {

    /**
     * Retrieves the zone for a given station name.
     * @param stationName The name of the station.
     * @return The zone number as an Integer.
     * @throws IllegalArgumentException if the station name is invalid or not found.
     */
    Integer getZoneByStation(String stationName);

    /**
     * Checks if a station exists in the service's data.
     * @param stationName The name of the station.
     * @return true if the station exists, false otherwise.
     */
    boolean stationExists(String stationName);

    /**
     * Retrieves a set of all stations belonging to a specific zone.
     * @param zone The zone number.
     * @return A Set of station names.
     */
    Set<String> getStationsByZone(Integer zone);

    /**
     * Retrieves a set containing the names of all available stations.
     * @return A Set of all station names.
     */
    Set<String> getStationsFromAllZones();

}
