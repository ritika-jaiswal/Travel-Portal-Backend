package com.exaze.ritika.travel.portal.config;

import com.exaze.ritika.travel.portal.entities.TravelRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public TravelRequest travelRequest() {
        return new TravelRequest();
    }
}

