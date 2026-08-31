package com.fhirguard.evaluation;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/evaluation")
public class EvaluationController {

    private final EvaluationService evaluationService;

    public EvaluationController(
            EvaluationService evaluationService) {

        this.evaluationService =
                evaluationService;
    }

    @PostMapping("/run")
    public List<EvaluationResult> runEvaluation() {

        return evaluationService.runFhirGuard();
    }
}