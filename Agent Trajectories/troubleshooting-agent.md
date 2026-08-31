# Troubleshooting Agent Trajectory

## Scenario

The target FHIR server returns an HTTP 400 response for a Patient resource.

## Input

The Troubleshooting Agent receives:

* FHIR request
* HTTP status
* Server error message
* FHIR validation result

Example:

```text
HTTP Status: 400

Server Error:
Patient.name.family is required

FHIR Validation:
Passed
```

## Agent Instruction

The Troubleshooting Agent is asked to review the validation result and server response together and determine what type of problem is being reported.

## Action

The agent:

1. Reviews the HTTP response.
2. Reviews the server error.
3. Reviews the FHIR validation result.
4. Looks at the relevant part of the Patient resource.
5. Compares the server requirement with the supplied resource.
6. Determines whether the issue is a base FHIR validation problem or a target-specific requirement.

## Evidence

The Patient resource contains:

```json
"name": [
  {
    "given": ["John"]
  }
]
```

There is no `family` element.

At the same time, the base FHIR validation has passed.

## Agent Finding

The error does not appear to be caused by a basic FHIR validation failure.

The available evidence indicates that the target server or profile has an additional requirement for:

```text
Patient.name.family
```

## Feedback to Next Stage

The Troubleshooting Agent passes the following finding to the Root Cause Agent:

```text
The resource passes the configured base FHIR validation,
but the target server reports that Patient.name.family is required.

The Patient resource currently contains a given name but no family name.
```

## Next Step

The Root Cause Agent uses this finding together with the original request and validation result to determine the likely cause and recommend a fix.
