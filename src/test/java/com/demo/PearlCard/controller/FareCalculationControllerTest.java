package com.demo.PearlCard.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.demo.PearlCard.dto.FareCalculationResponse;
import com.demo.PearlCard.dto.JourneyRequest;
import com.demo.PearlCard.model.Journey;
import com.demo.PearlCard.service.FareCalculationService;
import com.demo.PearlCard.service.FareValidationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;




@ExtendWith(MockitoExtension.class)
 class FareCalculationControllerTest {

    @Mock
    private FareCalculationService fareCalculationService;

    @Mock
    private FareValidationService fareValidationService;

    @InjectMocks
    private FareCalculationController fareCalculationController;

    private MockMvc mockMvc;



    private ObjectMapper objectMapper;


    @BeforeEach 
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(fareCalculationController).build();


        objectMapper = new ObjectMapper();

    }


    @Test
    @DisplayName(" Should Calculate total fare for single journey successfully")
    void shouldCalculateTotalFareForSingleJourney() throws Exception {

        // step to arrange input for test 

        JourneyRequest request = createVaslidJourneyRequest();

        FareCalculationResponse response = createSingleJourneyResponse();

        doNothing().when(fareValidationService).validateJourneyRequest(any());
        when(fareCalculationService.calculateTotalJourneyFaresForUser(any())).thenReturn(response);


         // Act - Perform the request and capture response
    // do action and asset the response 
         mockMvc.perform(post("/api/v1/calculateTotalFare")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.timestamp").exists())
        .andExpect(jsonPath("$.data.totalFare").value(40))
        .andExpect(jsonPath("$.data.journeys").isArray())
        .andExpect(jsonPath("$.data.journeys.length()").value(1))
        .andExpect(jsonPath("$.data.journeys[0].fromStation").value("DOWNTOWN"))
        .andExpect(jsonPath("$.data.journeys[0].toStation").value("DOWNTOWN"))
        .andExpect(jsonPath("$.errors").doesNotExist());

        // Verify mocks were called
        verify(fareValidationService).validateJourneyRequest(any());
        verify(fareCalculationService).calculateTotalJourneyFaresForUser(any());
    }

    private JourneyRequest createVaslidJourneyRequest() {

        JourneyRequest request = new JourneyRequest();
        request.setUserId("USER123");

        List<Journey> journeys = new ArrayList<>();

        Journey journey = new Journey();
        journey.setFromStation("DOWNTOWN");
        journey.setToStation("DOWNTOWN");

        journeys.add(journey);
        request.setJourneys(journeys);

        return request;

    }
    private FareCalculationResponse createSingleJourneyResponse() {

        Journey journey = new Journey();

        journey.setJourneyId(1);
        journey.setFromStation("DOWNTOWN");
        journey.setToStation("DOWNTOWN");
        journey.setFromZone(1);
        journey.setToZone(1);
        journey.setFare(40);

        FareCalculationResponse response = new FareCalculationResponse();
        response.setJourneys(Collections.singletonList(journey)); 
        response.setTotalFare(40);
        
        return response;

    }



}
