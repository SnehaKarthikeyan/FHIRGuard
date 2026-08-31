# FHIRGuard Evaluation

This folder contains the test cases used to evaluate FHIRGuard.

The purpose of the evaluation is to compare a simple FHIR validation baseline with the complete FHIRGuard troubleshooting workflow.

## Important: Run Each Test Case Separately

Each JSON file inside the `test-cases` folder represents **one separate FHIR request**.

Do **not** send all 15 JSON files together as one request.

Run them independently:

```text
TC-001 → Run separately → Record result
TC-002 → Run separately → Record result
TC-003 → Run separately → Record result
...
TC-015 → Run separately → Record result
```

Each test case is intended to be processed independently.

## Test Case Format

The JSON files contain the FHIR resource that should be supplied to the application.

For example:

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

The expected behaviour for each test case is documented separately in:

```text
expected-results.md
```

## Evaluation Approach

The same test cases are used for both:

### Baseline

The baseline uses basic FHIR parsing and validation without the complete FHIRGuard troubleshooting workflow.

```text
FHIR Request
     ↓
FHIR Validation
     ↓
Validation Result
```

### FHIRGuard

The complete FHIRGuard workflow is used.

```text
FHIR Request
     ↓
Validation Agent
     ↓
Troubleshooting Agent
     ↓
Root Cause Agent
     ↓
Recommendation
```

This allows the additional value of the troubleshooting workflow to be compared with the simpler validation approach.

## Test Categories

The 15 test cases cover:

* Valid FHIR resources
* Missing required information
* Invalid values
* Missing resource elements
* Malformed JSON
* Unsupported resource types
* Target-server/profile-specific requirements

## Important Note

The expected results describe the intended troubleshooting outcome. The actual result should always be taken from the application's execution.

If the validator or target server produces a different result because of its configured FHIR version, profile, or validation settings, that result should be recorded rather than changed to match the expected result.
