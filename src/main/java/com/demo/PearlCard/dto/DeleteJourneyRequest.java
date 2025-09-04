package com.demo.PearlCard.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteJourneyRequest {

    @NotBlank(message = "User ID is required")
    @JsonProperty("userId")
    private String userId;
   
    @NotBlank(message = "Journey id is required to delete")
    @JsonProperty("journeyId")
    private Integer id;



}
