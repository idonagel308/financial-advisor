package com.idonagel.financialadvisor.service;
/**
 * Coordinates the whole recommendation: turns a person's answers and money
 * into a portfolio and a projection, by calling the four calculators in order.
 * <p>
 * It calculates nothing itself. Its helpers are passed in through the
 * constructor (dependency injection), so Spring can provide them later.
 */
public class AdvisorService {

    private final RiskCalculator riskCalculator;
    private final PortfolioAllocator portfolioAllocator;
    private final SavingsScorer savingsScorer;
    private final ProjectionCalculator projectionCalculator;


    public AdvisorService(RiskCalculator riskCalculator, PortfolioAllocator portfolioAllocator, SavingsScorer savingsScorer, ProjectionCalculator projectionCalculator) {
        this.riskCalculator = riskCalculator;
        this.portfolioAllocator = portfolioAllocator;
        this.savingsScorer = savingsScorer;
        this.projectionCalculator = projectionCalculator;
    }

}
