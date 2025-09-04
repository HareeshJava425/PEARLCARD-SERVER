package com.demo.PearlCard.dao;

import java.util.List;

import com.demo.PearlCard.model.Journey;

public interface UserJourneyRepo {

    void replaceJourneysForUser(String userId, List<Journey> journeys);
    List<Journey> getAllJourneysForUser(String userId);
    Integer getTotalFareForUser(String userId);
    boolean removeJourneyById(String userId, Integer id);
    Integer getJourneyCountForUser(String userId);
    Journey getJourneyForUser(String userId, Integer id);
    void clearAllDataForUser(String userId);

}
