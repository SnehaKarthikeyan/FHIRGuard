package com.fhirguard.model;

import tools.jackson.databind.JsonNode;

public class TroubleshootingRequest {

    private JsonNode fhirRequest;

    private int httpStatus;

    private String errorMessage;

    public JsonNode getFhirRequest() {
        return fhirRequest;
    }

    public void setFhirRequest(JsonNode fhirRequest) {
        this.fhirRequest = fhirRequest;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}