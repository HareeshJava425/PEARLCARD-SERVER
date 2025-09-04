package com.demo.PearlCard.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class StationServiceImpl implements StationService{

    private Map<String, Integer> stationToZoneMap;
    
    @Autowired
    public StationServiceImpl(@Qualifier("stationToZoneMapping") Map<String, Integer> stationToZoneMapping) {
        this.stationToZoneMap = stationToZoneMapping;
    }

     /**
     * Get zone for a station by station name
     */
    public Integer getZoneByStation(String stationName) {
        if (stationName == null || stationName.trim().isEmpty()) {
            throw new IllegalArgumentException("Station name cannot be null or empty");
        }
        
        String normalizedStation = stationName.trim().toUpperCase();
        Integer zone = stationToZoneMap.get(normalizedStation);
        
        if (zone == null) {
            throw new IllegalArgumentException("Station not found: " + stationName);
        }
        
        return zone;
    }

     /**
     * Check if station exists
     */
    public boolean stationExists(String stationName) {
        if (stationName == null || stationName.trim().isEmpty()) {
            return false;
        }
        return stationToZoneMap.containsKey(stationName.trim().toUpperCase());
    }

    /**
     * Getting statuions by entering zone 
     * @param zone
     * @return
     */
    public Set<String> getStationsByZone(Integer zone) {
        return stationToZoneMap.entrySet().stream().filter(
            entry -> entry.getValue().equals(zone)).map(Map.Entry::getKey)
            .collect(Collectors.toSet());  
    }

     
    /**
     * Get current timestamp
     */
    public String getCurrentTimestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    public Set<String> getStationsFromAllZones() {
        return stationToZoneMap.keySet();
    }

  



}
