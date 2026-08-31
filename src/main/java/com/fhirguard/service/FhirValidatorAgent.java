package com.fhirguard.service;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.validation.FhirValidator;
import ca.uhn.fhir.validation.ValidationResult;

import com.fhirguard.model.AgentResult;
import com.fhirguard.model.TroubleshootingRequest;

import org.hl7.fhir.r4.model.Resource;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FhirValidatorAgent {

    private final FhirContext fhirContext;

    public FhirValidatorAgent() {

        this.fhirContext =
                FhirContext.forR4();
    }

    public AgentResult validate(
            TroubleshootingRequest request) {

        List<String> evidence =
                new ArrayList<>();

        try {

            String json =
                    request.getFhirRequest()
                            .toPrettyString();

            IParser parser =
                    fhirContext.newJsonParser();

            Resource resource =
                    (Resource) parser.parseResource(json);

            evidence.add(
                    "FHIR JSON parsed successfully."
            );

            evidence.add(
                    "Resource type: "
                            + resource.getResourceType().name()
            );

            FhirValidator validator =
                    fhirContext.newValidator();

            ValidationResult result =
                    validator.validateWithResult(
                            resource
                    );

            if (result.isSuccessful()) {

                return new AgentResult(
                        "FHIR Validator Agent",
                        "FHIR resource passed configured validation.",
                        0.95,
                        evidence
                );
            }

            result.getMessages()
                    .forEach(message -> {

                        String severity =
                                message.getSeverity() == null
                                        ? "UNKNOWN"
                                        : message.getSeverity()
                                                .toString();

                        String location =
                                message.getLocationString();

                        String text =
                                message.getMessage();

                        evidence.add(
                                severity
                                        + " | "
                                        + location
                                        + " | "
                                        + text
                        );
                    });

            return new AgentResult(
                    "FHIR Validator Agent",
                    "FHIR validation detected one or more issues.",
                    0.95,
                    evidence
            );

        } catch (Exception e) {

            evidence.add(
                    "FHIR validation error: "
                            + e.getMessage()
            );

            return new AgentResult(
                    "FHIR Validator Agent",
                    "FHIR resource could not be validated.",
                    0.99,
                    evidence
            );
        }
    }
}