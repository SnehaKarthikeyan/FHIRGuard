package com.fhirguard.service;

import com.fhirguard.model.AgentResult;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VerificationAgent {

    private final LlmClient llmClient;

    public VerificationAgent(
            LlmClient llmClient) {

        this.llmClient = llmClient;
    }

    public AgentResult verify(
            AgentResult rootCause,
            AgentResult validation) {

        String systemPrompt = """
                You are an independent verification agent.

                Check whether the proposed FHIR root cause
                is actually supported by the validator evidence.

                Return either:
                VERIFIED
                or
                NOT_VERIFIED

                Explain briefly.
                """;

        String userPrompt = """
                PROPOSED ROOT CAUSE:

                %s

                VALIDATION EVIDENCE:

                %s
                """.formatted(
                rootCause.getFinding(),
                validation.getFinding()
        );

        String result =
                llmClient.generate(
                        systemPrompt,
                        userPrompt
                );

        return new AgentResult(
                "Verification Agent",
                result,
                0.95,
                List.of(
                        "Root cause result",
                        "FHIR validation result"
                )
        );
    }
}