package com.idonagel.financialadvisor.service;

public class RiskCalculator {
    private static final double[] WEIGHTS = {0.35, 0.25, 0.20, 0.12, 0.08};// Order: time horizon, risk tolerance, upcoming expenses, family support, savings rate
    private static final double MIN_SCORE = 1;
    private static final double MAX_SCORE = 3;


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
