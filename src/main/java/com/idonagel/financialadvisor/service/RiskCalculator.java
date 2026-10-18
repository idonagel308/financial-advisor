package com.idonagel.financialadvisor.service;

public class RiskCalculator {
    private static final double[] WEIGHTS = {0.35, 0.25, 0.20, 0.12, 0.08};

    public double calculateRiskScore(int[] scores) {
        double sum = 0;
        for (int i = 0; i < WEIGHTS.length; i++) {
            sum += WEIGHTS[i] * scores[i];
        }
        return sum;
    }
}
