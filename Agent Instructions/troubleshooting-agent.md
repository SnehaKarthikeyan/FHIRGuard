# Troubleshooting Agent

## Purpose

The Troubleshooting Agent looks at the FHIR validation result together with the HTTP response and error message.

Its job is to understand what kind of problem occurred and decide what information should be considered when finding the root cause.

## Input

The agent receives:

* FHIR request
* HTTP status
* Server error message
* FHIR validation result

## What the agent does

1. Reviews the validation result.
2. Reviews the HTTP status and server error.
3. Compares the error with the FHIR request.
4. Checks whether the problem is already explained by FHIR validation.
5. If basic validation passes, considers whether the error may come from a profile or target-server requirement.
6. Creates a clear troubleshooting finding.
7. Passes the finding to the root-cause stage.

## Instructions

* Start with the information returned by the validator.
* Use the actual HTTP response and error message as evidence.
* Do not assume that every server error is a FHIR specification error.
* Distinguish between base FHIR validation problems and target-specific requirements.
* Point to the relevant resource or field whenever the information is available.
* Do not invent server requirements that are not supported by the available evidence.
* If the available information is not enough to determine the cause, say so instead of guessing.

## Expected Output

The agent should provide:

* Type of problem
* Relevant resource or field
* Evidence from the validation result or server response
* Initial troubleshooting finding
* Information required for root-cause analysis

## Example

Input:

```text
HTTP Status: 400

Server Error:
Patient.name.family is required

FHIR Validation:
Passed base FHIR validation
```

Possible result:

```text
The resource passed the configured base FHIR validation,
but the target server has reported that Patient.name.family
is required.

This appears to be a target-specific or profile-related
requirement rather than a basic FHIR validation failure.
```
