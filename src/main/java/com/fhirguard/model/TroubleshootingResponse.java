package com.fhirguard.model;

import tools.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;

public class TroubleshootingResponse {

    private String resourceType;

    private int httpStatus;

    private String rootCause;

    private double confidence;

    private String recommendedFix;

    private JsonNode correctedPayload;

    private List<String> agentTrace = new ArrayList<>();

    private List<String> evidence = new ArrayList<>();

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getRootCause() {
        return rootCause;
    }

    public void setRootCause(String rootCause) {
        this.rootCause = rootCause;
    }

    public double getConfidence() {
        return confidence;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }

    public String getRecommendedFix() {
        return recommendedFix;
    }

    public void setRecommendedFix(String recommendedFix) {
        this.recommendedFix = recommendedFix;
    }

    public JsonNode getCorrectedPayload() {
        return correctedPayload;
    }

    public void setCorrectedPayload(JsonNode correctedPayload) {
        this.correctedPayload = correctedPayload;
    }

    public List<String> getAgentTrace() {
        return agentTrace;
    }

    public void setAgentTrace(List<String> agentTrace) {
        this.agentTrace = agentTrace;
    }

    public List<String> getEvidence() {
        return evidence;
    }

    public void setEvidence(List<String> evidence) {
        this.evidence = evidence;
    }
}