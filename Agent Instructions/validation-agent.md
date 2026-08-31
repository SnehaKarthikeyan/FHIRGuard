# Validation Agent

## Purpose

The Validation Agent is responsible for checking whether the FHIR request is valid according to the FHIR R4 validation rules configured in the application.

The main purpose of this agent is to find validation problems early and provide the result to the next step in the troubleshooting flow.

## Input

The agent receives:

* FHIR request/resource
* Resource type
* FHIR R4 validation configuration

## What the agent does

1. Reads the FHIR request.
2. Parses the JSON as a FHIR resource.
3. Runs FHIR validation.
4. Collects any validation messages.
5. Identifies the affected resource or field when possible.
6. Passes the validation result to the troubleshooting stage.

## Instructions

* Validate the resource before trying to explain the error.
* Use the configured FHIR validator instead of assuming that a resource is invalid.
* Report the validation result clearly.
* Do not create an error that was not returned by the validator.
* If the resource passes the configured validation, report it as valid for that validation context.
* Keep validation and server-specific requirements separate. A resource may pass basic FHIR validation and still be rejected by a target server or profile.

## Expected Output

The agent should provide:

* Validation status
* Validation messages, if any
* Resource/field involved, when available
* Information that can be used by the troubleshooting agent

## Example

Input:

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

Possible result:

```text
Validation Status: Passed

No base FHIR validation error was identified.

The request can be passed to the troubleshooting stage if
the target server has reported an additional requirement.
```
