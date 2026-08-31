package com.fhirguard.evaluation;

public class EvaluationResult {

    private String caseId;

    private String expectedRootCause;

    private String actualRootCause;

    private boolean correct;

    private long executionTimeMs;

    private String system;

    public EvaluationResult(
            String caseId,
            String expectedRootCause,
            String actualRootCause,
            boolean correct,
            long executionTimeMs,
            String system) {

        this.caseId = caseId;
        this.expectedRootCause = expectedRootCause;
        this.actualRootCause = actualRootCause;
        this.correct = correct;
        this.executionTimeMs = executionTimeMs;
        this.system = system;
    }

    public String getCaseId() {
        return caseId;
    }

    public String getExpectedRootCause() {
        return expectedRootCause;
    }

    public String getActualRootCause() {
        return actualRootCause;
    }

    public boolean isCorrect() {
        return correct;
    }

    public long getExecutionTimeMs() {
        return executionTimeMs;
    }

    public String getSystem() {
        return system;
    }
}