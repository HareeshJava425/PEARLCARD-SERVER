package com.demo.PearlCard.model;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

public class Commuter {

    @Getter
    @Setter
    List<Journey> journeys;

    @Getter
    @Setter
    private int totalPricePerDay;

    @Getter
    @Setter
    private Map<Journey,String> mapOfTrips;

    public Commuter(List<Journey> journeys) {
        this.journeys = journeys;
    }


}
