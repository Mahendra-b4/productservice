package com.example.product.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppicationConfiguration {
//    Spring IOC container
    @Bean
    public RestTemplate createRestTemplate(){
        return new RestTemplate();
    }
}
