package com.demo.PearlCard.dto;

import java.util.List;

import com.demo.PearlCard.model.Journey;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JourneyRequest {

    @NotBlank(message = "User ID from Journey Request is required")
    @JsonProperty("userId")  
    private String userId;
    
    @NotEmpty(message = "At least one journey from journey request is required")
    @JsonProperty("journeys") 
    private List<Journey> journeys;

}
