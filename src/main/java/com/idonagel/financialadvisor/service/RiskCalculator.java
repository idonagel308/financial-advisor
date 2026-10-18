package com.idonagel.financialadvisor.service;

public class RiskCalculator {
    private static final double[] WEIGHTS = {0.35, 0.25, 0.20, 0.12, 0.08};// Order: time horizon, risk tolerance, upcoming expenses, family support, savings rate


    public double calculateRiskScore(int[] scores) {
        if(scores == null){
            throw new IllegalArgumentException("Scores is null");
        }
        if(scores.length != WEIGHTS.length){// checking if scores is not the same length as weights
            throw new IllegalArgumentException("Expected " + WEIGHTS.length + " scores but got " + scores.length);
        }
        for( int j =0; j< scores.length; j++){// testing if there is a bad value in the scores array
            if(scores[j]>3 || scores[j]<1){
                throw new IllegalArgumentException("Score at index " + j + " must be between 1 and 3 but was " + scores[j]);
            }
        }
        double sum = 0;
        for (int i = 0; i < WEIGHTS.length; i++) {
            sum += WEIGHTS[i] * scores[i];
        }
        return sum;
    }
}
