# Baseline Evaluation Results

## Baseline

The baseline represents the simpler approach before the complete FHIRGuard agent workflow is applied.

It uses the FHIR request and validation/server response to identify the reported error.

```text
FHIR Request
     ↓
FHIR Parsing / Validation
     ↓
Validation or Server Error
```

## Results

| Test Case | Baseline Result            | Root Cause             | Recommendation                    |
| --------- | -------------------------- | ---------------------- | --------------------------------- |
| TC-001    | Add actual baseline output | Add actual observation | Add actual recommendation, if any |
| TC-002    | Add actual baseline output | Add actual observation | Add actual recommendation, if any |
| TC-003    | Add actual baseline output | Add actual observation | Add actual recommendation, if any |

## Observation

The baseline provides the basic validation or error information.

The purpose of comparing it with FHIRGuard is to see whether the agent-based workflow provides a clearer explanation of the problem and a more useful next step.

The results in this file should be taken from the actual baseline execution.
