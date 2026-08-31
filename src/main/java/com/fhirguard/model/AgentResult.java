package com.fhirguard.model;

import java.util.ArrayList;
import java.util.List;

public class AgentResult {

    private String agentName;

    private String finding;

    private double confidence;

    private List<String> evidence = new ArrayList<>();

    public AgentResult() {
    }

    public AgentResult(
            String agentName,
            String finding,
            double confidence,
            List<String> evidence) {

        this.agentName = agentName;
        this.finding = finding;
        this.confidence = confidence;
        this.evidence = evidence;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getFinding() {
        return finding;
    }

    public void setFinding(String finding) {
        this.finding = finding;
    }

    public double getConfidence() {
        return confidence;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }

    public List<String> getEvidence() {
        return evidence;
    }

    public void setEvidence(List<String> evidence) {
        this.evidence = evidence;
    }
}