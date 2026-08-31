package com.fhirguard.evaluation;

import tools.jackson.databind.JsonNode;

public class EvaluationCase {

    private String id;

    private String resourceType;

    private String description;

    private JsonNode request;

    private int httpStatus;

    private String errorMessage;

    private String expectedRootCause;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public JsonNode getRequest() {
        return request;
    }

    public void setRequest(JsonNode request) {
        this.request = request;
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

    public String getExpectedRootCause() {
        return expectedRootCause;
    }

    public void setExpectedRootCause(String expectedRootCause) {
        this.expectedRootCause = expectedRootCause;
    }
}