package com.fhirguard.evaluation;

import com.fhirguard.model.TroubleshootingRequest;
import com.fhirguard.model.TroubleshootingResponse;
import com.fhirguard.service.FhirTroubleshootingService;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class EvaluationService {

    private final ObjectMapper objectMapper;

    private final FhirTroubleshootingService troubleshootingService;

    public EvaluationService(
            ObjectMapper objectMapper,
            FhirTroubleshootingService troubleshootingService) {

        this.objectMapper = objectMapper;
        this.troubleshootingService =
                troubleshootingService;
    }

    public List<EvaluationResult> runFhirGuard() {

        List<EvaluationCase> cases =
                loadCases();

        List<EvaluationResult> results =
                new ArrayList<>();

        for (EvaluationCase testCase : cases) {

            TroubleshootingRequest request =
                    new TroubleshootingRequest();

            request.setFhirRequest(
                    testCase.getRequest()
            );

            request.setHttpStatus(
                    testCase.getHttpStatus()
            );

            request.setErrorMessage(
                    testCase.getErrorMessage()
            );

            long start =
                    System.currentTimeMillis();

            TroubleshootingResponse response =
                    troubleshootingService
                            .analyze(request);

            long executionTime =
                    System.currentTimeMillis()
                            - start;

            boolean correct =
                    evaluateRootCause(
                            testCase
                                    .getExpectedRootCause(),
                            response.getRootCause()
                    );

            results.add(
                    new EvaluationResult(
                            testCase.getId(),
                            testCase
                                    .getExpectedRootCause(),
                            response.getRootCause(),
                            correct,
                            executionTime,
                            "FHIRGuard"
                    )
            );
        }

        return results;
    }

    private List<EvaluationCase> loadCases() {

        try {

            ClassPathResource resource =
                    new ClassPathResource(
                            "evaluation/test_cases.json"
                    );

            try (InputStream inputStream =
                         resource.getInputStream()) {

                return objectMapper.readValue(
                        inputStream,
                        new TypeReference<List<EvaluationCase>>() {
                        }
                );
            }

        } catch (Exception e) {

            throw new RuntimeException(
                    "Could not load evaluation cases.",
                    e
            );
        }
    }

    private boolean evaluateRootCause(
            String expected,
            String actual) {

        if (expected == null ||
                actual == null) {

            return false;
        }

        return actual
                .toLowerCase()
                .contains(
                        expected.toLowerCase()
                );
    }
}