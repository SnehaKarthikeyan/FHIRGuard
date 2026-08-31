# FHIRGuard Evaluation

This folder contains the evaluation cases used to test FHIRGuard.

The current evaluation focuses on three common FHIR troubleshooting scenarios involving missing required fields.

## Test Cases

| ID     | Resource    | Scenario                                      |
| ------ | ----------- | --------------------------------------------- |
| TC-001 | Patient     | `Patient.name.family` is reported as required |
| TC-002 | Observation | `Observation.status` is reported as required  |
| TC-003 | Encounter   | `Encounter.status` is reported as required    |

## Important: Run Each Test Case Separately

Each JSON file inside `test-cases/` represents **one independent troubleshooting request**.

Do not send all three test cases together.

Each test case should be submitted separately to FHIRGuard.

For example:

```text
TC-001 → FHIRGuard → Result 1

TC-002 → FHIRGuard → Result 2

TC-003 → FHIRGuard → Result 3
```

The input contains:

* The FHIR resource
* HTTP status returned by the target server
* Server error message

FHIRGuard then processes the request through its troubleshooting workflow.

## Evaluation Flow

```text
FHIR Request
     ↓
FHIR Validator Agent
     ↓
Local Troubleshooting Agent
     ↓
Local Root Cause Agent
     ↓
Local Verification Agent
     ↓
Fix Recommendation Agent
     ↓
Final Result
```

## What is being evaluated?

The evaluation looks at whether FHIRGuard can:

1. Parse the FHIR request.
2. Identify validation problems.
3. Consider the HTTP status and server error.
4. Identify the reported problem.
5. Determine the likely root cause.
6. Verify the diagnosis using local evidence.
7. Produce a recommended correction.

## Actual Results

The actual FHIRGuard output is recorded directly inside each test case file under 'test-cases/'.

The expected results are documented separately in `expected-results.json`.
