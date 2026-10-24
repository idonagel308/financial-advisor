package com.idonagel.financialadvisor.service;

import com.idonagel.financialadvisor.model.AdvisorRequest;
import com.idonagel.financialadvisor.model.Recommendation;
import com.idonagel.financialadvisor.model.YearlyValue;
import org.springframework.stereotype.Service;


import java.util.List;

/**
 * Coordinates the whole recommendation: turns a person's answers and money
 * into a portfolio and a projection, by calling the four calculators in order.
 * <p>
 * It calculates nothing itself. Its helpers are passed in through the
 * constructor (dependency injection); Spring creates them and passes them in when the app starts.
 */
@Service
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

    /**
     * Builds the full recommendation for one person.
     * <p>
     * Steps: score the savings rate, combine all answers into a risk score,
     * turn the risk score into a stock / money-market split, then project
     * how the portfolio grows and find when (if ever) the money runs out.
     *
     * @param request the person's questionnaire answers and money
     * @return the recommended portfolio and its year-by-year projection
     * @throws IllegalArgumentException if any input is invalid (from the calculators)
     */
    public Recommendation recommend(AdvisorRequest request) {
        // 1. Risk score: the four answers plus the savings score, in the order of RiskCalculator's WEIGHTS
        int scoreFromSavings = savingsScorer.calculateSavingsScore(request.monthlyIncome(), request.monthlyExpenses());
        int[] scores = {request.timeHorizon(), request.riskTolerance(), request.upcomingExpenses(), request.familySupport(), scoreFromSavings};
        double riskScore = riskCalculator.calculateRiskScore(scores);

        // 2. Portfolio: stock share (capped by risk tolerance), the rest in the money-market fund
        double stockPercentage = portfolioAllocator.calculateStockPercentage(riskScore, request.riskTolerance());
        double moneyMarketPercentage = 100 - stockPercentage;
        double annualReturn = portfolioAllocator.calculateExpectedReturn(stockPercentage);

        // 3. Projection: negative monthly savings means withdrawals
        double monthlySavings = request.monthlyIncome() - request.monthlyExpenses();
        List<YearlyValue> projection = projectionCalculator.project(request.currentEquity(), monthlySavings, annualReturn, request.years());
        YearlyValue lastYear = projection.getLast();
        double profit = lastYear.balance() - lastYear.totalDeposited();

        // 4. The first year the balance hits 0, or 0 if it never does
        int yearMoneyRunsOut = 0;
        for (YearlyValue row : projection) {
            if (row.balance() == 0) {
                yearMoneyRunsOut = row.year();
                break;
            }
        }

        return new Recommendation(riskScore, stockPercentage, moneyMarketPercentage, annualReturn,
                projection, lastYear.balance(), lastYear.totalDeposited(), profit, yearMoneyRunsOut);
    }

}
