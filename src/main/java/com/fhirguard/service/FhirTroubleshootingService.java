package com.fhirguard.service;

import com.fhirguard.config.FhirGuardProperties;
import com.fhirguard.model.AgentResult;
import com.fhirguard.model.TroubleshootingRequest;
import com.fhirguard.model.TroubleshootingResponse;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FhirTroubleshootingService {

    private final ErrorAnalyzerAgent errorAnalyzerAgent;
    private final FhirValidatorAgent fhirValidatorAgent;
    private final RootCauseAgent rootCauseAgent;
    private final VerificationAgent verificationAgent;
    private final FixRecommendationAgent fixRecommendationAgent;
    private final LocalTroubleshootingAgent localTroubleshootingAgent;
    private final FhirGuardProperties properties;

    public FhirTroubleshootingService(
            ErrorAnalyzerAgent errorAnalyzerAgent,
            FhirValidatorAgent fhirValidatorAgent,
            RootCauseAgent rootCauseAgent,
            VerificationAgent verificationAgent,
            FixRecommendationAgent fixRecommendationAgent,
            LocalTroubleshootingAgent localTroubleshootingAgent,
            FhirGuardProperties properties) {

        this.errorAnalyzerAgent = errorAnalyzerAgent;
        this.fhirValidatorAgent = fhirValidatorAgent;
        this.rootCauseAgent = rootCauseAgent;
        this.verificationAgent = verificationAgent;
        this.fixRecommendationAgent = fixRecommendationAgent;
        this.localTroubleshootingAgent = localTroubleshootingAgent;
        this.properties = properties;
    }

    public TroubleshootingResponse analyze(
            TroubleshootingRequest request) {

        TroubleshootingResponse response =
                new TroubleshootingResponse();

        /*
         * Basic request information
         */
        String resourceType =
                request.getFhirRequest()
                        .path("resourceType")
                        .asText("Unknown");

        response.setResourceType(resourceType);

        response.setHttpStatus(
                request.getHttpStatus()
        );

        /*
         * FHIR validation always runs.
         */
        AgentResult validation =
                fhirValidatorAgent.validate(request);

        AgentResult errorAnalysis;
        AgentResult rootCause;
        AgentResult verification;

        /*
         * =====================================================
         * LLM MODE
         * =====================================================
         */
        if (properties.isLlmEnabled()) {

            errorAnalysis =
                    errorAnalyzerAgent.analyze(request);

            rootCause =
                    rootCauseAgent.determine(
                            request,
                            errorAnalysis,
                            validation
                    );

            verification =
                    verificationAgent.verify(
                            rootCause,
                            validation
                    );

        }

        /*
         * =====================================================
         * LOCAL MODE
         * =====================================================
         */
        else {

            /*
             * Run local analysis ONLY ONCE.
             */
            errorAnalysis =
                    localTroubleshootingAgent.analyze(request);

            /*
             * Use the same analysis as the root-cause
             * result instead of calling the same agent again.
             */
            rootCause =
                    new AgentResult(
                            "Local Root Cause Agent",
                            errorAnalysis.getFinding(),
                            errorAnalysis.getConfidence(),
                            new ArrayList<>()
                    );

            /*
             * Local verification.
             */
            verification =
                    new AgentResult(
                            "Local Verification Agent",
                            "VERIFIED_LOCAL: Diagnosis is based on the " +
                            "server error, HTTP status and local rules.",
                            0.85,
                            List.of(
                                    "Server error",
                                    "HTTP status",
                                    "Local troubleshooting rules"
                            )
                    );
        }

        /*
         * =====================================================
         * FIX RECOMMENDATION
         * =====================================================
         */
        var correctedPayload =
                fixRecommendationAgent
                        .generateCorrectedPayload(
                                request,
                                rootCause
                        );

        /*
         * =====================================================
         * FINAL RESPONSE
         * =====================================================
         */
        response.setRootCause(
                rootCause.getFinding()
        );

        response.setConfidence(
                rootCause.getConfidence()
        );

        response.setCorrectedPayload(
                correctedPayload
        );

        response.setRecommendedFix(
                buildRecommendation(rootCause)
        );

        /*
         * =====================================================
         * AGENT TRACE
         * =====================================================
         */
        List<String> trace =
                new ArrayList<>();

        trace.add(
                errorAnalysis.getAgentName()
                        + " -> "
                        + errorAnalysis.getFinding()
        );

        trace.add(
                validation.getAgentName()
                        + " -> "
                        + validation.getFinding()
        );

        trace.add(
                rootCause.getAgentName()
                        + " -> "
                        + rootCause.getFinding()
        );

        trace.add(
                verification.getAgentName()
                        + " -> "
                        + verification.getFinding()
        );

        trace.add(
                "Fix Recommendation Agent -> " +
                "corrected payload generated"
        );

        response.setAgentTrace(trace);

        /*
         * =====================================================
         * EVIDENCE
         * =====================================================
         *
         * We deliberately do NOT add rootCause evidence
         * separately in local mode because it is derived
         * from errorAnalysis.
         */
        List<String> evidence =
                new ArrayList<>();

        addUniqueEvidence(
                evidence,
                errorAnalysis.getEvidence()
        );

        addUniqueEvidence(
                evidence,
                validation.getEvidence()
        );

        addUniqueEvidence(
                evidence,
                verification.getEvidence()
        );

        response.setEvidence(evidence);

        return response;
    }

    /*
     * Avoid duplicate evidence entries.
     */
    private void addUniqueEvidence(
            List<String> target,
            List<String> source) {

        if (source == null) {
            return;
        }

        for (String item : source) {

            if (item != null
                    && !item.isBlank()
                    && !target.contains(item)) {

                target.add(item);
            }
        }
    }

    /*
     * Build a useful human-readable recommendation.
     */
    private String buildRecommendation(
            AgentResult rootCause) {

        String diagnosis =
                rootCause.getFinding()
                        .toLowerCase();

        if (diagnosis.contains("family")) {

            return "Provide Patient.name.family and " +
                    "validate the resource against the " +
                    "target profile or server-specific rules.";
        }

        if (diagnosis.contains("birthdate")) {

            return "Check Patient.birthDate format and " +
                    "the target profile constraints.";
        }

        if (diagnosis.contains("gender")) {

            return "Check Patient.gender against the " +
                    "permitted FHIR values.";
        }

        if (diagnosis.contains("observation.status")) {

            return "Check Observation.status and use " +
                    "a permitted value.";
        }

        if (diagnosis.contains("observation.subject")) {

            return "Provide a valid Observation.subject " +
                    "reference.";
        }

        if (diagnosis.contains("authentication")) {

            return "Check the API authentication token " +
                    "and authentication configuration.";
        }

        if (diagnosis.contains("permission")) {

            return "Check the caller's permissions and " +
                    "the FHIR server authorization policy.";
        }

        return "Review the reported server error and " +
                "FHIR validation evidence.";
    }
}