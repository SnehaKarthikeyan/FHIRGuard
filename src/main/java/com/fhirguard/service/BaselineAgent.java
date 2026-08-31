package com.fhirguard.service;

import com.fhirguard.model.AgentResult;
import com.fhirguard.model.TroubleshootingRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BaselineAgent {

    private final LlmClient llmClient;

    public BaselineAgent(LlmClient llmClient) {
        this.llmClient = llmClient;
    }

    public AgentResult analyze(
            TroubleshootingRequest request) {

        String systemPrompt = """
                You are a FHIR expert.

                Analyze the provided FHIR request and
                API error.

                Identify the likely root cause and
                recommend a fix.

                Use only the supplied information.
                """;

        String userPrompt = """
                FHIR REQUEST:

                %s

                HTTP STATUS:

                %d

                ERROR:

                %s
                """.formatted(
                request.getFhirRequest().toPrettyString(),
                request.getHttpStatus(),
                request.getErrorMessage()
        );

        String result =
                llmClient.generate(
                        systemPrompt,
                        userPrompt
                );

        return new AgentResult(
                "Baseline Single LLM",
                result,
                0.50,
                List.of(
                        "FHIR request",
                        "HTTP status",
                        "Server error"
                )
        );
    }
}