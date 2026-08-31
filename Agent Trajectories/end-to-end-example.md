# End-to-End Agent Trajectory

## Scenario: Patient Resource Rejected by Target Server

This example shows one complete FHIRGuard execution from the original request through the final recommendation.

---

## 1. Original Request

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

Target server response:

```text
HTTP 400
Patient.name.family is required
```

---

## 2. Validation Agent

### Input

FHIR Patient resource.

### Action

The resource is parsed and validated using the configured HAPI FHIR R4 validator.

### Result

```text
Base FHIR validation: PASSED
```

### Observation

The configured validator did not report `Patient.name.family` as a base validation error.

### Handoff

The validation result and server error are passed to the Troubleshooting Agent.

---

## 3. Troubleshooting Agent

### Input

```text
HTTP 400
Patient.name.family is required
```

and:

```text
FHIR validation: PASSED
```

### Action

The agent compares the server error with the submitted Patient resource.

### Finding

The Patient resource contains a given name but no family name.

The server is applying a requirement that was not reported by the configured base validation.

### Handoff

The finding is passed to the Root Cause Agent.

---

## 4. Root Cause Agent

### Input

The Root Cause Agent receives:

* Original FHIR request
* Validation result
* Server error
* Troubleshooting finding

### Action

The agent connects the missing field with the server error.

### Root Cause

The target server/profile requires `Patient.name.family`, but the submitted resource does not contain it.

### Recommendation

Add the missing family name and retry the request.

---

## 5. Final FHIRGuard Result

```text
Validation:
PASSED according to the configured base FHIR validation.

Problem:
Target server rejected the Patient resource.

Root Cause:
Patient.name.family is required by the target implementation,
but it is missing from the request.

Recommended Fix:
Add Patient.name.family to the Patient resource and retry.
```

---

## What this trajectory demonstrates

The example shows the difference between simple validation and the complete troubleshooting workflow.

A basic validator can report whether the resource passes its configured validation rules.

FHIRGuard adds additional troubleshooting steps to explain a server-side rejection and provide a practical next action.

```text
FHIR Request
     ↓
Validation
     ↓
Validation Result
     ↓
Troubleshooting
     ↓
Root Cause
     ↓
Recommendation
```
