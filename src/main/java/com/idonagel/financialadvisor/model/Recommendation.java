package com.idonagel.financialadvisor.model;

import java.util.List;
/**
 * The app's full answer for one person: the recommended portfolio
 * and how it is projected to grow.
 *
 * @param riskScore             weighted risk score, 1.0 to 3.0
 * @param stockPercentage       share of the portfolio in stocks (50.0 means 50%)
 * @param moneyMarketPercentage share in the money-market fund (100 minus stocks)
 * @param annualReturn          expected yearly return as a decimal (0.065 means 6.5%)
 * @param projection            one row per year, starting at year 1
 * @param finalBalance          balance at the end of the last year, in shekels
 * @param totalDeposited        money put in over the whole period, net of withdrawals
 * @param profit                finalBalance minus totalDeposited
 * @param yearMoneyRunsOut      first year the balance reaches 0, or 0 if it never does
 */
public record Recommendation(double riskScore, double stockPercentage, double moneyMarketPercentage, double annualReturn, List<YearlyValue> projection, double finalBalance, double totalDeposited, double profit, int yearMoneyRunsOut) {
}
