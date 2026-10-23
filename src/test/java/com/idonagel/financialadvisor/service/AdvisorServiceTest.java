package com.idonagel.financialadvisor.service;

import com.idonagel.financialadvisor.model.AdvisorRequest;
import com.idonagel.financialadvisor.model.Recommendation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdvisorServiceTest {
    @Test
    void recommendsForExampleUser() {
        AdvisorService service = new AdvisorService(
                new RiskCalculator(), new PortfolioAllocator(),
                new SavingsScorer(), new ProjectionCalculator());
        AdvisorRequest request = new AdvisorRequest(3, 1, 3, 3, 8000, 7000, 10000, 30);
        Recommendation result = service.recommend(request);
        assertEquals(2.5, result.riskScore(), 0.0001);
        assertEquals(50, result.stockPercentage(), 0.0001);
        assertEquals(50, result.moneyMarketPercentage(), 0.0001);
        assertEquals(0.065, result.annualReturn(), 0.000001);
        assertEquals(30, result.projection().size());
        assertEquals(1133170, result.finalBalance(), 1);
        assertEquals(370000, result.totalDeposited(), 0.01);
        assertEquals(763170, result.profit(), 1);
        assertEquals(0, result.yearMoneyRunsOut());
    }
    @Test
    void recommendsForUserWhoSpendsMoreThanEarns(){
        AdvisorService service = new AdvisorService(
                new RiskCalculator(), new PortfolioAllocator(),
                new SavingsScorer(), new ProjectionCalculator());
        AdvisorRequest request = new AdvisorRequest(3, 1, 3, 3, 8000, 9000, 50000, 30);
        Recommendation result = service.recommend(request);
        assertEquals(2.34, result.riskScore(), 0.0001);
        assertEquals(5, result.yearMoneyRunsOut());
    }



}