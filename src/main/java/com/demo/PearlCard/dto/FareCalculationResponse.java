package com.demo.PearlCard.dto;

import java.util.List;

import com.demo.PearlCard.model.Journey;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FareCalculationResponse {


    @JsonProperty("journeys")
    private List<Journey> journeys;

    @JsonProperty("totalFare")
    private Integer totalFare;
    
    @JsonProperty("totalJourneys")
    private int totalJourneys;

}
