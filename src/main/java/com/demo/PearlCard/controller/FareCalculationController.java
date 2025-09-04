package com.demo.PearlCard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.demo.PearlCard.dao.UserJourneyRepoImpl;
import com.demo.PearlCard.dto.APIResponse;
import com.demo.PearlCard.dto.DeleteJourneyRequest;
import com.demo.PearlCard.dto.FareCalculationResponse;
import com.demo.PearlCard.dto.JourneyRequest;
import com.demo.PearlCard.model.Journey;
import com.demo.PearlCard.service.FareCalculationServiceImpl;
import com.demo.PearlCard.service.FareValidation;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class FareCalculationController {

    @Autowired
    private FareCalculationServiceImpl fareCalculationService;

    @Autowired
    private UserJourneyRepoImpl journeyStorageService;

    @Autowired
    private FareValidation fareValidation;

    /**
     * Get fare for a single trip between zones
     * GET /api/v1/getFare?fromZone=1&toZone=2
     */
    @GetMapping("/getFare")
    public ResponseEntity<APIResponse<Integer>> getFare(
            @RequestParam @Min(value = 1, message = "From zone must be positive") int fromZone,
            @RequestParam @Min(value = 1, message = "To zone must be positive") int toZone) {

        try {
            Integer fare = fareCalculationService.getTripFare(fromZone, toZone);

            if (fare != null && fare > 0) {
                return ResponseEntity.ok(
                    APIResponse.success("Fare retrieved successfully", fare)
                );
            } else {
                return ResponseEntity.badRequest().body(
                    APIResponse.error("Invalid journey from zone " + fromZone + " to zone " + toZone)
                );
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                APIResponse.error("An internal error occurred: " + e.getMessage())
            );
        }
    }

    /**
     * Calculate total fare for multiple journeys
     * POST /api/v1/calculateTotalFare
     */
    @PostMapping("/calculateTotalFare")
    public ResponseEntity<APIResponse<FareCalculationResponse>> calculateTotalFare(
            @Valid @RequestBody JourneyRequest request) {
        
        try {

            // Validate the journey request
            fareValidation.validateJourneyRequest(request);

            // Calculate fares and store journeys
            FareCalculationResponse response = fareCalculationService.calculateTotalJourneyFaresForUser(request);

            return ResponseEntity.ok(
                APIResponse.success("Fares calculated successfully", response)
            );

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(
                APIResponse.error("Invalid request: " + e.getMessage())
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                APIResponse.error("An error occurred while calculating fares: " + e.getMessage())
            );
        }
    }

    /**
     * Delete a journey by ID and return updated journey list
     * DELETE /api/v1/journeys/{id}
     */
    @DeleteMapping("/journeys")
    public ResponseEntity<APIResponse<FareCalculationResponse>> deleteJourney(
            @RequestBody DeleteJourneyRequest request) {

        try {
            // Check if journey exists
            Journey existingJourney = journeyStorageService.getJourneyForUser(request.getUserId(),request.getId());
            if (existingJourney == null) {
                return ResponseEntity.ok(
                    APIResponse.error("Journey with id " + request.getId() + " not found")
                );
            }
            // Remove the journey
            boolean removed = journeyStorageService.removeJourneyById(request.getUserId(), request.getId());

            if (removed) {
                // Get updated journey state
                FareCalculationResponse response = new FareCalculationResponse();
                response.setJourneys(journeyStorageService.getAllJourneysForUser(request.getUserId()));
                response.setTotalFare(journeyStorageService.getTotalFareForUser(request.getUserId()));
                response.setTotalJourneys(journeyStorageService.getJourneyCountForUser(request.getUserId()));
                

                return ResponseEntity.ok(
                    APIResponse.success("Journey with id " + request.getId() + " removed successfully", response)
                );
            } else {
                return ResponseEntity.ok(
                    APIResponse.error("Journey with id " + request.getId() + " could not be removed")
                );
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                APIResponse.error("An error occurred internally: " + e.getMessage())
            );
        }
    }

    /**
     * Health check endpoint
     * GET /api/v1/health
     */
    @GetMapping("/health")
    public ResponseEntity<APIResponse<String>> healthCheck() {
        return ResponseEntity.ok(
            APIResponse.success("PearlCard Fare Calculation API is running", "OK")
        );
    }
}