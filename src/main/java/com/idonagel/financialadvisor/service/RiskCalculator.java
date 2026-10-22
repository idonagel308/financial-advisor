package com.idonagel.financialadvisor.service;
/**
 * Combines the questionnaire answers into one risk score.
 * <p>
 * Each of the five inputs is a score from 1 (needs a conservative portfolio)
 * to 3 (can take more risk). The risk score is their weighted average,
 * so it is also between 1 and 3. Time horizon has the highest weight,
 * because it is the most important factor.
 */
public class RiskCalculator {
    /**
     * Weight of each score in the average, in this order: time horizon,
     * risk tolerance, upcoming expenses, family support, savings rate.
     * The weights add up to 1.
     */
    private static final double[] WEIGHTS = {0.35, 0.25, 0.20, 0.12, 0.08};
    /** Lowest allowed score for an answer (most conservative). */
    private static final double MIN_SCORE = 1;
    /** Highest allowed score for an answer (most willing to take risk). */
    private static final double MAX_SCORE = 3;

    /**
     * Calculates the weighted risk score.
     *
     * @param scores exactly 5 scores, each from 1 to 3, in the order of WEIGHTS
     * @return the risk score, between 1.0 and 3.0
     * @throws IllegalArgumentException if scores is null, does not have exactly
     *         5 values, or contains a score outside 1-3
     */
    public double calculateRiskScore(int[] scores) {
        if(scores == null){
            throw new IllegalArgumentException("Scores is null");
        }
        if(scores.length != WEIGHTS.length){// checking if scores is not the same length as weights
            throw new IllegalArgumentException("Expected " + WEIGHTS.length + " scores but got " + scores.length);
        }
        for( int j =0; j< scores.length; j++){// testing if there is a bad value in the scores array
            if(scores[j]>MAX_SCORE || scores[j]<MIN_SCORE){
                throw new IllegalArgumentException("Score at index " + j + " must be between "+MIN_SCORE+" and "+MAX_SCORE+" but was " + scores[j]);
            }
        }
        double sum = 0;
        for (int i = 0; i < WEIGHTS.length; i++) {
            sum += WEIGHTS[i] * scores[i];
        }
        return sum;
    }
}
