# Validation Agent Trajectory

## Scenario

FHIR `Patient` resource where the target server reports that `Patient.name.family` is required.

## Input

The agent receives a FHIR JSON request.

Example:

```json
{
  "resourceType": "Patient",
  "name": [
    {
      "given": ["John"]
    }
  ]
}
```

The request is parsed using HAPI FHIR.

## Agent Instruction

The Validation Agent is asked to validate the supplied FHIR resource and report the validation result without making assumptions about errors that are not returned by the validator.

## Action

The agent:

1. Receives the FHIR request.
2. Parses the JSON as a FHIR resource.
3. Runs the configured FHIR R4 validation.
4. Collects the validation result.
5. Passes the result to the next stage.

## Tool Used

**HAPI FHIR R4 Parser / Validator**

## Tool Result

The configured base FHIR validation passes for the supplied resource.

```text
FHIR validation: PASSED
```

No base FHIR validation error for `Patient.name.family` is returned by the validator in this validation context.

## Agent Observation

The resource is valid according to the validation rules currently being used.

However, the target server has separately reported that `Patient.name.family` is required.

Therefore, the validation result alone does not explain the server rejection.

## Next Step

The validation result is passed to the **Troubleshooting Agent**, together with the HTTP response and server error.
