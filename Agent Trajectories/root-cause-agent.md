# Root Cause Agent Trajectory

## Scenario

The Patient resource passes the configured FHIR validation but is rejected by the target server.

## Input

The Root Cause Agent receives:

* Original FHIR request
* FHIR validation result
* HTTP status
* Server error
* Troubleshooting finding

## Agent Instruction

The Root Cause Agent is asked to identify the most likely cause of the failure using the evidence collected by the previous stages and provide a practical recommendation.

## Action

The agent:

1. Reviews the original Patient resource.
2. Reviews the validation result.
3. Reviews the server error.
4. Reviews the troubleshooting finding.
5. Identifies the missing element.
6. Connects the missing element with the reported server requirement.
7. Produces a recommended fix.

## Evidence

The request contains:

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

The server reports:

```text
Patient.name.family is required
```

The configured base FHIR validation passes.

## Root Cause

The target implementation requires `Patient.name.family`, but the submitted Patient resource only contains the given name.

The failure is therefore related to a target-specific or profile-level requirement rather than the base validation result shown by the application.

## Recommended Fix

Add the family name to the Patient resource.

Example:

```json
{
  "resourceType": "Patient",
  "name": [
    {
      "family": "Doe",
      "given": ["John"]
    }
  ]
}
```

## Final Result

```text
Problem:
Patient.name.family is required by the target implementation.

Root Cause:
The submitted Patient resource does not contain a family name.

Recommended Fix:
Add Patient.name.family and submit the request again.
```

## Limitation

The exact requirement should be confirmed against the target server's profile or implementation guide before using the recommendation in production.
