package com.demo.PearlCard.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

import com.demo.PearlCard.model.Journey;

@Service
public class UserJourneyRepoImpl implements UserJourneyRepo {

    private final Map<String, List<Journey>> userJourneys = new ConcurrentHashMap<>();
    private final Map<String, AtomicInteger> userCounters = new ConcurrentHashMap<>();


    public void replaceJourneysForUser(String userId, List<Journey> journeys) {
        // Initialize user storage if not exists
        userJourneys.computeIfAbsent(userId, k -> new CopyOnWriteArrayList<>());
        userCounters.computeIfAbsent(userId, k -> new AtomicInteger(0));  

        List<Journey> userJourneyList = userJourneys.get(userId);
        AtomicInteger counter = userCounters.get(userId);   
        
        userJourneyList.clear();
        counter.set(0); // Reset counter to 0


        // Assign sequential IDs and add journeys
        for (Journey journey : journeys) {
            journey.setJourneyId(counter.incrementAndGet());
            userJourneyList.add(journey);
        }
        System.out.println("user" + userId + " journey list "+userJourneyList);
        System.out.println(" counter "+ counter);
        
    }

    /**
     * Get all journeys for a specific user
     */
    public List<Journey> getAllJourneysForUser(String userId) {
        List<Journey> userJourneyList = userJourneys.get(userId);
        return userJourneyList != null ? new ArrayList<>(userJourneyList) : new ArrayList<>();
    }

    /**
     * Calculate total fare for a specific user
     */
    public Integer getTotalFareForUser(String userId) {
        List<Journey> userJourneyList = userJourneys.get(userId);
        if (userJourneyList == null) {
            return 0;
        }
        
        return userJourneyList.stream()
                .mapToInt(journey -> journey.getFare()!= null ? journey.getFare() : 0)
                .sum();
      }

    public boolean removeJourneyById(String userId,Integer id) {

        List<Journey> userJourneyList = userJourneys.get(userId);
        if(userJourneyList == null) {
            return false;
        }
        boolean removed = userJourneyList.removeIf(journey -> journey.getJourneyId().equals(id));

        if (removed) {
            // Re-sequence IDs to maintain 1, 2, 3... order
            resequenceJourneyIdsForUser(userId);
        }
        return removed;
    }

    /**
     * Get journey count for a specific user
     */
    public Integer getJourneyCountForUser(String userId) {
        List<Journey> userJourneyList = userJourneys.get(userId);
        return userJourneyList != null ? userJourneyList.size() : 0;
    }

    private void resequenceJourneyIdsForUser(String userId) {
        List<Journey> userJourneyList = userJourneys.get(userId);
        if (userJourneyList != null) {
            for (int i = 0; i < userJourneyList.size(); i++) {
                userJourneyList.get(i).setJourneyId(i+1);
            }

                // Update the counter to reflect the new highest ID
            AtomicInteger counter = userCounters.get(userId);
            if (counter != null) {
                counter.set(userJourneyList.size());
            }
        }
    }

    public Journey getJourneyForUser(String userId, Integer id) {
        
            // Validate input parameters
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }
        
        if (id <= 0) {
            throw new IllegalArgumentException("Journey ID must be positive");
        }
    
        // Get user's journey list from storage
        List<Journey> currentUserJourneys = userJourneys.get(userId);
        
        // If user has no journeys, return null
        if (userJourneys == null || userJourneys.isEmpty()) {
            return null;
        }
    
        // Find and return the journey with matching ID
        return currentUserJourneys.stream()
                .filter(journey -> journey.getJourneyId() != null && journey.getJourneyId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void clearAllDataForUser(String userId) {
         userJourneys.remove(userId);
    }

}
