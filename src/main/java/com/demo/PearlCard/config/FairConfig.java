package com.demo.PearlCard.config;

import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "fares")
@Getter
@Setter
public class FairConfig {

    private Map<String,Integer> zoneToFareMap;

    @Bean("zoneToFareMapping")
    public  Map<String, Integer> fareMap() {
    
      return this.zoneToFareMap;

}
   


}
