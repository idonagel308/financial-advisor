package com.idonagel.financialadvisor.service;

import com.idonagel.financialadvisor.model.YearlyValue;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
/**
 * Projects how an investment account grows over the years, month by month.
 * <p>
 * Each month the balance first earns interest, then the monthly savings
 * are added: they arrive at the end of the month, so they start earning
 * the month after. Negative monthly savings are withdrawals. When the
 * account runs out, withdrawals stop and the balance stays at 0.
 * <p>
 * The yearly return is converted to a monthly rate that compounds to
 * exactly the yearly return after 12 months, so the projection stays
 * faithful to the historical data.
 */
@Service
public class ProjectionCalculator {
    /** Longest projection allowed, in years. Also protects the server from huge loops. */
    private static final int MAX_YEARS = 60;
    /**
     * Projects the account for the given number of years.
     *
     * @param initialEquity money already invested at the start, in shekels, 0 or more
     * @param monthlySavings shekels added each month; a negative value is a withdrawal
     * @param annualReturn yearly return as a decimal (0.065 means 6.5%)
     * @param years how many years to project, from 1 to MAX_YEARS (60)
     * @return one YearlyValue per year, starting at year 1,
     *         with the state of the account at the end of that year
     * @throws IllegalArgumentException if years is not between 1 and MAX_YEARS, or initialEquity is negative
     */
    public List<YearlyValue> project(double initialEquity, double monthlySavings, double annualReturn, int years) {
        if (years < 1 || years > MAX_YEARS) {
            throw new IllegalArgumentException("Years must be between 1 and "+MAX_YEARS+" but was " + years);
        }
        if (initialEquity < 0) {
            throw new IllegalArgumentException("Initial equity cannot be negative but was " + initialEquity);
        }
        double balance = initialEquity;
        double totalDeposited = initialEquity;
        List<YearlyValue> result = new ArrayList<>();
        double monthlyRate = Math.pow((1 + annualReturn), 1.0 / 12) - 1;
        double monthlyGrowth = 1 + monthlyRate;


        for (int year = 1; year <= years; year++) {
            for (int month = 0; month < 12; month++) {
                balance = balance * monthlyGrowth + monthlySavings;
                totalDeposited += monthlySavings;
                // the account can't go negative: once it's empty, withdrawals stop
                if (balance < 0) {
                    totalDeposited -= balance;
                    balance = 0;
                }
            }
            YearlyValue currentYear = new YearlyValue(year, balance, totalDeposited);
            result.add(currentYear);

        }
        return result;
    }

}
