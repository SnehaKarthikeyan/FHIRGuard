# Improvement Changelog

This section records the main changes I made while developing FHIRGuard. I used the results from each stage of testing to decide what needed to be improved next.

---

## Version 0.1 – Basic FHIR Validation

### What I started with

The first version focused on parsing and validating FHIR R4 resources using HAPI FHIR.

The basic flow was:

```text
FHIR Request
     ↓
Parse Resource
     ↓
FHIR Validation
     ↓
Validation Result
```

### What I observed

The validator was useful for identifying standard FHIR validation problems, but it did not always explain why a request was rejected by a particular target server.

For example, a resource could pass the configured base FHIR validation while the target server reported an additional requirement.

### Decision

I decided that validation alone was not enough for the troubleshooting use case.

---

## Version 0.2 – Added Troubleshooting

### What I changed

I added a troubleshooting stage that considers the FHIR validation result together with the HTTP status and server error.

The flow became:

```text
FHIR Request
     ↓
FHIR Validation
     ↓
HTTP / Server Error Analysis
     ↓
Troubleshooting Result
```

### What I observed

Looking at the validation result and server error together provided more useful information than looking at the validation result alone.

This helped separate basic FHIR validation issues from problems reported by the target server.

### Decision

I added a separate root-cause analysis stage so that the system could explain the reason for the failure more clearly.

---

## Version 0.3 – Added Root-Cause Analysis

### What I changed

I added the Root Cause Agent.

It receives the information collected during validation and troubleshooting and uses it to identify the most likely reason for the failure.

The flow became:

```text
Validation
     ↓
Troubleshooting
     ↓
Root Cause
     ↓
Recommendation
```

### What I observed

The result was more useful when the system explained not only the error, but also the likely reason behind it and the field that needed attention.

### Decision

I kept root-cause analysis as a separate step instead of putting all the logic into the validation stage.

---

## Version 0.4 – Added Evaluation Cases

### What I changed

I added evaluation cases covering different FHIR troubleshooting situations.

The cases include examples such as:

* Missing fields
* Invalid FHIR resources
* Invalid values
* Target-specific requirements
* Valid resources

### What I observed

Running the same workflow against multiple cases made it easier to check whether the system was producing consistent results.

It also helped identify cases where basic validation and server-side requirements behaved differently.

### Decision

I kept the evaluation cases as part of the project so that future changes can be tested against the same scenarios.

---

## Version 0.5 – Added Local MVP Mode

### What I changed

I made it possible to run the core troubleshooting workflow locally without depending completely on an external LLM service.

For example:

```properties
fhirguard.llm-enabled=false
```

### What I observed

A local mode makes the project easier to demonstrate and reproduce because the basic MVP does not depend on an external API being available.

### Decision

I kept the local mode as the default/reproducible path for the MVP and treated LLM functionality as optional.

---

## Version 0.6 – Improved Error Explanation

### What I changed

I refined the troubleshooting output so that the final result separates:

```text
Problem
Root Cause
Recommended Fix
```

instead of returning only the original validation or server error.

### What I observed

This format makes the result easier for a developer to understand and act on.

For example:

```text
Problem:
Patient.name.family is required.

Root Cause:
The target implementation requires a family name,
but the submitted resource does not contain one.

Recommended Fix:
Add Patient.name.family and retry the request.
```

### Decision

I kept this structure for the final MVP because it makes the output easier to read during both development and demonstration.

---

# Summary of the Improvements

The main progression of FHIRGuard was:

```text
Basic Validation
       ↓
Troubleshooting
       ↓
Root-Cause Analysis
       ↓
Evaluation
       ↓
Reproducible Local MVP
       ↓
Clearer Recommendations
```

The biggest improvement was moving from **just reporting a FHIR validation result** to providing a more complete explanation of the problem and a possible next step.

---

# What I Did Not Keep

During development, I kept the implementation focused on the main troubleshooting workflow rather than adding unnecessary components.

I did not add functionality simply to make the architecture look more complex. The current MVP focuses on demonstrating the core idea:

```text
FHIR Request
     ↓
Validate
     ↓
Troubleshoot
     ↓
Find Root Cause
     ↓
Recommend a Fix
```

Future improvements can build on this workflow once there is a need for additional tools, profiles, real FHIR server integration, or automatic re-validation.
