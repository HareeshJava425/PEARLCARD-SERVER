package com.demo.PearlCard.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Journey {

    @Positive(message = "Fare must be positive")
    private Integer journeyId;

    @NotBlank(message = "From station is required")
    @JsonProperty("fromStation")
    private String fromStation;
    
    @NotBlank(message = "To station is required")
    @JsonProperty("toStation")
    private String toStation;

    @JsonProperty("userId")
    //@NotBlank(message = "User ID is required")
    private String userId;

    //@NotBlank(message = "From zone is required")
    @Pattern(regexp = "^[1-9]\\d*$", message = "From zone must be a positive integer")
    private Integer fromZone;

   // @NotBlank(message = "To zone is required")
    @Pattern(regexp = "^[1-9]\\d*$", message = "To zone must be a positive integer")
    private Integer toZone;
        
    @JsonProperty("fare")
    private Integer fare; // Changed to Integer

    @JsonProperty("timestamp")
    private String timestamp;
}
