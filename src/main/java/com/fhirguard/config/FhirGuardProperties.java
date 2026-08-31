package com.fhirguard.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "fhirguard")
public class FhirGuardProperties {

    private boolean llmEnabled = false;

    private String openAiModel = "gpt-5.6";

    public boolean isLlmEnabled() {
        return llmEnabled;
    }

    public void setLlmEnabled(boolean llmEnabled) {
        this.llmEnabled = llmEnabled;
    }

    public String getOpenAiModel() {
        return openAiModel;
    }

    public void setOpenAiModel(String openAiModel) {
        this.openAiModel = openAiModel;
    }
}