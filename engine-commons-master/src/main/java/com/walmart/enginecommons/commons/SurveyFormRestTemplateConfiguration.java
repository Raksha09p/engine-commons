package com.walmart.enginecommons.commons;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class SurveyFormRestTemplateConfiguration {

    @Bean
    public RestTemplate restTemplate()
    {
        return new RestTemplate();
    }
}
