package com.fhirguard.service;

import com.fhirguard.model.AgentResult;
import com.fhirguard.model.TroubleshootingRequest;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LocalTroubleshootingAgent {

    public AgentResult analyze(
            TroubleshootingRequest request) {

        List<String> evidence =
                new ArrayList<>();

        String error =
                request.getErrorMessage() == null
                        ? ""
                        : request.getErrorMessage()
                                .toLowerCase();

        String resourceType =
                request.getFhirRequest()
                        .path("resourceType")
                        .asText("Unknown");

        String finding =
                "Unable to determine a specific root cause.";

        double confidence = 0.50;

        /*
         * -------------------------------------------------
         * PATIENT - FAMILY NAME
         * -------------------------------------------------
         */
        if ("Patient".equalsIgnoreCase(resourceType)
                && error.contains("family")) {

            finding =
                    "Patient.name.family is reported as required " +
                    "by the target server or profile. The resource " +
                    "passed the currently configured base FHIR validation.";

            confidence = 0.90;

            evidence.add(
                    "Server error mentions Patient.name.family."
            );

            evidence.add(
                    "Base FHIR validation passed."
            );

            evidence.add(
                    "The failure may be caused by a target profile " +
                    "or server-specific constraint."
            );
        }

        /*
         * -------------------------------------------------
         * PATIENT - BIRTH DATE
         * -------------------------------------------------
         */
        else if ("Patient".equalsIgnoreCase(resourceType)
                && error.contains("birthdate")) {

            finding =
                    "Patient.birthDate is reported as invalid " +
                    "by the target server.";

            confidence = 0.85;

            evidence.add(
                    "Server error mentions Patient.birthDate."
            );
        }

        /*
         * -------------------------------------------------
         * PATIENT - GENDER
         * -------------------------------------------------
         */
        else if ("Patient".equalsIgnoreCase(resourceType)
                && error.contains("gender")) {

            finding =
                    "Patient.gender contains a value that " +
                    "the target server does not accept.";

            confidence = 0.85;

            evidence.add(
                    "Server error mentions Patient.gender."
            );
        }

        /*
         * -------------------------------------------------
         * OBSERVATION - STATUS
         * -------------------------------------------------
         */
        else if ("Observation".equalsIgnoreCase(resourceType)
                && error.contains("status")) {

            finding =
                    "Observation.status is the reported problem.";

            confidence = 0.90;

            evidence.add(
                    "Server error mentions Observation.status."
            );
        }

        /*
         * -------------------------------------------------
         * OBSERVATION - SUBJECT
         * -------------------------------------------------
         */
        else if ("Observation".equalsIgnoreCase(resourceType)
                && error.contains("subject")) {

            finding =
                    "Observation.subject is the reported problem.";

            confidence = 0.90;

            evidence.add(
                    "Server error mentions Observation.subject."
            );
        }

        /*
         * -------------------------------------------------
         * MEDICATION REQUEST - MEDICATION
         * -------------------------------------------------
         */
        else if ("MedicationRequest"
                .equalsIgnoreCase(resourceType)
                && error.contains("medication")) {

            finding =
                    "MedicationRequest.medication is the " +
                    "reported problem.";

            confidence = 0.90;

            evidence.add(
                    "Server error mentions " +
                    "MedicationRequest.medication."
            );
        }

        /*
         * -------------------------------------------------
         * BUNDLE - TYPE
         * -------------------------------------------------
         */
        else if ("Bundle".equalsIgnoreCase(resourceType)
                && error.contains("type")) {

            finding =
                    "Bundle.type is the reported problem.";

            confidence = 0.90;

            evidence.add(
                    "Server error mentions Bundle.type."
            );
        }

        /*
         * -------------------------------------------------
         * HTTP 401 - AUTHENTICATION
         * -------------------------------------------------
         */
        else if (request.getHttpStatus() == 401) {

            finding =
                    "Authentication appears to have failed.";

            confidence = 0.80;

            evidence.add(
                    "HTTP status is 401."
            );
        }

        /*
         * -------------------------------------------------
         * HTTP 403 - AUTHORIZATION
         * -------------------------------------------------
         */
        else if (request.getHttpStatus() == 403) {

            finding =
                    "The caller may not have sufficient permission.";

            confidence = 0.80;

            evidence.add(
                    "HTTP status is 403."
            );
        }

        /*
         * -------------------------------------------------
         * HTTP 404 - NOT FOUND
         * -------------------------------------------------
         */
        else if (request.getHttpStatus() == 404) {

            finding =
                    "The requested endpoint or referenced " +
                    "resource may not exist.";

            confidence = 0.80;

            evidence.add(
                    "HTTP status is 404."
            );
        }

        /*
         * -------------------------------------------------
         * HTTP 5XX - SERVER ERROR
         * -------------------------------------------------
         */
        else if (request.getHttpStatus() >= 500) {

            finding =
                    "The FHIR server reported a server-side failure.";

            confidence = 0.75;

            evidence.add(
                    "HTTP status is 5xx."
            );
        }

        /*
         * -------------------------------------------------
         * HTTP 400 - BAD REQUEST
         * -------------------------------------------------
         */
        else if (request.getHttpStatus() == 400) {

            finding =
                    "The server rejected the request with HTTP 400.";

            confidence = 0.70;

            evidence.add(
                    "HTTP status is 400."
            );
        }

        /*
         * -------------------------------------------------
         * COMMON EVIDENCE
         * -------------------------------------------------
         */
        evidence.add(
                "Resource type: " + resourceType
        );

        evidence.add(
                "Server error: "
                        + request.getErrorMessage()
        );

        /*
         * -------------------------------------------------
         * RETURN RESULT
         * -------------------------------------------------
         */
        return new AgentResult(
                "Local Troubleshooting Agent",
                finding,
                confidence,
                evidence
        );
    }
}