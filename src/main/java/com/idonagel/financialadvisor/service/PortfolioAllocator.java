package com.idonagel.financialadvisor.service;
/**
 * Turns a risk score into a recommended share of stocks.
 * The rest of the portfolio goes to a money-market fund.
 * <p>
 * The risk score is mapped onto a straight line: score 1 gives 20% stocks,
 * score 3 gives 80%, and every score in between lands proportionally.
 * Then a cap based on the "your portfolio dropped 30%" question limits the
 * result: people who would sell in a panic get fewer stocks, whatever their
 * other answers.
 */
public class PortfolioAllocator {

    /** Stock percentage for the most conservative risk score (1). */
    private static final double MIN_STOCKS = 20;
    /** Stock percentage for the most aggressive risk score (3). */
    private static final double MAX_STOCKS = 80;
    /**
     * Maximum stock percentage for risk tolerance 1, 2 and 3.
     * 80 equals MAX_STOCKS, so tolerance 3 means no cap in practice.
     */
    private static final double[] STOCK_CAPS = {50, 65, 80};
    /** Lowest possible risk score. */
    private static final double MIN_SCORE = 1;
    /** Highest possible risk score. */
    private static final double MAX_SCORE = 3;

    /**
     * Calculates the recommended percentage of the portfolio in stocks.
     *
     * @param riskScore the weighted risk score from RiskCalculator, 1.0 to 3.0
     * @param riskTolerance the answer to the risk-tolerance question, 1 to 3
     *                      (1 = would sell in a crash)
     * @return the stock percentage, 20 to 80 (59.0 means 59%)
     * @throws IllegalArgumentException if riskScore or riskTolerance is outside 1-3
     */
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

