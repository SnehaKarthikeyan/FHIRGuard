package com.fhirguard.service;

import com.fhirguard.model.AgentResult;
import com.fhirguard.model.TroubleshootingRequest;

import org.springframework.stereotype.Component;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.node.ArrayNode;
import tools.jackson.databind.node.ObjectNode;

@Component
public class FixRecommendationAgent {

    public JsonNode generateCorrectedPayload(
            TroubleshootingRequest request,
            AgentResult rootCause) {

        JsonNode payload =
                request.getFhirRequest()
                        .deepCopy();

        String diagnosis =
                rootCause.getFinding()
                        .toLowerCase();

        /*
         * Patient family name
         */
        if (diagnosis.contains("family")
                && payload.has("name")
                && payload.get("name").isArray()) {

            ArrayNode names =
                    (ArrayNode) payload.get("name");

            if (!names.isEmpty()
                    && names.get(0).isObject()) {

                ObjectNode firstName =
                        (ObjectNode) names.get(0);

                if (!firstName.has("family")) {

                    firstName.put(
                            "family",
                            "<PROVIDE_FAMILY_NAME>"
                    );
                }
            }
        }

        return payload;
    }
}