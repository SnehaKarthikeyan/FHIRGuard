# Expected Evaluation Results

The evaluation contains three test cases. Each test case is executed separately.

The JSON files under `test-cases/` contain the input request and the expected outcome for that particular scenario.

| Test Case | Scenario                                 | Expected Outcome                                                                        |
| --------- | ---------------------------------------- | --------------------------------------------------------------------------------------- |
| TC-001    | FHIR validation/troubleshooting scenario | Identify the validation or server-side issue and provide an appropriate recommendation. |
| TC-002    | FHIR validation/troubleshooting scenario | Identify the reported problem and provide the likely root cause and recommended fix.    |
| TC-003    | FHIR validation/troubleshooting scenario | Analyse the available evidence and provide a useful troubleshooting result.             |

## Important

Each test case is an independent request.

Do not send `TC-001`, `TC-002`, and `TC-003` together as one request.

The expected output is used only for comparison with the actual FHIRGuard result.
