package com.idonagel.financialadvisor.controller;

import com.idonagel.financialadvisor.model.AdvisorRequest;
import com.idonagel.financialadvisor.model.Recommendation;
import com.idonagel.financialadvisor.service.AdvisorService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
/**
 * Web entry point for recommendations.
 * <p>
 * Answers POST requests to {@code /api/recommendation}: receives the questionnaire answers and
 * financial details as JSON, passes them to {@link AdvisorService}, and returns the
 * {@link Recommendation} as JSON. It does no calculations itself.
 */
@RestController
public class RecommendationController {
    private final AdvisorService advisorService;

    public RecommendationController(AdvisorService advisorService) {
        this.advisorService = advisorService;
    }

    @PostMapping("/api/recommendation")
    public Recommendation recommend(@RequestBody AdvisorRequest advisorRequest) {
        return advisorService.recommend(advisorRequest);
    }

}
