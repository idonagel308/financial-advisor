package com.idonagel.financialadvisor.service;

public class PortfolioAllocator {


    private static final double MIN_STOCKS = 20;//stock % for the most conservative user (score 1)
    private static final double MAX_STOCKS = 80;//stock % for the most aggressive user (score 3)
    private static final double[] STOCK_CAPS = {50, 65, 80};//the cap for tolerance 1, 2, 3
    private static final double MIN_SCORE = 1;
    private static final double MAX_SCORE = 3;

    public double calculateStockPercentage(double riskScore, int riskTolerance) {
        if (riskScore < MIN_SCORE || riskScore > MAX_SCORE) {
            throw new IllegalArgumentException("Risk score must be between "+MIN_SCORE+" and "+MAX_SCORE+" but was " + riskScore);
        }
        if (riskTolerance < 1 || riskTolerance > STOCK_CAPS.length) {
            throw new IllegalArgumentException("Risk tolerance must be between 1 and " + STOCK_CAPS.length + " but was " + riskTolerance);
        }
        double fraction = (riskScore - MIN_SCORE) /(MAX_SCORE-MIN_SCORE);
        double line = MIN_STOCKS + fraction * (MAX_STOCKS - MIN_STOCKS);
        double cap = STOCK_CAPS[riskTolerance - 1];
        return Math.min(line, cap);
    }
}

