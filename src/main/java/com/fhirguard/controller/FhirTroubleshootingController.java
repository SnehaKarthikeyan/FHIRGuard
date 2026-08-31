package com.fhirguard.controller;

import com.fhirguard.model.TroubleshootingRequest;
import com.fhirguard.model.TroubleshootingResponse;
import com.fhirguard.service.FhirTroubleshootingService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class FhirTroubleshootingController {

    private final FhirTroubleshootingService service;

    public FhirTroubleshootingController(
            FhirTroubleshootingService service) {

        this.service = service;
    }

    @PostMapping("/analyze")
    public ResponseEntity<TroubleshootingResponse> analyze(
            @RequestBody TroubleshootingRequest request) {

        TroubleshootingResponse response =
                service.analyze(request);

        return ResponseEntity.ok(response);
    }
}