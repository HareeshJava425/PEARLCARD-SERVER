package com.demo.PearlCard.config;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "stations")
@Getter
@Setter
public class StationConfig {

    private Map<String,Integer> stationToZoneMap;

    @Bean("stationToZoneMapping")
    public Map<String,Integer> getStationToZoneMapping() {

        return this.stationToZoneMap;
    }


}
