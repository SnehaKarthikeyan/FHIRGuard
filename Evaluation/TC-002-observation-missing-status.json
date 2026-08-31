# Input :

```json
{
  "fhirRequest": {
  "resourceType": "Observation",
  "code": {
    "text": "Blood Pressure"
  }
},
  "httpStatus": 400,
  "errorMessage": "Observation.status is required"
}
```


# Output :

```json
{
    "agentTrace": [
        "Local Troubleshooting Agent -> Observation.status is the reported problem.",
        "FHIR Validator Agent -> FHIR validation detected one or more issues.",
        "Local Root Cause Agent -> Observation.status is the reported problem.",
        "Local Verification Agent -> VERIFIED_LOCAL: Diagnosis is based on the server error, HTTP status and local rules.",
        "Fix Recommendation Agent -> corrected payload generated"
    ],
    "confidence": 0.9,
    "correctedPayload": {
        "resourceType": "Observation",
        "code": {
            "text": "Blood Pressure"
        }
    },
    "evidence": [
        "Server error mentions Observation.status.",
        "Resource type: Observation",
        "Server error: Observation.status is required",
        "FHIR JSON parsed successfully.",
        "ERROR | null | cvc-complex-type.2.4.a: Invalid content was found starting with element '{\"http://hl7.org/fhir\":code}'. One of '{\"http://hl7.org/fhir\":id, \"http://hl7.org/fhir\":meta, \"http://hl7.org/fhir\":implicitRules, \"http://hl7.org/fhir\":language, \"http://hl7.org/fhir\":text, \"http://hl7.org/fhir\":contained, \"http://hl7.org/fhir\":extension, \"http://hl7.org/fhir\":modifierExtension, \"http://hl7.org/fhir\":identifier, \"http://hl7.org/fhir\":basedOn, \"http://hl7.org/fhir\":partOf, \"http://hl7.org/fhir\":status}' is expected.",
        "Server error",
        "HTTP status",
        "Local troubleshooting rules"
    ],
    "httpStatus": 400,
    "recommendedFix": "Check Observation.status and use a permitted value.",
    "resourceType": "Observation",
    "rootCause": "Observation.status is the reported problem."
}
```
