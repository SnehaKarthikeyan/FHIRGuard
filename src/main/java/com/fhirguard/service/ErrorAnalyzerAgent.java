package com.fhirguard.service;

import com.fhirguard.model.AgentResult;
import com.fhirguard.model.TroubleshootingRequest;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ErrorAnalyzerAgent {

    private final LlmClient llmClient;

    public ErrorAnalyzerAgent(
            LlmClient llmClient) {

        this.llmClient = llmClient;
    }

    public AgentResult analyze(
            TroubleshootingRequest request) {

        String systemPrompt = """
                You are the FHIR Error Analyzer.

                Analyze the supplied API failure.

                Identify:
                - resource type
                - HTTP status
                - error category
                - affected field if known

                Do not invent healthcare data.
                """;

        String userPrompt = """
                FHIR request:

                %s

                HTTP status:
                %d

                Error:
                %s
                """.formatted(
                request.getFhirRequest()
                        .toPrettyString(),
                request.getHttpStatus(),
                request.getErrorMessage()
        );

        String finding =
                llmClient.generate(
                        systemPrompt,
                        userPrompt
                );

        return new AgentResult(
                "Error Analyzer Agent",
                finding,
                0.85,
                List.of(
                        "FHIR request",
                        "HTTP status",
                        "Server error"
                )
        );
    }
}