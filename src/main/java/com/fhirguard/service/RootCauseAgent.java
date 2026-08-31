package com.fhirguard.service;

import com.fhirguard.model.AgentResult;
import com.fhirguard.model.TroubleshootingRequest;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RootCauseAgent {

    private final LlmClient llmClient;

    public RootCauseAgent(
            LlmClient llmClient) {

        this.llmClient = llmClient;
    }

    public AgentResult determine(
            TroubleshootingRequest request,
            AgentResult errorAnalysis,
            AgentResult validation) {

        List<String> evidence =
                new ArrayList<>();

        evidence.addAll(
                errorAnalysis.getEvidence()
        );

        evidence.addAll(
                validation.getEvidence()
        );

        String systemPrompt = """
                You are the root-cause agent in a
                FHIR troubleshooting workflow.

                Determine the most likely cause using
                only the supplied evidence.

                Give highest priority to:
                1. FHIR validator evidence
                2. Server error
                3. Error analyzer

                Classify as:
                CONFIRMED
                LIKELY
                POSSIBLE
                UNKNOWN

                Never invent patient information.
                """;

        String userPrompt = """
                ORIGINAL FHIR REQUEST:

                %s

                SERVER ERROR:

                %s

                ERROR ANALYZER:

                %s

                FHIR VALIDATOR:

                %s
                """.formatted(
                request.getFhirRequest()
                        .toPrettyString(),
                request.getErrorMessage(),
                errorAnalysis.getFinding(),
                validation.getFinding()
        );

        String finding =
                llmClient.generate(
                        systemPrompt,
                        userPrompt
                );

        return new AgentResult(
                "Root Cause Agent",
                finding,
                0.90,
                evidence
        );
    }
}