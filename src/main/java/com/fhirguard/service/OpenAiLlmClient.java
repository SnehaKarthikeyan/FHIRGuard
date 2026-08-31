package com.fhirguard.service;

import com.fhirguard.config.FhirGuardProperties;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.ArrayNode;
import tools.jackson.databind.node.ObjectNode;

@Component
public class OpenAiLlmClient implements LlmClient {

    private final RestClient restClient;

    private final FhirGuardProperties properties;

    private final ObjectMapper objectMapper;

    public OpenAiLlmClient(
            FhirGuardProperties properties,
            ObjectMapper objectMapper) {

        this.properties = properties;
        this.objectMapper = objectMapper;

        this.restClient = RestClient.builder()
                .baseUrl("https://api.openai.com/v1")
                .defaultHeader(
                        "Authorization",
                        "Bearer " + getApiKey()
                )
                .build();
    }

    @Override
    public String generate(
            String systemPrompt,
            String userPrompt) {

        if (!properties.isLlmEnabled()) {

            return """
                    {
                      "status": "LOCAL_MODE",
                      "message": "LLM is disabled."
                    }
                    """;
        }

        ObjectNode requestBody =
                objectMapper.createObjectNode();

        requestBody.put(
                "model",
                properties.getOpenAiModel()
        );

        requestBody.put(
                "instructions",
                systemPrompt
        );

        requestBody.put(
                "input",
                userPrompt
        );

        JsonNode response =
                restClient.post()
                        .uri("/responses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(requestBody)
                        .retrieve()
                        .body(JsonNode.class);

        return extractOutputText(response);
    }

    private String getApiKey() {

        String key =
                System.getenv("OPENAI_API_KEY");

        if (key == null || key.isBlank()) {

            throw new IllegalStateException(
                    "OPENAI_API_KEY environment variable is not configured."
            );
        }

        return key;
    }

    private String extractOutputText(
            JsonNode response) {

        JsonNode output =
                response.path("output");

        if (!output.isArray()) {

            throw new IllegalStateException(
                    "OpenAI response did not contain output."
            );
        }

        StringBuilder text =
                new StringBuilder();

        for (JsonNode outputItem : output) {

            if (!"message".equals(
                    outputItem.path("type").asText())) {

                continue;
            }

            JsonNode content =
                    outputItem.path("content");

            if (!content.isArray()) {
                continue;
            }

            for (JsonNode contentItem : content) {

                if ("output_text".equals(
                        contentItem.path("type").asText())) {

                    String value =
                            contentItem.path("text").asText();

                    if (!value.isBlank()) {
                        text.append(value);
                    }
                }
            }
        }

        if (text.isEmpty()) {

            throw new IllegalStateException(
                    "OpenAI response contained no output text."
            );
        }

        return text.toString();
    }
}