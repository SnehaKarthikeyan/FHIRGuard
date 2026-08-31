# Input :

```json
{
  "fhirRequest": {
  "resourceType": "Encounter",
  "class": {
    "code": "AMB"
  }
},
  "httpStatus": 400,
  "errorMessage": "Encounter.status is required"
}
```


# Output :

```json
{
    "agentTrace": [
        "Local Troubleshooting Agent -> The server rejected the request with HTTP 400.",
        "FHIR Validator Agent -> FHIR validation detected one or more issues.",
        "Local Root Cause Agent -> The server rejected the request with HTTP 400.",
        "Local Verification Agent -> VERIFIED_LOCAL: Diagnosis is based on the server error, HTTP status and local rules.",
        "Fix Recommendation Agent -> corrected payload generated"
    ],
    "confidence": 0.7,
    "correctedPayload": {
        "resourceType": "Encounter",
        "class": {
            "code": "AMB"
        }
    },
    "evidence": [
        "HTTP status is 400.",
        "Resource type: Encounter",
        "Server error: Encounter.status is required",
        "FHIR JSON parsed successfully.",
        "ERROR | null | cvc-complex-type.2.4.a: Invalid content was found starting with element '{\"http://hl7.org/fhir\":class}'. One of '{\"http://hl7.org/fhir\":id, \"http://hl7.org/fhir\":meta, \"http://hl7.org/fhir\":implicitRules, \"http://hl7.org/fhir\":language, \"http://hl7.org/fhir\":text, \"http://hl7.org/fhir\":contained, \"http://hl7.org/fhir\":extension, \"http://hl7.org/fhir\":modifierExtension, \"http://hl7.org/fhir\":identifier, \"http://hl7.org/fhir\":status}' is expected.",
        "Server error",
        "HTTP status",
        "Local troubleshooting rules"
    ],
    "httpStatus": 400,
    "recommendedFix": "Review the reported server error and FHIR validation evidence.",
    "resourceType": "Encounter",
    "rootCause": "The server rejected the request with HTTP 400."
}
```
