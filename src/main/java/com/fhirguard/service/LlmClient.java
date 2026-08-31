package com.fhirguard.service;

public interface LlmClient {

    String generate(
            String systemPrompt,
            String userPrompt
    );
}