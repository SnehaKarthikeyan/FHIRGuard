package com.fhirguard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.fhirguard.config.FhirGuardProperties;

@SpringBootApplication
@EnableConfigurationProperties(FhirGuardProperties.class)
public class FhirGuardProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(
                FhirGuardProjectApplication.class,
                args
        );
    }
}