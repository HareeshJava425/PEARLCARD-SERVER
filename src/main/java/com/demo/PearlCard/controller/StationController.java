package com.demo.PearlCard.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.PearlCard.dto.APIResponse;
import com.demo.PearlCard.service.StationServiceImpl;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class StationController {

    @Autowired 
    private StationServiceImpl stationService;

    @GetMapping("/getAllStations")
    public ResponseEntity<APIResponse <Set<String>>> getAllStations() {

      Set<String> stations = this.stationService.getStationsFromAllZones();
      return ResponseEntity.ok().body(APIResponse.success(stations));


    }
}
