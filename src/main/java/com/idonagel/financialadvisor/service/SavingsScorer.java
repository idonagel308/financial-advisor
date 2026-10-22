package com.idonagel.financialadvisor.service;

public class SavingsScorer {
    private static final double HIGH_SAVINGS_THRESHOLD = 0.10;// Savings rate above this is considered high (score 3)

    // Savings rate threshold for the top score

    public int calculateSavingsScore(double monthlyIncome, double monthlyExpenses) {
        if (monthlyIncome < 0) {
            throw new IllegalArgumentException("Monthly income must not be negative but was " + monthlyIncome);
        }
        if (monthlyExpenses < 0) {
            throw new IllegalArgumentException("Monthly expense must not be negative but was " + monthlyExpenses);
        }
        if (monthlyIncome == 0) {
            return 1;
        }
        double rate = (monthlyIncome - monthlyExpenses) / monthlyIncome;
        if (rate <= 0) {
            return 1;
        }
        if (rate <= HIGH_SAVINGS_THRESHOLD) {
            return 2;
        }
        return 3;

    }
}
