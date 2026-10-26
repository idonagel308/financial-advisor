package com.idonagel.financialadvisor.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RiskCalculatorTest {
    @Test
    void returnsWeightedAverageForMixedScores() {
        RiskCalculator calculator = new RiskCalculator();
        int[] scores = {3, 1, 3, 3, 3};
        double riskScore = calculator.calculateRiskScore(scores);
        assertEquals(2.5, riskScore, 0.0001);
    }

    @Test
    void rejectsTooFewScores() {
        RiskCalculator calculator = new RiskCalculator();
        int[] scores = {3, 1, 3, 3};
        assertThrows(IllegalArgumentException.class, () -> calculator.calculateRiskScore(scores));
    }

    @Test
    void rejectsTooManyScores() {
        RiskCalculator calculator = new RiskCalculator();
        int[] scores = {3, 1, 3, 3, 3, 3};
        assertThrows(IllegalArgumentException.class, () -> calculator.calculateRiskScore(scores));
    }

    @Test
    void rejectsScoreAboveRange() {
        RiskCalculator calculator = new RiskCalculator();
        int[] scores = {3, 1, 7, 3, 3};
        assertThrows(IllegalArgumentException.class, () -> calculator.calculateRiskScore(scores));
    }

    @Test
    void rejectsScoreBelowRange() {
        RiskCalculator calculator = new RiskCalculator();
        int[] scores = {3, 1, 0, 3, 3};
        assertThrows(IllegalArgumentException.class, () -> calculator.calculateRiskScore(scores));
    }

    @Test
    void rejectsNullScores() {
        RiskCalculator calculator = new RiskCalculator();
        int[] scores = null;
        assertThrows(IllegalArgumentException.class, () -> calculator.calculateRiskScore(scores));

    }
}