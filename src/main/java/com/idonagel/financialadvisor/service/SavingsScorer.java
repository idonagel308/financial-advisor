package com.idonagel.financialadvisor.service;
/**
 * Turns monthly income and expenses into a savings score from 1 to 3,
 * the fifth input of the risk score.
 * <p>
 * Savings rate = (income - expenses) / income.
 * 0% or less scores 1, above 0% up to and including 10% scores 2,
 * above 10% scores 3. A user with no income scores 1 on purpose
 * (for example a student): the app still shows them what their
 * current money can grow to.
 */
public class SavingsScorer {
    /**
     * Savings rate above which the score is 3, as a decimal (0.10 = 10%).
     * Exactly 10% still scores 2.
     */
    private static final double HIGH_SAVINGS_THRESHOLD = 0.10;// Savings rate above this is considered high (score 3)


    /**
     * Calculates the savings score.
     *
     * @param monthlyIncome income per month in shekels, 0 or more
     * @param monthlyExpenses expenses per month in shekels, 0 or more
     * @return 1, 2 or 3
     * @throws IllegalArgumentException if income or expenses is negative
     */
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
